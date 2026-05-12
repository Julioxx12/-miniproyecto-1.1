package com.example.proyectoescriturarapida.controller;

import com.example.proyectoescriturarapida.view.Animaciones;
import com.example.proyectoescriturarapida.model.IPalabras;
import com.example.proyectoescriturarapida.model.Palabras;
import com.example.proyectoescriturarapida.model.ITemporizador;
import com.example.proyectoescriturarapida.model.Temporizador;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import java.net.URL;

/**
 * Main controller class for the Fast Writing game.
 * Manages gameplay logic including word validation, level progression,
 * difficulty scaling, and scene transitions.
 *
 * <p>Implements event-driven interactions via JavaFX FXML annotations,
 * handling keyboard (Enter key) and mouse (button click) events.</p>
 *
 * <p>Difficulty increases every 5 correct levels by reducing the time
 * per word by 2 seconds, down to a minimum of 2 seconds (per HU-3).</p>
 *
 * @author Julio Cesar
 * @version 2.0
 */
public class GameController {

    /** Label that displays the target word the user must type. */
    @FXML private Label targetWordLabel;

    /** Label that displays the remaining time in seconds. */
    @FXML private Label timerLabel;

    /** Text field where the user enters their typed response. */
    @FXML private TextField typingField;

    /** Label for visual feedback such as "¡Correcto!" or "Tiempo agotado". */
    @FXML private Label feedbackLabel;

    /** Button used to start the game session. */
    @FXML private Button btnIniciar;

    /** Current level, incremented on each correct answer (starts at 1). */
    private int nivelActual = 1;

    /** Base time in seconds granted per word; decreases with difficulty. */
    private int tiempoBase = 20;

    /** Tracks whether the current round ended due to time expiration. */
    private boolean tiempoAgotado = false;

    /** Model responsible for providing random words. */
    private final IPalabras misPalabras = new Palabras();

    /** Timer model for the per-level countdown. */
    private ITemporizador miTemporizador;

    /**
     * Initializes a new game session when the player clicks "Iniciar Juego".
     * Resets the UI, creates a fresh timer, loads the first word, and
     * disables the start button to prevent duplicate sessions.
     *
     * @param event The {@link ActionEvent} triggered by the start button.
     */
    @FXML
    void handleStartGame(ActionEvent event) {
        btnIniciar.setVisible(true);
        typingField.clear();
        typingField.setDisable(false);
        feedbackLabel.setText("");
        feedbackLabel.setStyle("");
        tiempoAgotado = false;

        miTemporizador = new Temporizador(timerLabel, this::handleTimeOut);

        mostrarNuevaPalabra();
        typingField.requestFocus();
        miTemporizador.iniciar(tiempoBase);
    }

    /**
     * Callback invoked by the timer when the countdown reaches zero.
     * Sets the {@code tiempoAgotado} flag and triggers word validation,
     * which will detect an expired timer and navigate to the Game Over screen.
     */
    private void handleTimeOut() {
        tiempoAgotado = true;
        handleSetWord(null);
    }

    /**
     * Loads a new random word into the target label and clears the input field.
     * Applies a zoom animation to emphasize the word change.
     */
    private void mostrarNuevaPalabra() {
        String nuevaPalabra = misPalabras.obtenerAleatoria();
        targetWordLabel.setText(nuevaPalabra);
        typingField.clear();
        Animaciones.zoom(targetWordLabel);
    }

    /**
     * Displays the game rules in an informational dialog window.
     * Uses a standard JavaFX {@code Alert} with the game icon applied.
     *
     * @param event The {@link ActionEvent} triggered by the "¿Cómo se juega?" button.
     */
    @FXML
    void handleMostrarReglas(ActionEvent event) {
        javafx.scene.control.Alert alert = new javafx.scene.control.Alert(
                javafx.scene.control.Alert.AlertType.INFORMATION);
        alert.setTitle("Reglas del Juego");
        alert.setHeaderText("¿Cómo jugar Escritura Rápida?");
        alert.setContentText(
            "1. Presiona 'INICIAR JUEGO' para comenzar el cronómetro.\n" +
            "2. Escribe la palabra que aparece exactamente como se muestra.\n" +
            "3. Valida presionando ENTER o el botón ENVIAR.\n" +
            "4. Cada 5 niveles superados el tiempo disminuye 2 segundos.\n" +
            "5. Tiempo mínimo por nivel: 2 segundos.\n" +
            "6. Pierdes si el tiempo llega a 0 o si escribes mal la palabra.\n\n" +
            "¡Demuestra tu agilidad!"
        );
        try {
            Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
            stage.getIcons().add(new javafx.scene.image.Image(
                getClass().getResourceAsStream(
                    "/com/example/proyectoescriturarapida/images/Reglas.png")));
        } catch (Exception e) {
            System.out.println("Could not load rules dialog icon.");
        }
        alert.showAndWait();
    }

    /**
     * Validates the player's input against the current target word.
     *
     * <p>Validation logic:</p>
     * <ul>
     *   <li>If the input is empty and the event was from the keyboard (not timeout), skip.</li>
     *   <li>If correct and time remains: increment level, adjust difficulty, show success.</li>
     *   <li>If incorrect or time expired: show the appropriate error message and go to Game Over.</li>
     * </ul>
     *
     * <p>Difficulty rule (HU-3): every 5 correct levels, {@code tiempoBase} decreases by 2,
     * down to a minimum of 2 seconds.</p>
     *
     * @param event The {@link ActionEvent} from the Enter key or submit button; {@code null} if from timer.
     */
    @FXML
    void handleSetWord(ActionEvent event) {
        String objetivo = targetWordLabel.getText();
        String escrito = typingField.getText();

        if (escrito.isEmpty() && event != null && !tiempoAgotado) return;

        boolean palabraCorrecta = escrito.equals(objetivo);
        boolean tiempoValido = miTemporizador != null && miTemporizador.getTiempoRestante() > 0;

        if (palabraCorrecta && tiempoValido && !tiempoAgotado) {
            nivelActual++;

            if (nivelActual % 5 == 0 && tiempoBase > 2) {
                tiempoBase = Math.max(2, tiempoBase - 2);
            }

            feedbackLabel.setStyle("-fx-text-fill: #2ecc71; -fx-font-family: Impact; -fx-font-size: 22;");
            feedbackLabel.setText("¡Correcto! Nivel " + (nivelActual - 1));
            Animaciones.pulso(feedbackLabel);

            mostrarNuevaPalabra();
            miTemporizador.iniciar(tiempoBase);
        } else {
            if (miTemporizador != null) miTemporizador.detener();

            String mensajeFallo = tiempoAgotado ? "Tiempo agotado" : "Respuesta incorrecta";
            feedbackLabel.setStyle("-fx-text-fill: #e74c3c; -fx-font-family: Impact; -fx-font-size: 22;");
            feedbackLabel.setText(mensajeFallo);
            Animaciones.sacudon(targetWordLabel);

            int tiempoRestante = miTemporizador != null ? miTemporizador.getTiempoRestante() : 0;
            navegarAGameOver(mensajeFallo, tiempoRestante);
        }
    }

    /**
     * Transitions the application to the Game Over scene, passing the final statistics.
     *
     * @param causaFallo    A human-readable description of why the game ended.
     * @param tiempoRestante The remaining time at the moment of failure (if applicable).
     */
    private void navegarAGameOver(String causaFallo, int tiempoRestante) {
        typingField.setDisable(true);
        try {
            URL location = getClass().getResource(
                "/com/example/proyectoescriturarapida/Perdistes.fxml");
            FXMLLoader loader = new FXMLLoader(location);
            Parent root = loader.load();

            PerdistesController gameOverCtrl = loader.getController();
            if (gameOverCtrl != null) {
                gameOverCtrl.mostrarEstadisticas(nivelActual - 1, causaFallo, tiempoRestante);
            }

            Stage stage = (Stage) typingField.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (Exception e) {
            System.err.println("Critical Error: Could not load Game Over scene.");
            e.printStackTrace();
        }
    }
}
