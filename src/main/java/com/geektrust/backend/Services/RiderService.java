package com.geektrust.backend.Services;

import java.util.List;
import java.util.Optional;
import com.geektrust.backend.Entities.Driver;
import com.geektrust.backend.Entities.Location;
import com.geektrust.backend.Entities.Rider;
import com.geektrust.backend.Exceptions.InvalidRideException;
import com.geektrust.backend.Repositories.IRiderRepository;

public class RiderService implements IRiderService {
    private final IRiderRepository riderRepository;


    public RiderService(IRiderRepository riderRepository) {
        this.riderRepository = riderRepository;
    }

    @Override
    public void addRider(String riderId, double locationX, double locationY) {

        Optional<Rider> existingRider = riderRepository.getRiderById(riderId);
        if (existingRider.isPresent()) {
            throw new IllegalArgumentException("Rider with ID " + riderId + " already exists");
        } else {
            Location riderLocation = new Location(locationX, locationY);
            Rider newRider = new Rider.Builder()
            .setId(riderId)
            .setCurrentLocation(riderLocation)
            .build();
            riderRepository.addRider(newRider);
        }
    }

    @Override
    public Optional<Rider> getRiderById(String id) {

        return riderRepository.getRiderById(id);
    }

    @Override
    public void setMatchedDrivers(String riderId, List<Driver> matchedDrivers)
            throws InvalidRideException {
        riderRepository.setMatchedDrivers(riderId, matchedDrivers);
    }


}
