package com.geektrust.backend.Commands;
import static org.mockito.Mockito.*;

import java.util.Arrays;
import java.util.List;
import com.geektrust.backend.Exceptions.InvalidRideException;
import com.geektrust.backend.Services.IRideHailingService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

public class StopRideCommandTest {
    @Test
    public void testExecute() throws Exception {
        // given
        IRideHailingService rideHailingService = mock(IRideHailingService.class);
        ICommand stopRideCommand = new StopRideCommand(rideHailingService);

        String rideId = "R01";
        double destX = 2.0;
        double destY = 2.0;
        int timeTaken = 10;
        List<String> tokens = Arrays.asList("STOP_RIDE",rideId, String.valueOf(destX), String.valueOf(destY), String.valueOf(timeTaken));
        // when
        stopRideCommand.execute(tokens);
        // then
        verify(rideHailingService, times(1)).stopRide(rideId, destX, destY, timeTaken);
    }
}
