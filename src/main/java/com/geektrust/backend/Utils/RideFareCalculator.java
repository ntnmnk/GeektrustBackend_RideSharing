package com.geektrust.backend.Utils;

import java.text.DecimalFormat;
import java.util.Optional;
import com.geektrust.backend.Entities.Location;
import com.geektrust.backend.Entities.Ride;
import com.geektrust.backend.Exceptions.InvalidRideException;
import com.geektrust.backend.Exceptions.RideNotCompletedException;
import com.geektrust.backend.GlobalConstants.Constants;
import com.geektrust.backend.Repositories.IRideRepository;

public class RideFareCalculator {
    private final IRideRepository rideRepository;

    public RideFareCalculator(IRideRepository rideRepository) {
        this.rideRepository = rideRepository;
    }

    public double calculateBill(String rideId) {
        Optional<Ride> ride = rideRepository.getRideById(rideId);

        if (!ride.isPresent()) {
            throw new InvalidRideException(Constants.INVALID_RIDE_MESSAGE);
        }

        Ride currentRide = ride.get();
        if (currentRide.getEndTime() == null) {
            throw new RideNotCompletedException(Constants.RIDE_NOT_COMPLETED_MESSAGE);
        }

        DecimalFormat df = new DecimalFormat("#.##");
        
        double distance = calculateDistance(currentRide.getDestination(), currentRide.getStartLocation());

        double distanceFare = calculateDistanceFare(distance);
        double timeFare = calculateTimeFare(currentRide.getTimeTaken());
        double totalFare = calculateTotalFare(distanceFare, timeFare);
        double serviceTax = calculateServiceTax(totalFare);
        double finalFare = calculateFinalFare(totalFare, serviceTax);
        
        return Double.parseDouble(df.format(finalFare));
    }

    private double calculateDistance(Location destination, Location startLocation) {
        DecimalFormat df = new DecimalFormat("#.##");
        double distance = LocationUtils.calculateDistance(destination, startLocation);
        return Double.parseDouble(df.format(distance));
    }

    private double calculateDistanceFare(double distance) {
        DecimalFormat df = new DecimalFormat("#.##");
        double fare = distance * Constants.PER_KM_FARE;
        return Double.parseDouble(df.format(fare));
    }

    private double calculateTimeFare(int timeTaken) {
        DecimalFormat df = new DecimalFormat("#.##");
        return timeTaken * Constants.PER_MINUTE_FARE;
        
    }

    private double calculateTotalFare(double distanceFare, double timeFare) {
        double totalFare = Constants.BASE_FARE + distanceFare + timeFare;
        DecimalFormat df = new DecimalFormat("#.##");
        return Constants.BASE_FARE + distanceFare + timeFare;
    }

    private double calculateServiceTax(double totalFare) {
        
        double serviceTax = totalFare * Constants.SERVICE_TAX_PERCENTAGE;
        DecimalFormat df = new DecimalFormat("#.##");
        return Double.parseDouble(df.format(serviceTax));
    }
    

    private double calculateFinalFare(double totalFare, double serviceTax) {
        double finalFare = totalFare + serviceTax;
        DecimalFormat df = new DecimalFormat("#.##");
        return Double.parseDouble(df.format(finalFare));
    }

  
}
