package com.geektrust.backend.Utils;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import com.geektrust.backend.Entities.Location;
import com.geektrust.backend.GlobalConstants.Constants;

public class LocationUtils {

   
  
    public static double calculateDistance(Location location1, Location location2) {
        DecimalFormat df = new DecimalFormat("#.##");
       // df.setRoundingMode(RoundingMode.UP);
      // df.setRoundingMode(RoundingMode.CEILING);
        double x1 = location1.getX();
        double y1 = location1.getY();
        double x2 = location2.getX();
        double y2 = location2.getY();
        double distance = Math.sqrt(Math.pow((x2 - x1), Constants.POWER_OF_TWO) + Math.pow((y2 - y1), Constants.POWER_OF_TWO));
        return Double.parseDouble(df.format(distance));
    } 
}
