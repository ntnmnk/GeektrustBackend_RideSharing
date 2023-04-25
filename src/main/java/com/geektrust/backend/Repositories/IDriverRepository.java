package com.geektrust.backend.Repositories;

import java.util.List;
import java.util.Optional;
import com.geektrust.backend.Entities.Driver;
import com.geektrust.backend.Entities.Location;

public interface IDriverRepository {
   
    List<Driver> findAll();
    public Optional<Driver> findDriverById(String id) ;
    public void addDriver(String driverId, Location location);
    public List<Driver> getAllAvailableDriversWithin5Kms(Location location);
    public void update(Driver driver);
}
