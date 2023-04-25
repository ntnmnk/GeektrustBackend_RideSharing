package com.geektrust.backend.Entities;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
public class RiderTest {
    @Test
    public void testRiderConstructor() {
        String riderId = "rider1";
        Location location = new Location(10.0, 20.0);
        Rider newRider = new Rider.Builder()
        .setId(riderId)
        .setCurrentLocation(location)
        .build();

        Assertions.assertEquals(riderId, newRider.getId());
        Assertions.assertEquals(location, newRider.getCurrentLocation());
    }

    @Test
    public void testAssignRide() {
        String riderId = "rider1";
        Location location = new Location(10.0, 20.0);
        

        String rideId = "ride1";
        Rider rider = new Rider.Builder()
        .setId(riderId)
        .setCurrentLocation(location)
        .setActiveRideId(rideId)
        .build();

        Assertions.assertEquals(rideId, rider.getActiveRideId());
    }

   

   
}
