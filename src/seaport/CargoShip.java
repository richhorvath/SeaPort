/*
 * File: CargoShip.java
 * Author: Richard Horvath
 * Date: 1/27/19
 * Purpose: models a cargoship to include value,volume and weight. calls ship super constructor
 *          
 */
package seaport;

import java.util.HashMap;
import java.util.Scanner;

/**
 *
 * @author richh
 */
public class CargoShip extends Ship {
    private double cargoValue;
    private double cargoVolume;
    private double cargoWeight;

    public CargoShip(Scanner sc, HashMap portMap, HashMap shipMap, HashMap dockMap) {
        super(sc,portMap, shipMap, dockMap);
        if(sc.hasNextDouble()) cargoWeight = sc.nextDouble();
        if(sc.hasNextDouble()) cargoVolume = sc.nextDouble();
        if(sc.hasNextDouble()) cargoValue = sc.nextDouble();
    }
    
    @Override
    public String toString(){
        return "Cargo Ship: "+ super.toString();
    }
    
}//end of CargoShip.java
