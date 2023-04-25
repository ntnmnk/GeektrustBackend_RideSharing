package com.geektrust.backend.Services;

import static org.junit.jupiter.api.Assertions.assertEquals;


import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import com.geektrust.backend.Entities.Driver;
import com.geektrust.backend.Entities.Location;
import com.geektrust.backend.Entities.Ride;
import com.geektrust.backend.Entities.Rider;
import com.geektrust.backend.Enums.Availability;
import com.geektrust.backend.Enums.RideStatus;
import com.geektrust.backend.Exceptions.InvalidRideException;
import com.geektrust.backend.Exceptions.NoDriversAvailableException;
import com.geektrust.backend.Exceptions.RideNotCompletedException;
import com.geektrust.backend.Repositories.IDriverRepository;
import com.geektrust.backend.Repositories.IRideRepository;
import com.geektrust.backend.Repositories.IRiderRepository;
import com.geektrust.backend.Services.IRideHailingService;
import com.geektrust.backend.Services.RideHailingService;
import com.geektrust.backend.Utils.RideFareCalculator;

public class RideHailingServiceTest {

    private IRideHailingService rideHailingService;

    @Mock
    private RideFareCalculator rideFareCalculator;

    @Mock
    private IDriverRepository driverRepository;

    @Mock
    private IRideRepository rideRepository;

    @Mock
    private IRiderRepository riderRepository;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        rideHailingService = new RideHailingService(driverRepository, rideRepository, riderRepository,rideFareCalculator);
    }

    @Test
    public void testMatchRider() throws NoDriversAvailableException, RideNotCompletedException {
        // given
        String riderId = "R01";
        Location location = new Location(100, 100);
        Rider rider = new Rider.Builder()
        .setId(riderId)
        .setCurrentLocation(location)
        .build();

        List<Driver> drivers = Arrays.asList(new Driver.Builder().setId("D01")
                                .setCurrentLocation(new Location(100, 90))
                                 .setAvailability(Availability.AVAILABLE).build());

        Mockito.when(riderRepository.getRiderById(riderId)).thenReturn(Optional.of(rider));
        Mockito.when(driverRepository.getAllAvailableDriversWithin5Kms(location)).thenReturn(drivers);
        // when
        List<Driver> matchedDrivers = rideHailingService.matchRider(riderId);
        // then
        assertEquals(drivers, matchedDrivers);
    }
    
    

}
