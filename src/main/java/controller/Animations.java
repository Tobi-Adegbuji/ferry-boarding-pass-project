package main.java.controller;

import javafx.animation.FadeTransition;
import javafx.scene.Node;
import javafx.util.Duration;

public class Animations {

    public void fadeIn(Object object, double milliseconds){
        FadeTransition fade = new FadeTransition();
        fade.setDuration(Duration.millis(milliseconds));
        fade.setFromValue(0);
        fade.setToValue(10);
        fade.setNode((Node)object);
        fade.play();
    }


}
