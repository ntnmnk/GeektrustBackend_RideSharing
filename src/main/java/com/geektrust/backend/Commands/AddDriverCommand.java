package com.geektrust.backend.Commands;

import java.util.List;
import com.geektrust.backend.Entities.Location;
import com.geektrust.backend.Exceptions.DriverAlreadyExistsException;
import com.geektrust.backend.GlobalConstants.Constants;
import com.geektrust.backend.Services.IDriverService;

public class AddDriverCommand implements ICommand {

    private final IDriverService driverService;

    public AddDriverCommand(IDriverService driverService) {
        this.driverService = driverService;

    }

    @Override
    public void execute(List<String> tokens) throws Exception {
        try {
            String driverId = tokens.get(Constants.DRIVER_ID_INDEX);
            int x = Integer.parseInt(tokens.get(Constants.LOCATION_X_INDEX));
            int y = Integer.parseInt(tokens.get(Constants.LOCATION_Y_INDEX));
            Location location = new Location(x, y);
            driverService.addDriver(driverId, location);


        } catch (DriverAlreadyExistsException e) {
            System.out.println("Driver already exists!");
        } catch (Exception e) {
            System.out.println("Error adding driver: " + e.getMessage());
            throw e;
        }

    }

}
