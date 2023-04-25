package com.geektrust.backend.Services;

import java.util.List;
import java.util.Optional;
import com.geektrust.backend.Entities.Driver;
import com.geektrust.backend.Entities.Ride;

public interface IRideHailingService {
    public List<Driver> matchRider(String riderId);

    public Optional<Ride> startRide(String rideId, String driverId, String riderId);

    public String stopRide(String rideId, double destX, double destY, int timeTaken);
    
    public double calculateBill(String rideId);
    
    public Optional<Ride> getRideById(String rideId);

    public Optional<Driver> getDriverById(String driverId);
   

   
}
