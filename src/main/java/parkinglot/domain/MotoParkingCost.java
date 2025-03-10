/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package parkinglot.domain;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import parkinglot.infra.Utilities;

/**
 *
 * @author ADMIN
 */
public class MotoParkingCost implements IParkingCost{

    @Override
    public long calculateCost(Vehicle veh, LocalDateTime input, LocalDateTime output) {
        double t = (ChronoUnit.MINUTES.between(input,output)/60.0);
        if (t<=1){
            return 1000;
        }
        else{
            long aux = Utilities.redondeo((t-1)*500+1000);
            return aux;
        }
    }
    
}
