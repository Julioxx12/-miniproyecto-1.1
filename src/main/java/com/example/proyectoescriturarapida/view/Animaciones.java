package com.example.proyectoescriturarapida.view;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.ScaleTransition;
import javafx.animation.Timeline;
import javafx.animation.TranslateTransition;
import javafx.scene.Node;
import javafx.util.Duration;

/**
 * Utility class providing reusable visual animations for UI components.
 * All methods are static and operate on any JavaFX {@link Node}.
 *
 * <p>Animations include zoom, shake (error feedback), and pulse effects
 * to enhance the player's visual experience.</p>
 *
 * @author Julio Cesar
 * @version 2.0
 */
public class Animaciones {

    /**
     * Applies a zoom-in (scale) animation to the given node.
     * Used to emphasize word changes and positive feedback.
     *
     * @param nodo The {@link Node} to animate.
     */
    public static void zoom(Node nodo) {
        ScaleTransition st = new ScaleTransition(Duration.millis(300), nodo);
        st.setFromX(0.5);
        st.setFromY(0.5);
        st.setToX(1.0);
        st.setToY(1.0);
        st.play();
    }

    /**
     * Applies a horizontal shake animation to the given node.
     * Used as visual feedback when the player types an incorrect word
     * or when time expires.
     *
     * @param nodo The {@link Node} to shake.
     */
    public static void sacudon(Node nodo) {
        TranslateTransition tt = new TranslateTransition(Duration.millis(50), nodo);
        tt.setFromX(-8);
        tt.setToX(8);
        tt.setCycleCount(6);
        tt.setAutoReverse(true);
        tt.setOnFinished(e -> nodo.setTranslateX(0));
        tt.play();
    }

    /**
     * Applies a brief pulse (scale up and back) animation to the given node.
     * Used to draw attention to the feedback label on success.
     *
     * @param nodo The {@link Node} to pulse.
     */
    public static void pulso(Node nodo) {
        Timeline tl = new Timeline(
            new KeyFrame(Duration.ZERO,
                new KeyValue(nodo.scaleXProperty(), 1.0),
                new KeyValue(nodo.scaleYProperty(), 1.0)),
            new KeyFrame(Duration.millis(150),
                new KeyValue(nodo.scaleXProperty(), 1.3),
                new KeyValue(nodo.scaleYProperty(), 1.3)),
            new KeyFrame(Duration.millis(300),
                new KeyValue(nodo.scaleXProperty(), 1.0),
                new KeyValue(nodo.scaleYProperty(), 1.0))
        );
        tl.play();
    }
}
