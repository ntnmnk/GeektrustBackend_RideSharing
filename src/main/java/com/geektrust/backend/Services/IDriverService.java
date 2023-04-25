package com.geektrust.backend.Services;

import java.util.List;
import java.util.Optional;
import com.geektrust.backend.Entities.Driver;
import com.geektrust.backend.Entities.Location;
import com.geektrust.backend.Exceptions.DriverAlreadyExistsException;

public interface IDriverService {
    void addDriver(String driverId, Location location) throws DriverAlreadyExistsException;
    
    List<Driver> getAllDrivers();
    
    Optional<Driver> getDriverById(String driverId);
}
