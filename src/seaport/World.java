/*
 * File: World.java
 * Author: Richard Horvath
 * Date: 1/27/18
 * Purpose: Generates the world that houses SeaPort objects in a hierarchy using 
 *          arrayList to separate ports. Also contains an arraylist with everything
 *          for searching, a hashmap for searching by index 
 */
package seaport;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.VBox;

/**
 *
 * @author richh
 */

public class World extends Thing{
    private ArrayList<SeaPort> ports = new ArrayList<SeaPort>();
    private ArrayList<Thing> everything = new ArrayList<Thing>();
    private HashMap<Integer, Thing> hashMap = new HashMap<Integer, Thing>();
    private HashMap<Integer, Dock> dockMap = new HashMap<Integer, Dock>();
     private HashMap<Integer, SeaPort> portMap = new HashMap<Integer, SeaPort>();
    private HashMap<Integer, Ship> shipMap = new HashMap<Integer, Ship>();
    private PortTime time;
    private SeaPort currentPort;
    private Dock currentDock;
    private Scanner scan;
    private VBox jobsBox;
    protected Boolean init = false;
    
    
    
    public World(VBox jobsBox){
        this.jobsBox = jobsBox;
    }
    
    //reads file generates alerts if something goes wrong
    public void readFile(File file){
        try{
            scan = new Scanner(file);
            while(scan.hasNextLine()){
                String line = scan.nextLine();
                if(line.startsWith("//")){
                    continue;
                }
                process(line);
            }
        }catch(IOException e){
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("");
            alert.setHeaderText(null);
            alert.setContentText("Oops the input was not read properly");
            alert.showAndWait();
            e.printStackTrace();
        }catch(NullPointerException e){
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("");
            alert.setHeaderText(null);
            alert.setContentText("Oops the input was not read properly");
            alert.showAndWait();
            e.printStackTrace();
            
        }
    }// end of readFile()
    
    //initializes objects using switch 
    public void process (String st) {
        Scanner sc = new Scanner (st);
        if (!sc.hasNext()){
            return;
        }else{
            switch (sc.next()){
                case "port": addPort(sc);
                break;
                case "dock": addDock(sc, portMap);
                break;
                case "pship": addPassengerShip(sc);
                break;
                case "cship": addCargoShip(sc);
                break;
                case "person": addPerson(sc);
                break;
                case "job": addJob(shipMap,dockMap,portMap,sc, this.jobsBox);
                
            }
        }
        
    }
    /**Helper method to start jobs for ports**/

    //add port to port list and everything list
    public void addPort(Scanner sc){
        currentPort = new SeaPort(sc);
        ports.add(currentPort);
        hashMap.put(currentPort.getIndex(), currentPort);
        portMap.put(currentPort.getIndex(), currentPort);
        everything.add(currentPort);
       
    }
    //adds dock to everything and port
    public void addDock(Scanner sc, HashMap portMap){
        Dock dock = new Dock(sc, portMap);
        currentPort = (SeaPort) hashMap.get(dock.getParent());
        currentPort.setDock(dock);
        hashMap.put(dock.getIndex(), dock);
        dockMap.put(dock.getIndex(), dock);
        everything.add(dock);
    }
    //adds passenger ship to port and everything list
    public void addPassengerShip(Scanner sc){
        PassengerShip pShip = new PassengerShip(sc, portMap, shipMap, dockMap);
        if(hashMap.containsKey(pShip.getParent())){
            if(hashMap.get(pShip.getParent()).getIndex()>=10000 && hashMap.get(pShip.getParent()).getIndex() < 20000){
                currentPort = (SeaPort) hashMap.get(pShip.getParent());
                currentPort.setShips(pShip);
                currentPort.setAllShips(pShip);
                
            }
            else{
                currentDock = (Dock) hashMap.get(pShip.getParent());
                currentDock.addShip(pShip);
                currentPort = (SeaPort) hashMap.get(currentDock.getParent());
                currentPort.setAllShips(pShip);
            }
                
                 
        }
        hashMap.put(pShip.getIndex(), pShip);
        shipMap.put(pShip.getIndex(), pShip);
        everything.add(pShip);
    }
    //adds cargoship to port and everything list
    public void addCargoShip(Scanner sc){
        CargoShip cShip = new CargoShip(sc, portMap, shipMap, dockMap);
        if(hashMap.containsKey(cShip.getParent())){
            if(hashMap.get(cShip.getParent()).getIndex()>=10000 && hashMap.get(cShip.getParent()).getIndex() < 20000){
                currentPort = (SeaPort) hashMap.get(cShip.getParent());
                currentPort.setShips(cShip);
                currentPort.setAllShips(cShip);
            }
            else{
                currentDock = (Dock) hashMap.get(cShip.getParent());
                currentDock.addShip(cShip);
                currentPort = (SeaPort) hashMap.get(currentDock.getParent());
                currentPort.setAllShips(cShip);
            }
                
                 
        }
        hashMap.put(cShip.getIndex(), cShip);
        shipMap.put(cShip.getIndex(), cShip);
        everything.add(cShip);
    }
    //adds person to port & everything arraylist
    public void addPerson(Scanner sc){
        Person person = new Person(sc);
        currentPort = (SeaPort) hashMap.get(person.getParent());
        currentPort.setPerson(person);
        hashMap.put(person.getIndex(), person);
        everything.add(person);
    }
    
    public void addJob(HashMap<Integer, Ship>shipMap,HashMap<Integer, Dock>dockMap,HashMap<Integer, SeaPort>portMap,Scanner sc, VBox jobsBox){
        Job job = new Job(this, shipMap, sc, jobsBox);
        Ship currentShip = (Ship) hashMap.get(job.getParent());
        currentShip.setJobs(job);
        currentShip.addJobCount();
    }
    
   
    //performs search by index
    public String searchByIndex(int index){
        if(hashMap.containsKey(index))
            return hashMap.get(index).toString();
        else
            return "Cannot Find index given";
    }
    //performs search by name
    public String searchByName(String name){
        for(Thing things: everything){
           if(things.getName().equalsIgnoreCase(name)){
               return things.toString();
           }
        }
        return "Cannot Find given Name";
    }
    
    //performs searchbyParent
        public String searchByParent(int parent){
        for(Thing things: everything){
           if(things.getParent()== parent){
               System.out.println(things.toString());
               return things.toString();
           }
        }
        return "Cannot Find given parent";
    }
        
        public SeaPort getPort(){
            return currentPort;
        }
    @Override
    public String toString(){ 
        return ports.toString();
    }
    
    
}// end of World.java class
