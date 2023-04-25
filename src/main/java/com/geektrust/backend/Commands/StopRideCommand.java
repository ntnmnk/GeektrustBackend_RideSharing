package com.geektrust.backend.Commands;

import java.util.List;
import com.geektrust.backend.Exceptions.InvalidRideException;
import com.geektrust.backend.GlobalConstants.Constants;
import com.geektrust.backend.Services.IRideHailingService;

public class StopRideCommand implements ICommand {

    private final IRideHailingService rideHailingService;


    public StopRideCommand(IRideHailingService rideHailingService) {
        this.rideHailingService = rideHailingService;
    }

    @Override
    public void execute(List<String> tokens) {
        String rideId = tokens.get(Constants.RIDE_ID_TOKEN_INDEX);
        double destX = Double.parseDouble(tokens.get(Constants.DEST_X_TOKEN_INDEX));
        double destY = Double.parseDouble(tokens.get(Constants.DEST_Y_TOKEN_INDEX));
        int timeTaken = Integer.parseInt(tokens.get(Constants.TIME_TAKEN_TOKEN_INDEX));

        try {
            String output = rideHailingService.stopRide(rideId, destX, destY, timeTaken);
            System.out.println(output);
        } catch (InvalidRideException e) {
            System.out.println(Constants.INVALID_RIDE_MESSAGE);

        }

    }
}


