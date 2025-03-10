/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package parkinglot.infra;

/**
 *
 * @author ADMIN
 */
public class Utilities {
    static public long redondeo(double cost){
               
        if(cost%100==0){
            return (long)cost;
        }
        long aux = (long)(cost/100)+1;
        return aux*100;      
    }
   
    static public long sorteo(double cost){
               
        if(cost%100==0){
            return (long)cost;
        }
        long aux = (long)(cost/100)+1;
        return aux*100;      
    }
}
