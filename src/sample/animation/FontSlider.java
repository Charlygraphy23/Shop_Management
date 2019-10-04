package sample.animation;

import javafx.animation.Interpolator;
import javafx.animation.TranslateTransition;
import javafx.scene.Node;
import javafx.util.Duration;

public class FontSlider {

   private TranslateTransition translateTransition;

    public FontSlider(Node node) {
        translateTransition=new TranslateTransition(Duration.millis(200),node);
        translateTransition.setToX(698);
        translateTransition.setInterpolator(Interpolator.EASE_IN);
        translateTransition.setCycleCount(1);
        translateTransition.setAutoReverse(false);
    }

    public void getTransition(){
        translateTransition.play();
    }
}
