package com.geektrust.backend.Commands;

import java.util.List;
import java.util.Optional;
import com.geektrust.backend.Entities.Ride;
import com.geektrust.backend.GlobalConstants.Constants;
import com.geektrust.backend.Services.IRideHailingService;

public class GenerateBillCommand implements ICommand{

   
   private final IRideHailingService rideHailingService;

   public GenerateBillCommand(IRideHailingService rideHailingService) {
    this.rideHailingService = rideHailingService;
}
    @Override
    public void execute(List<String> tokens) {
        try {
            if (tokens.size() != Constants.REQUIRED_TOKENS_COUNT) {
               throw new Exception(Constants.INVALID_COMMAND_MESSAGE);
            }
            
            String rideId = tokens.get(Constants.RIDE_ID_TOKEN_INDEX);
            double bill = rideHailingService.calculateBill(rideId);
   
            if (bill == Constants.ZERO_BILL) {
               return;
            }
   
            Optional<Ride> ride = rideHailingService.getRideById(rideId);
            if (!ride.isPresent()) {
               throw new Exception(Constants.INVALID_RIDE_MESSAGE);
            }
   
            Ride currentRide = ride.get();
            String driverId = currentRide.getDriver().getId();
   
            System.out.printf(Constants.BILL_MESSAGE, rideId, driverId, bill);
         } catch (Exception e) {
            System.out.println(Constants.INVALID_RIDE_MESSAGE);
         }
      }
    }
        
    
