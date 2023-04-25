package com.geektrust.backend.Services;

import java.util.List;
import java.util.Optional;
import com.geektrust.backend.Entities.Driver;
import com.geektrust.backend.Entities.Rider;
import com.geektrust.backend.Exceptions.InvalidRideException;

public interface IRiderService {
         /**
     * Adds a rider's location to the service.
     * 
     * @param riderId the ID of the rider whose location is being added
     * @param xCoordinate the X-coordinate of the rider's location
     * @param yCoordinate the Y-coordinate of the rider's location
     *
     */
   public void addRider(String riderId, double xCoordinate, double yCoordinate);

        /**
     * Retrieves a rider with the given ID.
     * 
     * @param id the ID of the rider to retrieve
     * @return the rider with the given ID, or null if no rider was found
     *
     */
    public Optional<Rider> getRiderById(String id) ;

    public void setMatchedDrivers(String riderId, List<Driver> matchedDrivers) throws InvalidRideException;
}
