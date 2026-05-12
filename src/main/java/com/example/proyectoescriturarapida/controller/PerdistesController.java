package com.example.proyectoescriturarapida.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;

/**
 * Controller for the Game Over screen.
 * Displays the player's final performance metrics including level reached,
 * cause of failure, and remaining time. Handles the restart action.
 *
 * @author Julio Cesar
 * @version 2.0
 */
public class PerdistesController {

    /** Label displaying the final level reached by the player. */
    @FXML private Label lblNiveles;

    /** Label displaying the total score (equals level reached). */
    @FXML private Label lblPuntos;

    /** Label displaying the cause of game over (incorrect word or timeout). */
    @FXML private Label lblCausa;

    /** Label displaying remaining time at the moment of failure (if applicable). */
    @FXML private Label lblTiempoRestante;

    /**
     * Populates the Game Over screen with the player's final statistics.
     * Called by {@link GameController} immediately after loading this scene.
     *
     * @param nivel          The last level successfully completed.
     * @param causaFallo     A description of why the game ended (e.g., "Tiempo agotado").
     * @param tiempoRestante Seconds remaining when the game ended; {@code 0} if timed out.
     */
    public void mostrarEstadisticas(int nivel, String causaFallo, int tiempoRestante) {
        if (lblNiveles != null) {
            lblNiveles.setText("Nivel alcanzado: " + nivel);
        }
        if (lblPuntos != null) {
            lblPuntos.setText("Puntuación: " + nivel);
        }
        if (lblCausa != null) {
            lblCausa.setText("Causa: " + causaFallo);
        }
        if (lblTiempoRestante != null) {
            if (tiempoRestante > 0) {
                lblTiempoRestante.setText("Tiempo restante: " + tiempoRestante + "s");
            } else {
                lblTiempoRestante.setText("Tiempo restante: —");
            }
        }
    }

    /**
     * Handles the restart button action. Loads the main game scene and resets
     * all game state by instantiating a fresh {@link GameController}.
     *
     * @param event The {@link ActionEvent} triggered by the restart button.
     */
    @FXML
    void handleRestart(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(
                getClass().getResource("/com/example/proyectoescriturarapida/EscrituraRapida.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (Exception e) {
            System.err.println("Error: Could not restart the game.");
            e.printStackTrace();
        }
    }
}
