package com.geektrust.backend.Repositories;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import com.geektrust.backend.Entities.Driver;
import com.geektrust.backend.Entities.Rider;
import com.geektrust.backend.Exceptions.InvalidRideException;

public class RiderRepository implements IRiderRepository {
    private final Map<String, Rider> riderMap;

    public  RiderRepository() {
        riderMap = new HashMap<>();
    }

    @Override
    public void addRider(Rider rider) {
        riderMap.put(rider.getId(), rider);
    }

    @Override
    public Optional<Rider> getRiderById(String riderId) {
        return Optional.ofNullable(riderMap.get(riderId));
    }

    @Override
    public void updateRider(String id, Rider rider) {
        riderMap.put(id, rider);
    }

    @Override
public void setActiveRideId(String riderId, String rideId) {
    Rider rider = riderMap.get(riderId);
    if (rider != null) {
        rider = new Rider.Builder()
                .setId(riderId)
                .setActiveRideId(rideId)
                .build();
        riderMap.put(riderId, rider);
    }
}

    @Override
    public void deleteRider(String id) {
        riderMap.remove(id);
    }
    
    @Override
public void setMatchedDrivers(String riderId, List<Driver> matchedDrivers) throws InvalidRideException {
    Optional<Rider> riderOptional = getRiderById(riderId);

    if (!riderOptional.isPresent()) {
        throw new InvalidRideException("Invalid rider id");
    }

    Rider rider = riderOptional.get();

    Rider updatedRider = new Rider.Builder()
             .setId(riderId)
            .setCurrentLocation(rider.getCurrentLocation())
            .setCurrentRide(rider.getCurrentRide())
            .setActiveRideId(rider.getActiveRideId())
            .setMatchedDriverIds(matchedDrivers)
            .build();

    updateRider(updatedRider.getId(), updatedRider);
}
}


