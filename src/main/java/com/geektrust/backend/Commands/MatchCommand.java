package com.geektrust.backend.Commands;

import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.stream.Collectors;
import com.geektrust.backend.Entities.Driver;
import com.geektrust.backend.Entities.Ride;
import com.geektrust.backend.Entities.Rider;
import com.geektrust.backend.Enums.RideStatus;
import com.geektrust.backend.Exceptions.InvalidRideException;
import com.geektrust.backend.Exceptions.NoDriversAvailableException;
import com.geektrust.backend.GlobalConstants.Constants;
import com.geektrust.backend.Repositories.IRideRepository;
import com.geektrust.backend.Repositories.IRiderRepository;
import com.geektrust.backend.Services.IRideHailingService;
import com.geektrust.backend.Services.IRiderService;

public class MatchCommand implements ICommand {

   
    private final IRideHailingService rideHailingService;
    private final IRiderService riderService;

    public MatchCommand(IRideHailingService rideHailingService, IRiderService riderService) {

        this.rideHailingService = rideHailingService;
        this.riderService = riderService;
    }

    @Override
    public void execute(List<String> tokens) {

        // Get the rider ID from the tokens
        String riderId = tokens.get(Constants.RIDER_ID_INDEX);

        // Match the rider with available drivers
        List<Driver> matchedDrivers;
        try {
            matchedDrivers = rideHailingService.matchRider(riderId);
        } catch (InvalidRideException e) {
            System.out.println(Constants.INVALID_RIDER_MESSAGE);
            return;
        } catch (NoDriversAvailableException e) {
            System.out.println(Constants.NO_DRIVERS_AVAILABLE_MESSAGE);
            return;
        }

        String driversMatched =
                matchedDrivers.stream().map(Driver::getId).collect(Collectors.joining(" "));


        // Update the Rider entity with the matched drivers
        try {
            riderService.setMatchedDrivers(riderId, matchedDrivers);
            System.out.println("DRIVERS_MATCHED " + driversMatched);
        } catch (InvalidRideException e) {
            System.out.println(Constants.INVALID_RIDER_MESSAGE);
            return;
        }



    }
}
