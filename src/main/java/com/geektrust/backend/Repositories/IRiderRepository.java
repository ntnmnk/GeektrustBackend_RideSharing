package com.geektrust.backend.Repositories;

import java.util.List;
import java.util.Optional;
import com.geektrust.backend.Entities.Driver;
import com.geektrust.backend.Entities.Rider;
import com.geektrust.backend.Exceptions.InvalidRideException;

public interface IRiderRepository {
    
    public void addRider(Rider rider);

    public Optional<Rider> getRiderById(String riderId);
    public void updateRider(String id, Rider rider);

    public void deleteRider(String id);
    public void setActiveRideId(String riderId, String rideId);
    public void setMatchedDrivers(String riderId, List<Driver> matchedDrivers) throws InvalidRideException;
}
