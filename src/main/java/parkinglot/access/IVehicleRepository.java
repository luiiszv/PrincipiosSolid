/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package parkinglot.access;

import parkinglot.domain.Vehicle;
import java.util.List;

/**
 *
 * @author ADMIN
 */
public interface IVehicleRepository {
    boolean save(Vehicle newVehicle);

    List<Vehicle> list();
}
