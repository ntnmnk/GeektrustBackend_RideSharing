package com.geektrust.backend.Repositories;

import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import com.geektrust.backend.Entities.Driver;
import com.geektrust.backend.Entities.Location;
import com.geektrust.backend.Enums.Availability;
import com.geektrust.backend.Exceptions.DriverAlreadyExistsException;
import com.geektrust.backend.GlobalConstants.Constants;
import com.geektrust.backend.Utils.LocationUtils;

public class DriverRepository implements IDriverRepository {

    
    private Map<String, Driver> drivers;


    public DriverRepository() {
        drivers = new HashMap<>();
    }

    @Override
    public List<Driver> findAll() {
        return drivers.values().stream().collect(Collectors.toList());
    }

    @Override
    public void addDriver(String driverId, Location location) {
        Driver driver =new Driver.Builder()
                 .setId(driverId)
                 .setCurrentLocation(location)
                .setAvailability(Availability.AVAILABLE)
                .build();
       
        drivers.putIfAbsent(driverId, driver);
        Optional.ofNullable(drivers.get(driverId))
                .orElseThrow(DriverAlreadyExistsException::new);
    }

    public List<Driver> getAllAvailableDriversWithin5Kms(Location location) {
        return drivers.values().stream()
        .filter(driver -> driver.getAvailability() == Availability.AVAILABLE)
        .filter(driver -> LocationUtils.calculateDistance(driver.getCurrentLocation(), location) <= Constants.DISTANCE_LIMIT_KM)
        .sorted(Comparator.comparingDouble(driver -> LocationUtils.calculateDistance(driver.getCurrentLocation(), location)))
        .collect(Collectors.toList());
}
    

    @Override
    public Optional<Driver> findDriverById(String driverId) {
        
        return Optional.ofNullable(drivers.get(driverId));

    }

    

    @Override
    public void update(Driver driver) {
        if (drivers.containsKey(driver.getId())) {
            drivers.put(driver.getId(), driver);
    }
}
}