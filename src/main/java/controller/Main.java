package main.java.controller;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.File;
import java.net.URL;
import java.text.Normalizer;

public class Main extends Application {
   static boolean reBook=false;

    public static void main(String[] args) {

        FormController f=new FormController();
        do {
            launch(args);
        }while(f.reRun());
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        URL url = new File("src/main/java/controller/form.fxml").toURI().toURL();
        Parent root = FXMLLoader.load(url);
        Scene scene = new Scene(root);
        primaryStage.setTitle("GA Ferries");
        primaryStage.getIcons().add(new Image("file:logo.png"));
        primaryStage.setResizable(false);
        primaryStage.setScene(scene);
        primaryStage.show();
        reBook=false;
    }

}
