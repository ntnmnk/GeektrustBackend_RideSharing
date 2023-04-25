package com.geektrust.backend.Commands;

import java.io.PrintStream;
import java.util.List;
import java.util.Optional;
import com.geektrust.backend.Entities.Ride;
import com.geektrust.backend.Exceptions.InvalidRideException;
import com.geektrust.backend.Exceptions.NoDriversAvailableException;
import com.geektrust.backend.GlobalConstants.Constants;
import com.geektrust.backend.Services.IRideHailingService;

public class StartRideCommand implements ICommand {


    private final IRideHailingService rideHailingService;
    private final PrintStream output;


    public StartRideCommand(IRideHailingService rideHailingService,PrintStream output) {
        this.rideHailingService = rideHailingService;
        this.output = output;
    }

    @Override
    public void execute(List<String> tokens) {

        String rideId = tokens.get(Constants.RIDE_ID_TOKEN_INDEX);
        String driverIndex = tokens.get(Constants.DRIVER_INDEX_TOKEN_INDEX);
        String riderId = tokens.get(Constants.RIDER_ID_TOKEN_INDEX);

        try {

            Optional<Ride> ride = rideHailingService.startRide(rideId, driverIndex, riderId);
            if (ride.isPresent()) {
                output.println("RIDE_STARTED " + ride.get().getId());
            }
        } catch (InvalidRideException | NoDriversAvailableException e) {
            output.println("INVALID_RIDE");
        }
    }
}


