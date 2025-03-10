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

public class ClientMain {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        boolean continuar = true;

        while (continuar) {
            try {
                System.out.println("\nMENU: \n Escriba una opción:");
                System.out.println("1. Moto");
                System.out.println("2. Carro");
                System.out.println("3. Camión");
                System.out.print("Opción: ");
                String opcionShoos = scanner.nextLine().trim();

                TypeEnum tipoVehiculo;
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
                        System.out.println("Opción no válida. Intente de nuevo.");
                        continue;
                }

                System.out.print("Ingrese la placa: ");
                String placa = scanner.nextLine().trim();
                if (placa.isEmpty()) {
                    System.out.println("La placa no puede estar vacía. Intente de nuevo.");
                    continue;
                }
                Vehicle veh = new Vehicle(placa, tipoVehiculo);

                System.out.print("Ingrese un número para la lotería: ");
                int userNumber = Integer.parseInt(scanner.nextLine().trim());

                LocalDateTime input = LocalDateTime.of(2025, Month.MARCH, 22, 8, 0);
                LocalDateTime output = LocalDateTime.of(2025, Month.MARCH, 22, 19, 30);

                IVehicleRepository repo = RepositoryFactory.getInstance().getRepository("default");
                Service service = new Service(repo); // Inyección de dependencias

                long result = service.calculateParkingCost(veh, input, output);
                if (veh.getType().equals(TypeEnum.TRUCK)) {
                    int prediction = Lottery.getLottery(userNumber);
                    System.out.println("\nNúmero jugado: " + userNumber);

                    if (prediction == 0) {
                        System.out.println("🎉 ¡Ganaste! El servicio es gratis, ahorraste: " + result);
                        result = 0;
                    } else {
                        System.out.println("❌ No ganaste. Resultado: " + prediction);
                        System.out.println("🔄 ¡Vuelve a intentarlo!");
                    }
                }

                System.out.println("💰 Valor a pagar: " + result);
                service.saveVehicle(veh);

                // Mostrar todos los vehículos registrados
                System.out.println("\n🚗 Lista de vehículos registrados:");
                List<Vehicle> list = service.listVehicles();
                list.forEach(System.out::println);

                // Preguntar si desea continuar
                System.out.print("\n¿Desea registrar otro vehículo? (s/n): ");
                String respuesta = scanner.nextLine().trim().toLowerCase();
                continuar = respuesta.equals("s");

            } catch (NumberFormatException e) {
                System.out.println("❌ Entrada inválida. Ingrese un número válido.");
            }
        }

        System.out.println("📌 Programa finalizado.");
        scanner.close();
    }
}
