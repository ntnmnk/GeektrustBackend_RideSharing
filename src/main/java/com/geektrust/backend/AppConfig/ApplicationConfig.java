package com.geektrust.backend.AppConfig;

import java.io.PrintStream;
import com.geektrust.backend.Commands.AddDriverCommand;
import com.geektrust.backend.Commands.AddRiderCommand;
import com.geektrust.backend.Commands.CommandInvoker;
import com.geektrust.backend.Commands.GenerateBillCommand;
import com.geektrust.backend.Commands.MatchCommand;
import com.geektrust.backend.Commands.StartRideCommand;
import com.geektrust.backend.Commands.StopRideCommand;
import com.geektrust.backend.Repositories.DriverRepository;
import com.geektrust.backend.Repositories.IDriverRepository;
import com.geektrust.backend.Repositories.IRideRepository;
import com.geektrust.backend.Repositories.IRiderRepository;
import com.geektrust.backend.Repositories.RideRepository;
import com.geektrust.backend.Repositories.RiderRepository;
import com.geektrust.backend.Services.DriverService;
import com.geektrust.backend.Services.IDriverService;
import com.geektrust.backend.Services.IRideHailingService;
import com.geektrust.backend.Services.IRiderService;
import com.geektrust.backend.Services.RideHailingService;
import com.geektrust.backend.Services.RiderService;
import com.geektrust.backend.Utils.RideFareCalculator;

public class ApplicationConfig {

    
    private final PrintStream output=new PrintStream(System.out);
    private final IRiderRepository riderRepository = new RiderRepository();
    private final IDriverRepository driverRepository = new DriverRepository();
    private final CommandInvoker commandInvoker = new CommandInvoker();
    private final IDriverService driverService = new DriverService(driverRepository);
    private final IRiderService riderService = new RiderService(riderRepository);
    private final IRideRepository rideRepository = new RideRepository();
    private RideFareCalculator rideFareCalculator=new RideFareCalculator(rideRepository);
    private final AddDriverCommand addDriverCommand = new AddDriverCommand(driverService);
    private final IRideHailingService rideHailingService = new RideHailingService(driverRepository, rideRepository, riderRepository,rideFareCalculator);
    private final AddRiderCommand addRiderCommand = new AddRiderCommand(riderService);
    private final MatchCommand matchCommand = new MatchCommand(rideHailingService,riderService);
    private final StartRideCommand startRideCommand = new StartRideCommand(rideHailingService,output);
    private final StopRideCommand stopRideCommand = new StopRideCommand(rideHailingService);
    private final GenerateBillCommand generateBillCommand = new GenerateBillCommand(rideHailingService);
   

    public CommandInvoker getCommandInvoker() {
        registerAddDriverCommand();
        registerAddRiderCommand();
        registerMatchCommand();
        registerStartRideCommand();
        registerStopRideCommand();
        registerGenerateBillCommand();

        return commandInvoker;
    }

    private void registerAddDriverCommand() {
        commandInvoker.register("ADD_DRIVER", addDriverCommand);
    }

    private void registerAddRiderCommand() {
        commandInvoker.register("ADD_RIDER", addRiderCommand);
    }

    private void registerMatchCommand() {
        commandInvoker.register("MATCH", matchCommand);
    }

    private void registerStartRideCommand() {
        commandInvoker.register("START_RIDE", startRideCommand);
    }

    private void registerStopRideCommand() {
        commandInvoker.register("STOP_RIDE", stopRideCommand);
    }

    private void registerGenerateBillCommand() {
        commandInvoker.register("BILL", generateBillCommand);
    }
}

