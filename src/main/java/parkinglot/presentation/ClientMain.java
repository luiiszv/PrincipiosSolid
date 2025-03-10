/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package parkinglot.presentation;

import parkinglot.access.IVehicleRepository;
import parkinglot.access.RepositoryFactory;
import parkinglot.domain.Vehicle;
import parkinglot.domain.TypeEnum;
import parkinglot.domain.service.Service;
import parkinglot.infra.Lottery;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.List;
import java.util.Scanner;


/**
 * Un cliente main de prueba
 *
 * @author ADMIN
 */
import java.time.LocalDateTime;
import java.time.Month;
import java.util.List;
import java.util.Scanner;

public class ClientMain {
    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        boolean continuar = true;

        while (continuar) {
            String opcionShoos;
            TypeEnum tipoVehiculo = null;

            System.out.println("MENU: \n Escriba una opción \n 1. Moto \n 2. Carro \n 3. Camión");
            opcionShoos = scanner.nextLine();

            switch (opcionShoos) {
                case "1":
                    tipoVehiculo = TypeEnum.MOTO;
                    System.out.println("Seleccionaste Moto");
                    break;
                case "2":
                    tipoVehiculo = TypeEnum.CAR;
                    System.out.println("Seleccionaste Carro");
                    break;
                case "3":
                    tipoVehiculo = TypeEnum.TRUCK;
                    System.out.println("Seleccionaste Camión");
                    break;
                default:
                    System.out.println("Opción no válida");
                    continue;
            }

            System.out.println("Ingrese la placa");
            String placa = scanner.nextLine();
            Vehicle veh = new Vehicle(placa, tipoVehiculo);

            System.out.println("Ingrese un Número");
            int userNumber = Integer.parseInt(scanner.nextLine());

            LocalDateTime input = LocalDateTime.of(2025, Month.MARCH, 22, 8, 0);
            LocalDateTime output = LocalDateTime.of(2025, Month.MARCH, 22, 19, 30);

            IVehicleRepository repo = RepositoryFactory.getInstance().getRepository("default");
            Service service = new Service(repo); // Inyección de dependencias

            long result = service.calculateParkingCost(veh, input, output);
            if (veh.getType().equals(TypeEnum.TRUCK)) {
                int prediction = Lottery.getLottery(userNumber);
                System.out.println("Número jugado: " + userNumber);

                if (prediction != 0) {
                    System.out.println("¡Ganaste! El servicio es gratis, ahorraste: " + result);
                    result = 0;
                } else {
                    System.out.println("Resultado: " + prediction + " \n¡Vuelve a intentarlo!");
                }
            }

            System.out.println("Valor a pagar: " + result);
            service.saveVehicle(veh);

            // Mostrar todos los vehículos registrados
            System.out.println("\nLista de vehículos registrados:");
            List<Vehicle> list = service.listVehicles();
            list.forEach(vehicle -> System.out.println(vehicle.toString()));

            // Preguntar si desea continuar
            System.out.println("\n¿Desea registrar otro vehículo? (s/n)");
            String respuesta = scanner.nextLine().trim().toLowerCase();
            continuar = respuesta.equals("s");
        }

        System.out.println("Programa finalizado.");
        scanner.close();
    }
}
