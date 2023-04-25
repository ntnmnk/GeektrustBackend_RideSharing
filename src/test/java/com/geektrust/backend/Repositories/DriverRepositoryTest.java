package com.geektrust.backend.Repositories;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import java.util.Arrays;
import java.util.List;
import com.geektrust.backend.Entities.Driver;
import com.geektrust.backend.Entities.Location;
import com.geektrust.backend.Enums.Availability;
import com.geektrust.backend.Utils.LocationUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class DriverRepositoryTest {

        @Mock
        private LocationUtils locationUtils;

        @InjectMocks
        private IDriverRepository driverRepository = new DriverRepository();

        private Driver driver1;
        private Driver driver2;
        private Driver driver3;

        @BeforeEach
        void setUp() throws Exception {
                driver1 = new Driver.Builder().setId("D001").setCurrentLocation(new Location(0, 0))
                                .setAvailability(Availability.AVAILABLE).build();
                driver2 = new Driver.Builder().setId("D002").setCurrentLocation(new Location(1, 1))
                                .setAvailability(Availability.AVAILABLE).build();
                driver3 = new Driver.Builder().setId("D003").setCurrentLocation(new Location(2, 2))
                                .setAvailability(Availability.UNAVAILABLE).build();

                driverRepository.addDriver(driver1.getId(), driver1.getCurrentLocation());
                driverRepository.addDriver(driver2.getId(), driver2.getCurrentLocation());
                driverRepository.addDriver(driver3.getId(), driver3.getCurrentLocation());
        }


        @DisplayName("Add driver")
        @Test
        void testAddDriver() {
                Location location = new Location(3, 3);
                String driverId = "D004";
                driverRepository.addDriver(driverId, location);
                assertEquals(driverId, driverRepository.findDriverById(driverId).get().getId());
        }

        @Test
        @DisplayName("Get all available drivers within 5kms")
        public void testGetAllAvailableDriversWithin5Kms() {

                Driver driver1 = new Driver.Builder().setId("driver1")
                                .setCurrentLocation(new Location(3, 1))
                                .setAvailability(Availability.AVAILABLE).build();
                Driver driver2 = new Driver.Builder().setId("driver2")
                                .setCurrentLocation(new Location(5, 6))
                                .setAvailability(Availability.AVAILABLE).build();
                Driver driver3 = new Driver.Builder().setId("driver3")
                                .setCurrentLocation(new Location(1, 8))
                                .setAvailability(Availability.AVAILABLE).build();
                Driver driver4 = new Driver.Builder().setId("driver4")
                                .setCurrentLocation(new Location(3, 6))
                                .setAvailability(Availability.AVAILABLE).build();

                Location location = new Location(2, 7);

                DriverRepository driverRepository = mock(DriverRepository.class);
                when(driverRepository.getAllAvailableDriversWithin5Kms(location))
                                .thenReturn(Arrays.asList(driver3, driver4, driver2));

                // Act
                List<Driver> result = driverRepository.getAllAvailableDriversWithin5Kms(location);

                // Assert
                assertEquals(3, result.size());
                assertEquals(driver3, result.get(0));
                assertEquals(driver4, result.get(1));
                assertEquals(driver2, result.get(2));
        }



        
}
