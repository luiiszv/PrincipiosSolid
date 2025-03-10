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
public class Lottery {
    
    static public int getLottery(int n){
        int aux = (int)(Math.random()*100+1);            
        if(aux==n){
            return 0;
        }
        return aux;
    }
    
}
