package sample.animation;

import javafx.animation.Interpolator;
import javafx.animation.TranslateTransition;
import javafx.scene.Node;
import javafx.util.Duration;

public class ReverseFontSlider {

        private TranslateTransition translateTransition;

        public ReverseFontSlider(Node node) {
            translateTransition=new TranslateTransition(Duration.millis(200),node);
            translateTransition.setInterpolator(Interpolator.EASE_IN);
            translateTransition.setToX(0);
            translateTransition.setCycleCount(1);
            translateTransition.setAutoReverse(false);
        }

        public void getTransition(){
            translateTransition.play();
        }
}

