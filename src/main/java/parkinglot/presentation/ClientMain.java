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
public class ClientMain {
    public static void main(String[] args) {
        
        Scanner scanner = new Scanner(System.in);
        
        String opcionShoos;
       
        
        Enum<TypeEnum> selected = null;
        
        System.out.println("MENU: \n Escriba una opcion \n 1.moto \n 2.carro \n 3.camion");
        
        opcionShoos = scanner.nextLine();
        
        switch(opcionShoos){
            case "1":  {
                selected = TypeEnum.MOTO;
                System.out.println("Seleccionaste Moto");
                break;
            }
            case "2":{
                selected = TypeEnum.CAR;
                System.out.println("Seleccionaste Moto");
                break;
            }
            case "3":{
                selected = TypeEnum.TRUCK;
                System.out.println("Seleccionaste Camion");
                break;
            }
            default: 
                System.out.println("Opcion no valida");
        }
        
        System.out.println("Ingrese la placa");
        String placa = scanner.nextLine();
        Vehicle veh = new Vehicle(placa, (TypeEnum) selected);
        LocalDateTime input = LocalDateTime.of(2025, Month.MARCH, 22, 8, 0);
        LocalDateTime output = LocalDateTime.of(2025, Month.MARCH, 22, 19, 30);
        IVehicleRepository repo = RepositoryFactory.getInstance().getRepository("default");
        Service service = new Service(repo); //Inyección de dependencias
        long result = service.calculateParkingCost(veh, input, output);
        if(veh.getType().equals(TypeEnum.TRUCK)){
            int n=(int)(Math.random()*100+1);
            int l = Lottery.getLottery(n);
            System.out.println("Numero jugado: "+n);
            if(l==0){
                System.out.println("Ganaste el servicio es gratis, ahorraste: "+result);
                result = 0;
            }  
            else {
                System.out.println("Resultado: "+l +" \nVuelve a intentarlo!");
            }
        }
        System.out.println("Valor a pagar : " + result);
        service.saveVehicle(veh);
        
        
        veh = new Vehicle("JNK-124", TypeEnum.CAR);
        service.saveVehicle(veh);
        List<Vehicle> list = service.listVehicles();
        list.forEach(vehicle -> {
            System.out.println(vehicle.toString());
        });
    }
}

