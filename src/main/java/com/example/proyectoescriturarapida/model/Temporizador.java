package com.example.proyectoescriturarapida.model;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.control.Label;
import javafx.util.Duration;

/**
 * Handles the countdown timer logic for the gameplay.
 * Uses JavaFX {@link Timeline} to update the UI label in real-time on the
 * JavaFX Application Thread, eliminating the need for {@code Platform.runLater()}.
 * Executes a callback when the countdown reaches zero.
 *
 * @author Julio Cesar
 * @version 2.0
 */
public class Temporizador implements ITemporizador {

    /** JavaFX timeline that drives the per-second countdown. */
    private Timeline timeline;

    /** Remaining seconds in the current countdown. */
    private int tiempoRestante;

    /** UI label that displays the countdown to the player. */
    private final Label labelTiempo;

    /** Callback executed when the timer reaches zero. */
    private final Runnable alTerminar;

    /**
     * Constructs a new {@code Temporizador} bound to a display label and a completion callback.
     *
     * @param labelTiempo The {@link Label} where the remaining seconds are shown.
     * @param alTerminar  The {@link Runnable} invoked when the countdown reaches zero.
     */
    public Temporizador(Label labelTiempo, Runnable alTerminar) {
        this.labelTiempo = labelTiempo;
        this.alTerminar = alTerminar;
    }

    /**
     * Starts or restarts the countdown from the specified number of seconds.
     * Cancels any previously active timeline before creating a new one.
     * Updates the label each second and triggers {@code alTerminar} at zero.
     *
     * @param segundos Initial countdown value in seconds.
     */
    @Override
    public void iniciar(int segundos) {
        if (timeline != null) timeline.stop();
        this.tiempoRestante = segundos;
        actualizarLabel();

        timeline = new Timeline(new KeyFrame(Duration.seconds(1), e -> {
            tiempoRestante--;
            actualizarLabel();
            if (tiempoRestante <= 0) {
                timeline.stop();
                alTerminar.run();
            }
        }));
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
    }

    /**
     * Stops the countdown immediately without triggering the completion callback.
     * Safe to call even if the timer is not currently running.
     */
    @Override
    public void detener() {
        if (timeline != null) timeline.stop();
    }

    /**
     * Returns the remaining time in seconds.
     *
     * @return Current remaining seconds; {@code 0} if expired or stopped.
     */
    @Override
    public int getTiempoRestante() {
        return tiempoRestante;
    }

    /**
     * Updates the timer label text and applies a visual urgency style
     * when 5 or fewer seconds remain (red color), resetting to white otherwise.
     */
    private void actualizarLabel() {
        labelTiempo.setText("Tiempo: " + tiempoRestante);
        if (tiempoRestante <= 5) {
            labelTiempo.setStyle("-fx-text-fill: #ff4444; -fx-font-family: Impact; -fx-font-size: 24;");
        } else {
            labelTiempo.setStyle("-fx-text-fill: white; -fx-font-family: Impact; -fx-font-size: 24;");
        }
    }
}
