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

    /**
     * Test de cálculo de costos para motos
     */
    @Test
    public void MotosTest() {
        System.out.println("Moto hora y media");
        Vehicle veh = new Vehicle("FTK-123", TypeEnum.MOTO);
        LocalDateTime input = LocalDateTime.of(2021, Month.FEBRUARY, 22, 17, 0);
        LocalDateTime output = LocalDateTime.of(2021, Month.FEBRUARY, 22, 18, 30);
        IVehicleRepository repo = RepositoryFactory.getInstance().getRepository("default");
        Service service = new Service(repo);
        long result = service.calculateParkingCost(veh, input, output);
        assertEquals(1300L, result);

        System.out.println("Moto menos una hora");
        output = LocalDateTime.of(2021, Month.FEBRUARY, 22, 17, 45);
        result = service.calculateParkingCost(veh, input, output);
        assertEquals(1000L, result);

        System.out.println("Moto 3 horas y 45 minutos");
        input = LocalDateTime.of(2021, Month.FEBRUARY, 22, 8, 0);
        output = LocalDateTime.of(2021, Month.FEBRUARY, 22, 11, 45);
        result = service.calculateParkingCost(veh, input, output);
        assertEquals(2400L, result);
    }

    /**
     * Test de cálculo de costos para carros
     */
    @Test
    public void CarTest() {
        System.out.println("Carro 2 horas y 10 minutos");
        Vehicle veh = new Vehicle("FTK-123", TypeEnum.CAR);
        LocalDateTime input = LocalDateTime.of(2021, Month.FEBRUARY, 22, 8, 0);
        LocalDateTime output = LocalDateTime.of(2021, Month.FEBRUARY, 22, 10, 10);
        IVehicleRepository repo = RepositoryFactory.getInstance().getRepository("default");
        Service service = new Service(repo);
        long result = service.calculateParkingCost(veh, input, output);
        assertEquals(3200L, result);

        System.out.println("Carro menos una hora");
        output = LocalDateTime.of(2021, Month.FEBRUARY, 22, 8, 45);
        result = service.calculateParkingCost(veh, input, output);
        assertEquals(2000L, result);

        System.out.println("Carro 1 hora y 30 minutos");
        input = LocalDateTime.of(2021, Month.FEBRUARY, 22, 8, 0);
        output = LocalDateTime.of(2021, Month.FEBRUARY, 22, 9, 30);
        result = service.calculateParkingCost(veh, input, output);
        assertEquals(2500L, result);
    }

    /**
     * Test de calculo de costos para camiones
     */
    @Test
    public void TruckTest() {
        System.out.println("Camión menos de 12 horas");
        Vehicle veh = new Vehicle("JNK-838", TypeEnum.TRUCK);
        LocalDateTime input = LocalDateTime.of(2021, Month.FEBRUARY, 22, 8, 0);
        LocalDateTime output = LocalDateTime.of(2021, Month.FEBRUARY, 22, 13, 0);
        IVehicleRepository repo = RepositoryFactory.getInstance().getRepository("default");
        Service service = new Service(repo);
        long result = service.calculateParkingCost(veh, input, output);
        assertEquals(10000L, result);

        System.out.println("Camión más de 12 horas");
        output = LocalDateTime.of(2021, Month.FEBRUARY, 22, 22, 0);
        result = service.calculateParkingCost(veh, input, output);
        assertEquals(15000L, result);

        System.out.println("Camión 3 días y una hora");
        input = LocalDateTime.of(2021, Month.FEBRUARY, 22, 8, 0);
        output = LocalDateTime.of(2021, Month.FEBRUARY, 25, 9, 15);
        result = service.calculateParkingCost(veh, input, output);
        assertEquals(45700L, result);
    }

    /**
     * Test de persistencia: guardar y listar vehículos
     */
    @Test
    public void PersistenceTest() {
        System.out.println("Guardar y listar");

        IVehicleRepository repo = RepositoryFactory.getInstance().getRepository("default");
        Service service = new Service(repo);

        // Guardar vehículos
        service.saveVehicle(new Vehicle("QET-646", TypeEnum.MOTO));
        service.saveVehicle(new Vehicle("NBV-987", TypeEnum.CAR));
        service.saveVehicle(new Vehicle("IJY-987", TypeEnum.TRUCK));

        // Obtener lista de vehículos
        List<Vehicle> list = service.listVehicles();

        // Verificaciones
        assertEquals(3, list.size());
        assertEquals("QET-646", list.get(0).getPlate());
    }
}