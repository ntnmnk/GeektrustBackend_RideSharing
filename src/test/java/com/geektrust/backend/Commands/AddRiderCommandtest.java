package com.geektrust.backend.Commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.verify;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import com.geektrust.backend.Entities.Ride;
import com.geektrust.backend.Entities.Rider;
import com.geektrust.backend.Repositories.RiderRepository;
import com.geektrust.backend.Services.RiderService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class AddRiderCommandtest {
    private RiderService riderService;
    private ICommand command;

    @BeforeEach
    void setUp() {
        riderService = new RiderService(new RiderRepository());
        command = new AddRiderCommand(riderService);
    }

    @Test
    void shouldAddRider() throws Exception {
        List<String> tokens = Arrays.asList("","rider-1", "12.34", "56.78");
        command.execute(tokens);

        Optional<Rider> crider = riderService.getRiderById("rider-1");
        Rider rider =crider.get();
        assertNotNull(crider);
        assertEquals("rider-1", rider.getId());
        assertEquals(12.34, rider.getCurrentLocation().getX());
        assertEquals(56.78, rider.getCurrentLocation().getY());
    }
}