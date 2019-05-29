/*
 * File: Person.java
 * Author: Richard Horvath
 * Date: 1/27/19
 * Purpose: Models a person includes a String for skills
 */
package seaport;

import java.util.Scanner;

/**
 *
 * @author richh
 */
public class Person extends Thing{
    private String skill;
    private boolean isAvailable = true;

    public Person(Scanner sc) {
        super(sc);
        skill = sc.next();
    }
    
    public String getSkill() {
        return skill;
    }
    public String toString(){
        return "Person: " + super.toString()+ " " +skill;
    }
    synchronized public Person hire(){
        if(isAvailable){
            isAvailable = false;
            return this;
        }
        return null;
    }
    synchronized public void release(){
        if(!isAvailable){
            isAvailable = true;
            
        }
        System.out.println("worker released");
    }
    public boolean isAvailable(){
        return isAvailable;
    }
    
    public boolean hasSkill(String req){
        return req.toLowerCase().matches(skill.toLowerCase());
    }
    
}//end of person.java
