/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package parkinglot.domain;
import java.time.LocalDateTime;
import parkinglot.infra.Utilities;

/**
 * Interfaz para calcular el costo del parqueadero de cualquier tipo de
 * vehiculo: carro, moto, camión
 *
 * @author ADMIN
 */
public interface IParkingCost {

    long calculateCost(Vehicle veh, LocalDateTime input, LocalDateTime output);
}
