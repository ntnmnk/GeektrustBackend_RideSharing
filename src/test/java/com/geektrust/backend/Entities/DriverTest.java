package com.geektrust.backend.Entities;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import com.geektrust.backend.Enums.Availability;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class DriverTest {
   

    @Test
    public void DriverAvailability_test() {
        Location location = new Location(0, 0);
        Driver driver = new Driver.Builder()
                .setId("123")
                .setCurrentLocation(location)
                .setAvailability(Availability.UNAVAILABLE)
                .build();

        Assertions.assertFalse(driver.isAvailable());
    }

   


    @Test
    public void testGetAvailabilityReturnsSetAvailability() {
        Availability availability = Availability.AVAILABLE;
        Driver driver = new Driver.Builder()
                .setId("1234")
                .setAvailability(availability)
                .build();

        Availability result = driver.getAvailability();

        assertEquals(availability, result);
    }

    @Test
    public void testBuilderBuildReturnsValidDriver() {
        String id = "1234";
        Location currentLocation = new Location(1.0, 2.0);
        Availability availability = Availability.AVAILABLE;
        Driver driver = new Driver.Builder()
                .setId(id)
                .setCurrentLocation(currentLocation)
                .setAvailability(availability)
                .build();

        assertNotNull(driver);
        assertEquals(id, driver.getId());
        assertEquals(currentLocation.getX(), driver.getCurrentLocation().getX());
        assertEquals(currentLocation.getY(), driver.getCurrentLocation().getY());
        assertEquals(availability, driver.getAvailability());
    }

}
