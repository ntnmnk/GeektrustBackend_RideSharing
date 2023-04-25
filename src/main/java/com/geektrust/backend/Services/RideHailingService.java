package com.geektrust.backend.Services;


import java.text.DecimalFormat;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.TreeMap;
import java.util.stream.Collectors;
import com.geektrust.backend.Entities.Driver;
import com.geektrust.backend.Entities.Location;
import com.geektrust.backend.Entities.Ride;
import com.geektrust.backend.Entities.Rider;
import com.geektrust.backend.Entities.Ride.Builder;
import com.geektrust.backend.Enums.Availability;
import com.geektrust.backend.Enums.RideStatus;
import com.geektrust.backend.Exceptions.InvalidRideException;
import com.geektrust.backend.Exceptions.NoDriversAvailableException;
import com.geektrust.backend.Exceptions.RideNotCompletedException;
import com.geektrust.backend.GlobalConstants.Constants;
import com.geektrust.backend.Repositories.IDriverRepository;
import com.geektrust.backend.Repositories.IRideRepository;
import com.geektrust.backend.Repositories.IRiderRepository;
import com.geektrust.backend.Utils.LocationUtils;
import com.geektrust.backend.Utils.RideFareCalculator;



public class RideHailingService implements IRideHailingService {

    private final RideFareCalculator rideFareCalculator;

    private final IDriverRepository driverRepository;
    private final IRideRepository rideRepository;
    private final IRiderRepository riderRepository;

    public RideHailingService(IDriverRepository driverRepository, IRideRepository rideRepository,
            IRiderRepository riderRepository, RideFareCalculator rideFareCalculator) {
        this.driverRepository = driverRepository;
        this.rideRepository = rideRepository;
        this.riderRepository = riderRepository;
        this.rideFareCalculator = rideFareCalculator;
    }

    @Override
    public List<Driver> matchRider(String riderId) {

        Optional<Rider> riderOpt = riderRepository.getRiderById(riderId);

        Rider rider = riderOpt.orElseThrow(() -> new RideNotCompletedException("INVALID_RIDE"));

        if (rider.getCurrentRide() != null) {
            throw new RideNotCompletedException("RIDE_NOT_COMPLETED");
        }

        Location riderLocation = rider.getCurrentLocation();
        List<Driver> availableDrivers =
                driverRepository.getAllAvailableDriversWithin5Kms(riderLocation);

        if (availableDrivers.isEmpty()) {
            throw new NoDriversAvailableException("NO_DRIVERS_AVAILABLE");
        }
        TreeMap<Double, List<Driver>> driversByDistance = new TreeMap<>();
        for (Driver driver : availableDrivers) {
            double distance = LocationUtils.calculateDistance(riderLocation, driver.getCurrentLocation());
            driversByDistance.computeIfAbsent(distance, k -> new ArrayList<>()).add(driver);
        }
    
        List<Driver> matchedDrivers = new ArrayList<>();
        for (List<Driver> drivers : driversByDistance.values()) {
            Collections.sort(drivers, Comparator.comparing(Driver::getId));
            matchedDrivers.addAll(drivers);
            if (matchedDrivers.size() >= 5) {
                break;
            }
        }
    
        matchedDrivers.forEach(driverRepository::update);
        return matchedDrivers.subList(0, Math.min(matchedDrivers.size(), 5));
    }

    @Override
    public Optional<Ride> startRide(String rideId, String driverIndex, String riderId)
            throws InvalidRideException, NoDriversAvailableException {



        Rider rider = validateRider(riderId);
        Driver driver = validateDriver(rider, driverIndex);

        Location startLocation =
                new Location(rider.getCurrentLocation().getX(), rider.getCurrentLocation().getY());
        LocalDateTime startTime = LocalDateTime.now();

        Ride ride = new Builder().setId(rideId).setRider(rider).setDriver(driver)
                .setStartLocation(startLocation).setRideStatus(RideStatus.STARTED)
                .setStartTime(startTime).build();
        riderRepository.setActiveRideId(riderId, rideId);
        rideRepository.addRide(ride);

        Driver updatedDriver = new Driver.Builder().setId(driver.getId())
                .setCurrentLocation(driver.getCurrentLocation())
                .setAvailability(Availability.UNAVAILABLE).build();
        driverRepository.update(updatedDriver);
        return Optional.of(ride);
    }

    /**
     * Stops the current ride by setting the end time and calculating the total bill.
     *
     * @param destinationX the X coordinate of the destination location
     * @param destinationY the Y coordinate of the destination location
     * @param timeTakenInMinutes the time taken to complete the ride in minutes
     * 
     */
    @Override
    public String stopRide(String rideId, double destX, double destY, int timeTaken) {
        if (timeTaken < Constants.INPUT_FILE_ARG_INDEX) {
            throw new IllegalArgumentException("Time taken cannot be negative");
        }

        Driver driver = rideRepository.getDriverByRideId(rideId);
        Optional<Ride> optionalRide = rideRepository.getRideById(rideId);
        if (optionalRide.isPresent()) {
            Ride ride = optionalRide.get();
            if (ride.getRideStatus() == RideStatus.STARTED) {
                Location destination = new Location(destX, destY);

                // Create a new driver object with updated availability
                Driver updatedDriver = new Driver.Builder().setId(driver.getId())
                        .setCurrentLocation(driver.getCurrentLocation())
                        .setAvailability(Availability.AVAILABLE).build();

                driverRepository.update(updatedDriver);

                ride = Ride.newBuilder().setId(rideId).setDriver(driver)
                        .setStartLocation(ride.getStartLocation()).setDestination(destination)
                        .setEndTime(LocalDateTime.now()).setTimeTaken(timeTaken)
                        .setRideStatus(RideStatus.COMPLETED).build();

                rideRepository.update(ride);

                return "RIDE_STOPPED " + ride.getId();
            } else {
                throw new InvalidRideException("INVALID_RIDE");
            }
        } else {
            throw new InvalidRideException("INVALID_RIDE");
        }
    }

    private Rider validateRider(String riderId) throws InvalidRideException {

        Optional<Rider> riderOpt = riderRepository.getRiderById(riderId);
        if (!riderOpt.isPresent()) {
            throw new InvalidRideException("Invalid rider id");
        }
        Rider rider = riderOpt.get();
        if (rider.getCurrentRide() != null) {
            throw new InvalidRideException("RIDE_NOT_COMPLATED");
        }
        return rider;
    }

    private Driver validateDriver(Rider rider, String driverIndex) throws InvalidRideException {
        List<Driver> matchedDrivers = rider.getMatchedDriverIds();
        if (matchedDrivers == null || matchedDrivers.isEmpty()) {
            throw new InvalidRideException("INVALID_RIDE");
        }
        int index = Integer.parseInt(driverIndex) - Constants.INDEX; 
        if (index < 0 || index >= matchedDrivers.size()) {
            throw new InvalidRideException("Invalid driver index");
        }
        Driver selectedDriver = matchedDrivers.get(index);
        Optional<Driver> optionalDriver = driverRepository.findDriverById(selectedDriver.getId());
        if (!optionalDriver.isPresent()) {
            throw new InvalidRideException("INVALID_RIDE");
        }
        return optionalDriver.get();
    }

    @Override
    public double calculateBill(String rideId) {
        double finalFare = rideFareCalculator.calculateBill(rideId);
        return finalFare;
    }

    @Override
    public Optional<Ride> getRideById(String rideId) {
        return rideRepository.getRideById(rideId);
    }

    @Override
    public Optional<Driver> getDriverById(String driverId) {
        return driverRepository.findDriverById(driverId);
    }

}
