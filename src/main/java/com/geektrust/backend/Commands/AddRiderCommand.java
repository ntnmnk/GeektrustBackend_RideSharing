package com.geektrust.backend.Commands;

import java.util.List;
import com.geektrust.backend.GlobalConstants.Constants;
import com.geektrust.backend.Services.IRiderService;

public class AddRiderCommand implements ICommand {


    private final IRiderService riderService;

    public AddRiderCommand(IRiderService riderService) {
        this.riderService = riderService;
    }

    @Override
    public void execute(List<String> tokens) {
        String riderId = tokens.get(Constants.RIDER_ID_INDEX);
        double latitude = Double.parseDouble(tokens.get(Constants.LATITUDE_INDEX));
        double longitude = Double.parseDouble(tokens.get(Constants.LONGITUDE_INDEX));
        riderService.addRider(riderId, latitude, longitude);

    }
}
