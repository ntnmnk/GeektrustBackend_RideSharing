package com.geektrust.backend.Services;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.Optional;
import com.geektrust.backend.Entities.Location;
import com.geektrust.backend.Entities.Rider;
import com.geektrust.backend.Repositories.IRiderRepository;


public class RiderServiceTest {
    private IRiderService riderService;

    @Mock
    private IRiderRepository riderRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        riderService = new RiderService(riderRepository);
    }

    @Test
    @DisplayName("Add new rider successfully")
    void addNewRider()  {
        Mockito.when(riderRepository.getRiderById(ArgumentMatchers.anyString()))
                .thenReturn(Optional.empty());
        Location location = new Location(10.0, 20.0);
        riderService.addRider("R001", location.getX(), location.getY());

        Mockito.verify(riderRepository).addRider(ArgumentMatchers.any(Rider.class));
    }
   
    @Test
    @DisplayName("Get existing rider by id")
    void getExistingRiderById() {
        Mockito.when(riderRepository.getRiderById("R001"))
            .thenReturn(Optional.of(new Rider.Builder()
                    .setId("R001")
                    .setCurrentLocation(new Location(10.0, 20.0))
                    .build()));

        Optional<Rider> rider = riderService.getRiderById("R001");

        Assertions.assertTrue(rider.isPresent());
        Assertions.assertEquals("R001", rider.get().getId());
        Assertions.assertEquals(10.0, rider.get().getCurrentLocation().getX());
        Assertions.assertEquals(20.0, rider.get().getCurrentLocation().getY());
    }

    @Test
    @DisplayName("Get non-existing rider by id")
    void getNonExistingRiderById() {
        Mockito.when(riderRepository.getRiderById("R002"))
                .thenReturn(Optional.empty());

        Optional<Rider> rider = riderService.getRiderById("R002");

        Assertions.assertFalse(rider.isPresent());
    }

    
}
