/*
 * File: Ship.java
 * Author: Richard Horvath
 * Date: 1/27/19
 * Purpose: Models a ship to include portimes, weight, length etc.
 *          Added get methods and updated toString to include weight,length etc. 
 */
package seaport;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;
import java.util.Set;

/**
 *
 * @author richh
 */
public class Ship extends Thing{
    private PortTime arrivalTime;
    private PortTime dockTime;
    private ArrayList<Job> jobs = new ArrayList<Job>();
    private HashMap portMap, shipMap, dockMap;
    private double weight;
    private double length;
    private double width;
    private double draft;
    private boolean docked = false;
    private boolean jobsDone = false;
    private int numOfJobs = 0;
   
    
    
    public Ship(Scanner sc, HashMap portMap, HashMap shipMap, HashMap dockMap){
        super(sc);
        if(sc.hasNextDouble()) weight = sc.nextDouble();
        if(sc.hasNextDouble()) length = sc.nextDouble();
        if(sc.hasNextDouble()) width = sc.nextDouble();
        if(sc.hasNextDouble()) draft = sc.nextDouble();
        this.portMap = portMap;
        this.shipMap = shipMap;
        this.dockMap = dockMap;
    }
    public void addJobCount(){
        numOfJobs++;
    }
    public double getWeight(){
        return weight;
    }
    public double getLength(){
        return length;
    }
    public double getWidth(){
        return width;
    }
    public double getDraft(){
        return draft;
    }
    public void setJobs(Job job){
        jobs.add(job);
    }
    public ArrayList<Job> getJobs(){
        return jobs;
    }
    public boolean isDocked(){
        return docked;
    }
    public void dockShip(Boolean docked){
        this.docked = docked;
    }
    public void setJobCompletion(Boolean jobsDone){
        this.jobsDone = jobsDone;
    }
    public synchronized void releaseShip(){
            Dock dock = (Dock) (dockMap.get(super.getParent()));
            SeaPort port = (SeaPort) portMap.get(dock.getParent());
            super.setParent(port.getIndex());
            docked = false;
            if(!port.que.isEmpty()){
                Ship nextShip = (Ship) port.que.remove(0);
                dock.addShip(nextShip);
                System.out.println("Ship Released");
            }
            
    }
    public boolean allJobsDone(){
        return jobsDone;
    }
    public synchronized void jobDone(){
        if(numOfJobs==1){
            jobsDone = true;
            releaseShip();
        }else
        numOfJobs--;
        System.out.println(super.getName() +" JobDone called, num of jobs = " + numOfJobs);
    }
    public synchronized void getWorkers(ArrayList<String> jobReqs, Job job){
        Dock dock = (Dock) (dockMap.get(super.getParent()));
        SeaPort port = (SeaPort) portMap.get(dock.getParent());
        ArrayList<Person> workers = port.availableWorkers(jobReqs);
        if(workers==null){
            job.hasWorkers(false);
        }
        else{
            job.assignWorkers(workers);
            job.hasWorkers(true);
        }
            
    }
    

    
   @Override 
    public String toString(){
        return super.toString() +  " Weight: " + weight +" Length: " + length +
                " Width: "+ width +" Draft: " +draft;
    }
    
    

    
}//end of Ship.java
