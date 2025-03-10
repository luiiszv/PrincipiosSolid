package parkinglot.domain;

import parkinglot.access.IVehicleRepository;
import parkinglot.access.RepositoryFactory;
import parkinglot.domain.service.Service;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.List;

import org.junit.Test;
import static org.junit.Assert.*;

public class ParkingTest {

    public ParkingTest() {
    }

    @Test
    public void testMotoCost() {
        System.out.println("Prueba Moto - Cálculo de costo");

        Vehicle veh = new Vehicle("FTK-123", TypeEnum.MOTO);
        IVehicleRepository repo = RepositoryFactory.getInstance().getRepository("default");
        Service service = new Service(repo);

        // Caso 1: Moto - 1 hora y 30 minutos
        LocalDateTime input = LocalDateTime.of(2021, Month.FEBRUARY, 22, 17, 0);
        LocalDateTime output = LocalDateTime.of(2021, Month.FEBRUARY, 22, 18, 30);
        assertEquals(1300L, service.calculateParkingCost(veh, input, output));

        // Caso 2: Moto - Menos de una hora
        output = LocalDateTime.of(2021, Month.FEBRUARY, 22, 17, 45);
        assertEquals(1000L, service.calculateParkingCost(veh, input, output));
    }

    @Test
    public void testCarCost() {
        System.out.println("Prueba Carro - Cálculo de costo");

        Vehicle veh = new Vehicle("FTK-123", TypeEnum.CAR);
        IVehicleRepository repo = RepositoryFactory.getInstance().getRepository("default");
        Service service = new Service(repo);

        // Caso 1: Carro - 2 horas y 10 minutos
        LocalDateTime input = LocalDateTime.of(2021, Month.FEBRUARY, 22, 8, 0);
        LocalDateTime output = LocalDateTime.of(2021, Month.FEBRUARY, 22, 10, 10);
        assertEquals(3200L, service.calculateParkingCost(veh, input, output));

        // Caso 2: Carro - Menos de una hora
        output = LocalDateTime.of(2021, Month.FEBRUARY, 22, 8, 45);
        assertEquals(2000L, service.calculateParkingCost(veh, input, output));
    }

    @Test
    public void testTruckCost() {
        System.out.println("Prueba Camión - Cálculo de costo");

        Vehicle veh = new Vehicle("JNK-838", TypeEnum.TRUCK);
        IVehicleRepository repo = RepositoryFactory.getInstance().getRepository("default");
        Service service = new Service(repo);

        // Caso 1: Camión - Menos de 12 horas
        LocalDateTime input = LocalDateTime.of(2021, Month.FEBRUARY, 22, 8, 0);
        LocalDateTime output = LocalDateTime.of(2021, Month.FEBRUARY, 22, 13, 0);
        assertEquals(10000L, service.calculateParkingCost(veh, input, output));

        // Caso 2: Camión - Más de 12 horas
        output = LocalDateTime.of(2021, Month.FEBRUARY, 22, 22, 0);
        assertEquals(15000L, service.calculateParkingCost(veh, input, output));
    }

    @Test
    public void testVehiclePersistence() {
        System.out.println("Prueba de Persistencia - Guardar y Listar");

        IVehicleRepository repo = RepositoryFactory.getInstance().getRepository("default");
        Service service = new Service(repo);

        Vehicle veh1 = new Vehicle("QET-646", TypeEnum.MOTO);
        service.saveVehicle(veh1);

        Vehicle veh2 = new Vehicle("NBV-987", TypeEnum.CAR);
        service.saveVehicle(veh2);

        Vehicle veh3 = new Vehicle("IJY-986", TypeEnum.TRUCK);
        service.saveVehicle(veh3);

        List<Vehicle> list = service.listVehicles();
        assertEquals(3, list.size());
        assertEquals("QET-646", list.get(0).getPlate());
    }

    @Test
    public void testNonExistentVehicle() {
        System.out.println("Prueba - Buscar un vehículo inexistente");

        IVehicleRepository repo = RepositoryFactory.getInstance().getRepository("default");
        Service service = new Service(repo);

        List<Vehicle> list = service.listVehicles();
        assertFalse(list.stream().anyMatch(v -> v.getPlate().equals("XYZ-999")));
    }
}
