package com.geektrust.backend.Commands;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import com.geektrust.backend.Entities.Driver;
import com.geektrust.backend.Entities.Location;
import com.geektrust.backend.Enums.Availability;
import com.geektrust.backend.Exceptions.DriverAlreadyExistsException;
import com.geektrust.backend.Repositories.DriverRepository;
import com.geektrust.backend.Services.DriverService;
import com.geektrust.backend.Services.IDriverService;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

public class AddDriverCommandTest {
    private final IDriverService driverService = new DriverService(new DriverRepository());
    private final ICommand command = new AddDriverCommand(driverService);

    @Test
    public void testExecute_AddsDriverToService() throws Exception {
        // Arrange
        String driverId = "d1";
        int x = 10;
        int y = 20;
        Location location = new Location(x, y);
        String[] inputTokens = {"add_driver", driverId, Integer.toString(x), Integer.toString(y)};

        // Act
        command.execute(Arrays.asList(inputTokens));

        // Assert
        Driver driver = driverService.getDriverById(driverId).orElse(null);
        assertNotNull(driver);
        assertEquals(driverId, driver.getId());
       // assertEquals(location, driver.getCurrentLocation());
    }

}
