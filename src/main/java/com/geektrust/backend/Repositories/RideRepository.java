package com.geektrust.backend.Repositories;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import com.geektrust.backend.Entities.Driver;
import com.geektrust.backend.Entities.Ride;

public class RideRepository implements IRideRepository {
    private final Map<String, Ride> ridesMap;

    public RideRepository() {
        ridesMap = new HashMap<>();
    }

    public Driver getDriverByRideId(String rideId) {
        Optional<Ride> optionalRide = ridesMap.values()
                                              .stream()
                                              .filter(ride -> ride.getId().equals(rideId))
                                              .findFirst();
        if (optionalRide.isPresent()) {
            return optionalRide.get().getDriver();
        } else {
            return null;
        }
    }

    @Override
    public List<Ride> getAllRides() {
        return new ArrayList<>(ridesMap.values());
    }


    @Override
    public void addRide(Ride ride) {
        ridesMap.putIfAbsent(ride.getId(), ride);
    }

    @Override
    public Optional<Ride> getRideById(String rideId) {
        if (ridesMap.containsKey(rideId)) {
            return Optional.of(ridesMap.get(rideId));
        }
        
        return Optional.empty();
    }
   
    @Override
    public List<Ride> getRidesForDriver(String driverId) {
       
        return null;
    }

    @Override
    public List<Ride> getRidesForRider(String riderId) {
        
        return null;
    }

    @Override
    public void update(Ride ride) {
        
        ridesMap.put(ride.getId(), ride);
        
    } 
}
   
