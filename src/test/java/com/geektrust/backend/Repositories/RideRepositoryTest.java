package com.geektrust.backend.Repositories;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import com.geektrust.backend.Entities.Driver;
import com.geektrust.backend.Entities.Location;
import com.geektrust.backend.Entities.Ride;
import com.geektrust.backend.Entities.Rider;
import com.geektrust.backend.Enums.Availability;
import com.geektrust.backend.Enums.RideStatus;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class RideRepositoryTest {
    private IRideRepository rideRepository;
    private DriverRepository driverRepository;
    private RiderRepository riderRepository;

    @BeforeEach
    public void setUp() {
        rideRepository = new RideRepository();
        driverRepository = new DriverRepository();
        riderRepository = new RiderRepository();
    }

    @Test
    public void testAddAndGetRideById() {
        // create a rider
        Rider rider = new Rider.Builder()
        .setId("rider1")
        .setCurrentLocation(new Location(1.0, 2.0))
        .build();
        
        riderRepository.addRider(rider);

        // create a driver
        Driver driver = new Driver.Builder()
        .setId("driver1")
        .setCurrentLocation(new Location(3.0, 4.0))
        .setAvailability(Availability.AVAILABLE)
        .build();
        
        driverRepository.addDriver(driver.getId(),new Location(3.0, 4.0));

        // create a ride
        LocalDateTime startTime = LocalDateTime.now();
        Ride ride = new Ride.Builder().setId("ride1").setRider(rider).setDriver(driver)
                .setStartLocation(new Location(1.0, 2.0)).setRideStatus(RideStatus.STARTED)
                .setStartTime(startTime).build();

        // add the ride to the repository
        rideRepository.addRide(ride);

        // get the ride from the repository by id
        Optional<Ride> optionalRide = rideRepository.getRideById("ride1");

        // assert that the ride is present and contains the correct rider and driver
        Assertions.assertTrue(optionalRide.isPresent());
        Assertions.assertEquals(rider, optionalRide.get().getRider());
        Assertions.assertEquals(driver, optionalRide.get().getDriver());
    }


}
