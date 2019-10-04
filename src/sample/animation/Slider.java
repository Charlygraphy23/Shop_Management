package sample.animation;

import javafx.animation.Interpolator;
import javafx.animation.TranslateTransition;
import javafx.scene.Node;
import javafx.util.Duration;

public class Slider {
    private TranslateTransition translateTransition;

    public Slider(Node node) {
        translateTransition=new TranslateTransition(Duration.millis(300),node);
        translateTransition.setFromX(1026);
        translateTransition.setToX(0);
        translateTransition.setInterpolator(Interpolator.EASE_IN);
        translateTransition.setCycleCount(1);
        translateTransition.setAutoReverse(false);
    }

    public void getTransition(){
        translateTransition.play();
    }
}

