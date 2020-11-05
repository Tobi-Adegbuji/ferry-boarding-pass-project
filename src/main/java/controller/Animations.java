package main.java.controller;

import javafx.animation.FadeTransition;
import javafx.scene.Node;
import javafx.util.Duration;

public class Animations {

    public void fadeOut(Object object){
        //Instantiating FadeTransition class
        FadeTransition fade = new FadeTransition();


        //setting the duration for the Fade transition
        fade.setDuration(Duration.millis(2000));

        //setting the initial and the target opacity value for the transition
        fade.setFromValue(0);
        fade.setToValue(10);

//        //setting cycle count for the Fade transition
//        fade.setCycleCount(1000);
//
//        //the transition will set to be auto reversed by setting this to true
//        fade.setAutoReverse(true);

        //setting Circle as the node onto which the transition will be applied
        fade.setNode((Node)object);


        //playing the transition
        fade.play();
    }


}
