/*
 * File: SeaPort.java
 * Author: Richard Horvath
 * Date: 1/27/19
 * Purpose: initializes ArrayList for each object and que. Sets objects into respective
 *          List. Contains sorting methods and Comparators for sorting ships by name
 *          weight, length, width, draft. In both ascending and descending orders
 */
package seaport;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Scanner;
import java.util.Stack;
import java.util.concurrent.locks.ReentrantLock;

/**
 *
 * @author richh
 */
public class SeaPort extends Thing {
    private ArrayList<Dock> docks = new ArrayList<Dock>();
    protected  ArrayList<Ship> que = new ArrayList<Ship>();
    private  ArrayList<Ship> ships = new ArrayList<Ship>();
    private ArrayList<Person> persons = new ArrayList<Person>();
    protected Stack queStack = new Stack();
  
    
    public SeaPort (Scanner sc){
        super(sc);
    }
  
    public synchronized ArrayList<Person> availableWorkers(ArrayList<String> jobReqs){
       ArrayList<Person> available = new ArrayList<Person>();
       for(Person person:persons){
           for(String jobReq:jobReqs){
               if(person.isAvailable()&&person.hasSkill(jobReq))
                 available.add(person);
               if(available.size()==jobReqs.size())
                   return available;
           }
       }
       return null;
    }
        
    
    
    //creates dock and sets to dock arraylist
    public void setDock(Dock dock){
        docks.add(dock);
    }
    //sets ships to arrayList and determines if they belong to a dock
    public void setShips(Ship newShip){
                if(!que.contains(newShip))
                    que.add(newShip);
                else
                    return;
    }
    public void setAllShips(Ship newShip){
        ships.add(newShip);
    
    }
    //compares index and parent
    public boolean compareID(Thing index, Thing parent){
        if(index.getIndex()==parent.getParent())
            return true;
        else
            return false;
    }
    
//adds person
    public void setPerson(Person person){
        persons.add(person);
    }
    
    
    public String sortQueByNameAscending(){
        Collections.sort(que);
        String st = toString();
        return st;
    }
    public String sortQueByNameDescending(){
        Collections.sort(que);
        Collections.reverse(que);
        String st = toString();
        return st;
    }
    public String sortDocksByNameAsending(){
        Collections.sort(docks);
        String st = toString();
        return st;
    }
    public String sortDocksByNameDescending(){
        Collections.sort(docks);
        Collections.reverse(docks);
        String st = toString();
        return st;
    }
    public String sortShipsByNameAscending(){
        Collections.sort(ships);
        String st = toString();
        return st;
    }
    public String sortShipsByNameDescending(){
        Collections.sort(ships);
        Collections.reverse(ships);
        String st = toString();
        return st;
    }
     public String sortPersonByNameAscending(){
        Collections.sort(persons);
        String st = toString();
        return st;
    }
     public String sortPersonByNameDescending(){
        Collections.sort(persons);
        Collections.reverse(persons);
        String st = toString();
        return st;
    }
   
   public String sortShipsByWeightDescending(){
      Collections.sort(que, new SortByWeightDescending());
        String st = toString();
        return st;
   }
    public String sortShipsByWeightAscending(){
      Collections.sort(que, new SortByWeightDescending().reversed());
      String st = toString();
      return st;
   }
   
   public String sortShipsByLengthDescending(){
      Collections.sort(que, new SortByLengthDescending());
      String st = toString();
      return st;
   }
   
    public String sortShipsByLengthAscending(){
      Collections.sort(que, new SortByLengthDescending().reversed());
      String st = toString();
      return st;
   }
   public String sortShipsByWidthDescending(){
      Collections.sort(que, new SortByWidthDescending());
      String st = toString();
      return st;
   }
   
   public String sortShipsByWidthAscending(){
      Collections.sort(que, new SortByWidthDescending().reversed());
      String st = toString();
      return st;
   }
   public String sortShipsByDraftDescending(){
      Collections.sort(que, new SortByDraftDescending());
      String st = toString();
      return st;
   }
    public String sortShipsByDraftAscending(){
      Collections.sort(que, new SortByDraftDescending().reversed());
      String st = toString();
      return st;
   }
    
    
    
    @Override
    public String toString () {
      String st = "\n\nSeaPort: " + super.toString();
      for (Dock md: docks) st += "\n" + md;
      st += "\n\n --- List of all ships in que:";
      for (Ship ms: que ) st += "\n   > " + ms;
      st += "\n\n --- List of all ships:";
      for (Ship ms: ships) st += "\n   > " + ms;
      st += "\n\n --- List of all persons:";
      for (Person mp: persons) st += "\n   > " + mp;
      return st;
   } // end method toString
    
    class SortByWeightDescending implements Comparator<Ship>{

        @Override
        public int compare(Ship t, Ship t1) {
            if(t.getWeight()<t1.getWeight())
                return 1;
            else
                return -1;
        }
    }// end of sortByWeightDescending class

    
    class SortByLengthDescending implements Comparator<Ship>{

        @Override
        public int compare(Ship t, Ship t1) {
            if(t.getLength()<t1.getLength())
                return 1;
            else
                return -1;
        }
    }//end of SortByLengthDescending class
    
    class SortByWidthDescending implements Comparator<Ship>{

        @Override
        public int compare(Ship t, Ship t1) {
            if(t.getWidth()<t1.getWidth())
                return 1;
            else
                return -1;
        }
    }
    
    class SortByDraftDescending implements Comparator<Ship>{

        @Override
        public int compare(Ship t, Ship t1) {
            if(t.getDraft()<t1.getDraft())
                return 1;
            else
                return -1;
        }
    }
    
    
    

}//end of SeaPort.java
