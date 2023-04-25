package com.geektrust.backend.Repositories;

import java.util.List;
import java.util.Optional;
import com.geektrust.backend.Entities.Driver;
import com.geektrust.backend.Entities.Ride;

public interface IRideRepository {
    void addRide(Ride ride);
    List<Ride> getRidesForDriver(String driverId);
    List<Ride> getRidesForRider(String riderId);
    Optional<Ride> getRideById(String rideId);
    public List<Ride> getAllRides();
    void update(Ride ride);
    public Driver getDriverByRideId(String rideId);
}
