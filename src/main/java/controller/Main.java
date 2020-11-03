package main.java.controller;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import main.java.dao.Dao;
import main.java.model.BoardingPass;
import main.java.model.Gender;
import main.java.model.Passenger;

import java.io.File;
import java.net.URL;
import java.util.Date;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        URL url = new File("src/main/java/controller/home.fxml").toURI().toURL();
        Parent root = FXMLLoader.load(url);
        Scene scene = new Scene(root);
        primaryStage.setTitle("Ferry Ticket");
        primaryStage.setResizable(false);
        primaryStage.setScene(scene);
        primaryStage.show();
    }


    public static void main(String[] args) {
        Dao dao = new Dao();
        Passenger passenger = new Passenger("John","john@email.com","6786789867", Gender.MALE,15);
        Passenger passenger2 = new Passenger("Shelby","shelby@email.com","7707897865", Gender.FEMALE,82);

        dao.createPassenger(passenger);
        dao.createPassenger(passenger2);
        dao.createFerryTicket(new BoardingPass(new Date(),"example","example","example","example",passenger));
        dao.createFerryTicket(new BoardingPass(new Date(),"example","example","example","example",passenger2));
        launch(args);
    }
}
