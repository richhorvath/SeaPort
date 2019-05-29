/*
 * File: Thing.java
 * Author: Richard Horvath
 * Date: 1/25/19
 * Purpose: abstract class contains the basic fields all child classes will contain
 *          Used to generate objects and toString, updated compareTo method to compare
 *          name variable
 */
package seaport;

import java.util.Scanner;

/**
 *
 * @author richh
 */

public abstract class Thing implements Comparable<Thing>{
    private String name;
    private int index;
    private int parent;
   
    public Thing(){
        
    }
    public Thing(Scanner sc){
        name = sc.next();
        index = sc.nextInt();
        parent = sc.nextInt();
    }
    
    
    
    @Override
    public int compareTo(Thing t) {
        String name = t.getName();
       return this.name.compareTo(name);
    }
    
    
    
    @Override
    public String toString(){
        return name + " " + index;
    }
    
    public String getName() {
        return name;
    }
    
    public int getIndex(){
        return index;
    }
    
    public int getParent(){
        return parent;
    }
    public void setParent(int parentIndex){
        this.parent = parentIndex;
    }
    
}// end of thing.java class
