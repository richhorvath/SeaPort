/*
 * File: SeaPortProgram.java
 * Author: Richard Horvath
 * Date: 2/25/19
 * Purpose: Generates GUI for SeaPortProgram, contains a menubar, open button, 
 *          search button and text areas to display text file
 *          New additions include sorting methods and support gui components 
 *          as well as useing a HashMap for more efficient index searches
 */

package seaport;

import java.io.File;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
/************************************TODO****************************
 * 
 */
/**
 *
 * @author richh
 */
public class SeaPortProgram extends Application {
    
    @Override
    public void start(Stage primaryStage) {
        ScrollPane jobPane = new ScrollPane();
        VBox jobsBox = new VBox();
        World world = new World(jobsBox);
        BorderPane pane = new BorderPane();
        FlowPane flow = new FlowPane();
        flow.setAlignment(Pos.TOP_CENTER);
        
        //creates menu named file
        final Menu menu1 = new Menu("File");
        //create menu bar menuItem adn attach to bar
        MenuBar menuBar = new MenuBar();
        MenuItem load = new MenuItem("Open");
        menu1.getItems().add(load);
        menuBar.getMenus().add(menu1);
        //attach menu to a VBox
        VBox menuBox = new VBox();
        menuBox.getChildren().add(menuBar);
        
          //create the text area to display the file information
        Label seaPortLabel = new Label("SeaPort File");
        TextArea seaPortTextArea = new TextArea();
        seaPortTextArea.setPrefWidth(600);
        seaPortTextArea.setPrefHeight(800);
        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setContent(seaPortTextArea);
        VBox fileBox = new VBox();
        fileBox.alignmentProperty().setValue(Pos.CENTER);
        fileBox.getChildren().addAll(seaPortLabel,scrollPane);
        
  
        
        
        //search bar
        Label searchLabel = new Label("Search:");
        TextField searchField = new TextField();
        VBox searchBox = new VBox();
        searchBox.setSpacing(10);
        searchBox.getChildren().addAll(searchLabel, searchField);
        
        //Create radiobutton  togglegroup
        ToggleGroup group = new ToggleGroup();
        //create radio buttons and assign to group
        RadioButton indexButton = new RadioButton("Index");
        indexButton.setToggleGroup(group);
        RadioButton parentButton = new RadioButton("Parent");
        parentButton.setToggleGroup(group);
        RadioButton nameButton = new RadioButton("Name");
        nameButton.setToggleGroup(group);
        
        //set index button to true
        indexButton.setSelected(true);
        //create a hbox set specing to 10 and add all buttons
        HBox radioBox = new HBox();
        radioBox.setSpacing(10);
        radioBox.getChildren().addAll(indexButton, parentButton, nameButton);
        
        //search button
        Button searchButton = new Button("Search");
        //TODO make search hightlight text in main text area instead
        
        //result text area
        Label resultLabel = new Label("Result");
        TextArea resultField = new TextArea();
        resultField.setPrefWidth(50);
        resultField.setPrefHeight(100);
        VBox resultBox = new VBox();
        resultBox.setSpacing(5);
        resultBox.alignmentProperty().setValue(Pos.CENTER);
        resultBox.getChildren().addAll(resultLabel, resultField);
        
        //Sorting Options
        Label sortLabel = new Label("Sorting Options");
        //radio Buttons for ascending and descending
        RadioButton ascendingButton = new RadioButton("Ascending");
        ascendingButton.setSelected(true);
        RadioButton descendingButton = new RadioButton("Desscending");
        //hbox to keep buttons on same line
        HBox radioBox1 = new HBox(10);
        //addbuttons to radiobox1 and make togglegroup 
        radioBox1.getChildren().addAll(ascendingButton, descendingButton);
        ToggleGroup directionGroup = new ToggleGroup();
        ascendingButton.setToggleGroup(directionGroup);
        descendingButton.setToggleGroup(directionGroup);
        
        //Combo box with sorting options
        ComboBox dropDown = new ComboBox();
        dropDown.getItems().addAll("Que by name", 
                          "Ships by name", 
                          "Person by name",
                          "Ships by weight",
                          "Ships by length",
                          "Ships by width",
                          "Ships by draft");
 
        //VBoX to organize right panel
        VBox rightPanel = new VBox();
        rightPanel.setSpacing(10);
        rightPanel.alignmentProperty().setValue(Pos.CENTER);
        rightPanel.getChildren().addAll(searchBox,radioBox,searchButton,resultBox, sortLabel,radioBox1,dropDown);
        
 
       //create another flow pane to center and balance layout between center and right
        FlowPane rightFlow = new FlowPane();
        jobPane.setContent(jobsBox);
        jobPane.setFitToWidth(true);
        jobPane.setHbarPolicy(ScrollBarPolicy.NEVER);
        jobPane.setVbarPolicy(ScrollBarPolicy.ALWAYS);
        jobPane.setPrefViewportHeight(500);
        rightFlow.alignmentProperty().setValue(Pos.TOP_CENTER);
        rightFlow.setPrefWidth(200);
        rightFlow.getChildren().addAll(rightPanel,jobPane);
        
        
        //open file listener
        load.setOnAction((event)->{
            FileChooser fileChooser = new FileChooser();
            File selectedFile = fileChooser.showOpenDialog(null);
            world.readFile(selectedFile);
            seaPortTextArea.setText(world.toString());
            world.init=true;
         
        });
        
        //search button listener 
        searchButton.setOnAction((event)->{
            //if index button is selected, performs index search
            if(indexButton.isSelected()){
                resultField.setText("");
                resultField.setText(world.searchByIndex(Integer.parseInt(searchField.getText())));
            }
            //if parent button is selected performs search
            if(parentButton.isSelected()){
                resultField.setText("");
                resultField.setText(world.searchByParent(Integer.parseInt(searchField.getText())));
            }
            if(nameButton.isSelected()){
                resultField.setText("");
                resultField.setText(world.searchByName(searchField.getText()));
            }
        });
        
        // event handler for drop down sorting 
        dropDown.setOnAction((event)->{
            try{
                //if ascending is selected 
                 if(ascendingButton.isSelected()){
                    if(dropDown.getValue().toString().equalsIgnoreCase("que by name")){
                        world.getPort().sortQueByNameAscending();
                        seaPortTextArea.setText(world.toString());
                    }
                    if(dropDown.getValue().toString().equalsIgnoreCase("ships by name")){
                        world.getPort().sortShipsByNameAscending();
                        seaPortTextArea.setText(world.toString());
                    }
                    if(dropDown.getValue().toString().equalsIgnoreCase("person by name")){
                        world.getPort().sortPersonByNameAscending();
                        seaPortTextArea.setText(world.toString());
                    }
                    if(dropDown.getValue().toString().equalsIgnoreCase("Ships by weight")){
                        world.getPort().sortShipsByWeightAscending();
                        seaPortTextArea.setText(world.toString());
                    }
                    if(dropDown.getValue().toString().equalsIgnoreCase("Ships by widtht")){
                        world.getPort().sortShipsByWidthAscending();
                        seaPortTextArea.setText(world.toString());
                    }
                    if(dropDown.getValue().toString().equalsIgnoreCase("Ships by length")){
                        world.getPort().sortShipsByLengthAscending();
                        seaPortTextArea.setText(world.toString());
                    }
                    if(dropDown.getValue().toString().equalsIgnoreCase("Ships by draft")){
                        world.getPort().sortShipsByDraftAscending();
                        seaPortTextArea.setText(world.toString());
                    }
                 }
            //if descending is selected
            else if(descendingButton.isSelected()){
                if(dropDown.getValue().toString().equalsIgnoreCase("que by name")){
                    world.getPort().sortQueByNameDescending();
                    seaPortTextArea.setText(world.toString());
                }
                if(dropDown.getValue().toString().equalsIgnoreCase("ships by name")){
                    world.getPort().sortShipsByNameDescending();
                    seaPortTextArea.setText(world.toString());
                }
                if(dropDown.getValue().toString().equalsIgnoreCase("person by name")){
                    world.getPort().sortPersonByNameDescending();
                    seaPortTextArea.setText(world.toString());
                }
                if(dropDown.getValue().toString().equalsIgnoreCase("Ships by weight")){
                    world.getPort().sortShipsByWeightDescending();
                    seaPortTextArea.setText(world.toString());
                }
                if(dropDown.getValue().toString().equalsIgnoreCase("Ships by widtht")){
                    world.getPort().sortShipsByWidthDescending();
                    seaPortTextArea.setText(world.toString());
                }
                if(dropDown.getValue().toString().equalsIgnoreCase("Ships by length")){
                    world.getPort().sortShipsByLengthDescending();
                    seaPortTextArea.setText(world.toString());
                }
                if(dropDown.getValue().toString().equalsIgnoreCase("Ships by draft")){
                    world.getPort().sortShipsByDraftDescending();
                    seaPortTextArea.setText(world.toString());
                }
                
                //if for some reason a improper selection is made
            }else{
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("");
                alert.setHeaderText(null);
                alert.setContentText("Improper Selection made");
                alert.showAndWait();
            }
                //if there is no data loaded and a option is made
            }catch(NullPointerException ex){
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("");
                alert.setHeaderText(null);
                alert.setContentText("Cannot find option requested");
                alert.showAndWait();
                ex.printStackTrace();
                
            }
           
        });
     
  
        
      
        
        //add everything to to the main pane
        flow.getChildren().add(fileBox);
        pane.setTop(menuBox);
        pane.setRight(rightFlow);
        pane.setCenter(flow);
     
        primaryStage.setOnCloseRequest((event)->{
            Platform.exit();
            System.exit(0);
        });
        Scene scene = new Scene(pane, 1000, 1000);
        primaryStage.setTitle("Seaport Program");
        primaryStage.setScene(scene);
        primaryStage.show();
    }//end of launch method

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
   
    
}//end of SeaPortProgram.java
