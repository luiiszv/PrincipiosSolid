/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package parkinglot.domain;

import parkinglot.infra.Utilities;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

/**
 *
 * @author ADMIN
 */
public class TruckParkingCost implements IParkingCost{

    @Override
    public long calculateCost(Vehicle veh, LocalDateTime input, LocalDateTime output) {
        double t = (ChronoUnit.HOURS.between(input,output));
        if (t<=12){
            return 10000;
        }
        else if (t>12&&t<=24){
            return 15000;
        }
        else{
            long aux = Utilities.redondeo((t*15000)/24);
            return aux;       
        }
    }
    
}
