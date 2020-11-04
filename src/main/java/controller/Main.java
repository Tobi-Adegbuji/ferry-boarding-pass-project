package main.java.controller;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import main.java.dao.Dao;
import main.java.dao.DataBootStrap;
import main.java.model.BoardingPass;
import main.java.model.Ferry;
import main.java.model.Gender;
import main.java.model.Passenger;

import java.io.File;
import java.net.URL;
import java.util.Date;

public class Main extends Application {

    @FXML
    private ChoiceBox<String> originChoiceBox;

    @FXML
    private ChoiceBox<String> destinationChoiceBox;

    @FXML
    private ChoiceBox<String> genderChoiceBox;

    @FXML
    private ChoiceBox<String> departureTime;

    @FXML
    ObservableList<String> locationsList = FXCollections
            .observableArrayList("Sapelo Island","St. Catherine's Island","Little Tybee Island");


    @Override
    public void start(Stage primaryStage) throws Exception{


        URL url = new File("src/main/java/controller/home.fxml").toURI().toURL();
        Parent root = FXMLLoader.load(url);
        Scene scene = new Scene(root);
        primaryStage.setTitle("Ferry Boarding Pass");
        primaryStage.getIcons().add(new Image("file:logo.png"));
        primaryStage.setResizable(false);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    @FXML
    public void initialize(){
        destinationChoiceBox.setItems(locationsList);
        originChoiceBox.setItems(locationsList);
        genderChoiceBox.setItems(FXCollections.observableArrayList("MALE","FEMALE"));

        originChoiceBox.getSelectionModel().selectedItemProperty()
                .addListener((v, oldValue, newValue) -> {
                    if(!newValue.equals(destinationChoiceBox.getValue()) && destinationChoiceBox.getValue() != null){
                        departureTime.setDisable(false);
                    }
                    else
                        departureTime.setDisable(true);
                });

        destinationChoiceBox.getSelectionModel().selectedItemProperty()
                .addListener((v, oldValue, newValue) -> {
                    if(!newValue.equals(originChoiceBox.getValue()) && originChoiceBox.getValue() != null){
                        departureTime.setDisable(false);
                    }
                    else
                        departureTime.setDisable(true);
                });
        DataBootStrap.bootStrapData();

    }


    public static void main(String[] args) {
        launch(args);
    }
}
