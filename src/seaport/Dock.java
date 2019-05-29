/*
 * File: Dock.java
 * Author: Richard Horvath
 * Date: 1/27/19
 * Purpose: dock template containing a single ship dock 
 */
package seaport;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

/**
 *
 * @author richh
 */

public class Dock extends Thing{
    private Ship ship;
    private  ArrayList<Job> jobs;
    private boolean docked = false;
    private HashMap portMap;
    private SeaPort parentPort;
    
    public Dock(Scanner sc, HashMap portMap){
        super(sc);
        this.portMap = portMap;
        ship = null;
        parentPort = (SeaPort) portMap.get(super.getParent());
    }
    public void addShip(Ship ship){
        ship.dockShip(true);
        this.ship = ship;
        ship.setParent(super.getIndex());
        docked = true;
    }
    
    public Ship getShip(){
        return ship;
    }
    
    @Override
    public String toString(){
        return "Dock: " + super.toString()+"\n"+
                "  Ship: "+ ship.toString();
    }
    
   
    public boolean isDocked(){
        return docked;
    }
    
    
   
  
    
}//end of Dock.java
