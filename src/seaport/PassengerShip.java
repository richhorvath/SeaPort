/*
 * File: PassengerShip.java
 * Author: Richard Horvath
 * Date: 1/27/19
 * Purpose: models a passenger ship, contains extra fields from ship and uses ship
 *          super contstructor
 */
package seaport;

import java.util.HashMap;
import java.util.Scanner;

/**
 *
 * @author richh
 */
public class PassengerShip extends Ship {
    private int numberOfOccupiedRooms;
    private int numberOfPassengers;
    private int numberOfRooms;

    public PassengerShip(Scanner sc, HashMap portMap, HashMap shipMap, HashMap dockMap) {
        super(sc, portMap, shipMap, dockMap);
        if(sc.hasNextInt()) numberOfPassengers = sc.nextInt();
        if(sc.hasNextInt()) numberOfRooms = sc.nextInt();
        if(sc.hasNextInt()) numberOfOccupiedRooms = sc.nextInt();
    
    }
    
    public String toString () {
        return "Passenger Ship : "+super.toString();
   } // end method toString
}//end of PassengerShip.java
