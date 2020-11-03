package main.java.controller;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
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

    @Override
    public void start(Stage primaryStage) throws Exception{
        URL url = new File("src/main/java/controller/home.fxml").toURI().toURL();
        Parent root = FXMLLoader.load(url);
        Scene scene = new Scene(root);
        primaryStage.setTitle("Ferry Boarding Pass");
        primaryStage.setResizable(false);
        primaryStage.setScene(scene);
        primaryStage.show();
    }


    public static void main(String[] args) {
        DataBootStrap.bootStrapData();
        launch(args);
    }
}
