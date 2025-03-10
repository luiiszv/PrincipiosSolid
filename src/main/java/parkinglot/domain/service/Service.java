/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package parkinglot.domain.service;

import parkinglot.access.IVehicleRepository;
import parkinglot.domain.IParkingCost;
import parkinglot.domain.ParkingCostFactory;
import parkinglot.domain.Vehicle;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author ADMIN
 */
public class Service {
    private IVehicleRepository repository;

    /**
     * Inyección de dependencias en el constructor. Ya no conviene que el mismo
     * servicio cree un repositorio concreto
     *
     * @param repository una clase hija de IProductRepository
     */
    public Service(IVehicleRepository repository) {
        this.repository = repository;
    }

    public long calculateParkingCost(Vehicle vehicle, LocalDateTime input , LocalDateTime output) {

        //Validate vehicle.
        if (vehicle == null) {
            return -1;
        }
        // La fábrica devuelve una instancia de la jerarquia IDelivery
        IParkingCost parking = ParkingCostFactory.getInstance().getParking(vehicle.getType());
        long result = parking.calculateCost(vehicle, input, output);

        return result;
    }

    public boolean saveVehicle(Vehicle newVehicle) {

        //Validate product
        if (newVehicle == null || newVehicle.getPlate().isBlank()) {
            return false;
        }

        repository.save(newVehicle);
        return true;

    }

    public List<Vehicle> listVehicles() {
        List<Vehicle> vehicles = new ArrayList<>();
        vehicles = repository.list();

        return vehicles;
    }
}
