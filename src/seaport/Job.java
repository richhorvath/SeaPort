/*
 * File: Job.java
 * Author: Richard Horvath
 * Date: 2/24/19
 * Purpose; creates a progressbars for each job and begins a timer that counters every second
 *          Moving the progress bar closer to completion. 
 */
package seaport;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

/**
 *
 * @author richh
 */
public class Job extends Thing implements Runnable {
   private double jobDuration;
   private boolean jobDone = false;
   private ArrayList<String> jobReq = new ArrayList<String>();
   private ProgressBar pb = new ProgressBar();
   private HBox progressBox = new HBox();
   private VBox jobsBox = new VBox();
   private Button resume = new Button("Cancel");
   private Button pause = new Button ("Pause");
   private double seconds = 0;
   private ArrayList<Person> workers = new ArrayList();
   private HashMap<Integer, Ship> shipMap;
   private Thread thread;
    private boolean noKillFlag = true;
    private boolean goFlag = false;
    private boolean dockedShip = false;
    private Ship ship;
    private boolean hasWorkers = false;
    private World world;
    
    
    
    public Job (World world, HashMap<Integer, Ship>shipMap, Scanner sc, VBox jobsBox){
       super(sc);
       ship = shipMap.get(super.getParent());
        jobDuration = sc.nextDouble();
        this.jobsBox = jobsBox;
        this.shipMap =  shipMap;
        this.world = world;
        while(sc.hasNext()){
            String req = sc.next();
            if(req != null && req.length()>0)
                jobReq.add(req);
            System.out.println(req);
        }
       
        Label jobName = new Label(super.getName());
        pb.setProgress(seconds/100);
        progressBox.getChildren().addAll(jobName, pb, pause, resume);
        jobsBox.getChildren().add(progressBox);
         pause.setOnAction((event)->{
                            goFlag = !goFlag;
                        });
                        resume.setOnAction((event)->{
                            jobDone = true;
                            ship = shipMap.get(super.getParent());
                            ship.releaseShip();
                            pb.setStyle("-fx-accent: green");
                        });
                       new Thread(this).start();
    }
    public void assignWorkers(ArrayList<Person> workers){
        System.out.println("workers assigned");
        this.workers = workers;
        for(Person worker: workers){
            worker.hire();
            System.out.println("workers hired");
        }
    }
    
    public double getDuration(){
        return jobDuration;
    }
    public ArrayList<String> getRequirements(){
        return jobReq;
    }

    @Override
    public void run() {
        while (world.init!=true){
            try {
                Thread.sleep(1000);
            } catch (InterruptedException ex) {
                Logger.getLogger(Job.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        while(!ship.isDocked()){
            try {
                Thread.sleep(100);
            } catch (InterruptedException ex) {
                Logger.getLogger(Job.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        while(ship.isDocked()&&!hasWorkers){
                 try {
                     ship.getWorkers(jobReq, this);
                     Thread.sleep(100);
                 } catch (InterruptedException ex) {
                     Logger.getLogger(Job.class.getName()).log(Level.SEVERE, null, ex);
                 }
             }
        
        while(ship.isDocked()&&hasWorkers){
             goFlag = true;
                 if(shipMap.get(super.getParent()).isDocked()&&hasWorkers){
                double start = System.currentTimeMillis();
                while(!jobDone){
                        try {
                            if(goFlag){
                            seconds = (System.currentTimeMillis()-start)/1000;
                            String time = String.valueOf(seconds);
                            pb.setProgress(seconds/jobDuration);
                            pb.setStyle("-fx-accent: red");
                            Thread.sleep(100); 
                            if(seconds >= jobDuration){
                                System.out.println("Job done inside job class");
                                pb.setStyle("-fx-accent: green");
                                ship.jobDone();
                                jobDone=true;
                                }
                            hasWorkers=false;
                            }else{
                           pb.setStyle("-fx-accent: yellow");  
                        }
                    } catch (InterruptedException e1) {
                        e1.printStackTrace();
                    }
                }
                hasWorkers=false;
              
                 }
                 
                 
           
            
        }
          for(Person worker: workers){
                    worker.release();
                }
        
              
    }
    public boolean isJobDone(){
        return jobDone;
    }
    public void setGoFlag(Boolean goFlag){
        this.goFlag = goFlag;
    }
    public synchronized void hasWorkers(Boolean bool){
        hasWorkers = true;
    }
 
    
}
  