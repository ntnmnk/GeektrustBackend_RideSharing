package com.geektrust.backend.Services;

import java.util.List;
import java.util.Optional;
import com.geektrust.backend.Entities.Driver;
import com.geektrust.backend.Entities.Location;
import com.geektrust.backend.Exceptions.DriverAlreadyExistsException;
import com.geektrust.backend.Repositories.IDriverRepository;

public class DriverService implements IDriverService {
    private final IDriverRepository driverRepository;

    public DriverService(IDriverRepository driverRepository) {
        this.driverRepository = driverRepository;
    }

    @Override
    public void addDriver(String driverId, Location location) throws DriverAlreadyExistsException {
        driverRepository.addDriver(driverId, location);
    }

    @Override
    public List<Driver> getAllDrivers() {
        return driverRepository.findAll();
    }

    @Override
    public Optional<Driver> getDriverById(String driverId) {
        return driverRepository.findDriverById(driverId);
    }
}
