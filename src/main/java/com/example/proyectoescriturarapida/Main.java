package com.example.proyectoescriturarapida;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

/**
 * Entry point of the Fast Writing application.
 * Extends {@link Application} to launch the JavaFX runtime, loads the main
 * FXML scene, and configures the primary stage with title and icon.
 *
 * @author Julio Cesar
 * @version 2.0
 */
public class Main extends Application {

    /**
     * Initializes and shows the primary application window.
     * Loads {@code EscrituraRapida.fxml} as the initial scene.
     *
     * @param stage The primary {@link Stage} provided by the JavaFX runtime.
     * @throws Exception If the FXML resource cannot be found or loaded.
     */
    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader(
            Main.class.getResource("EscrituraRapida.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 667, 580);

        stage.getIcons().add(new Image(
            getClass().getResourceAsStream(
                "/com/example/proyectoescriturarapida/images/Lapiz.png")));
        stage.setTitle("Escritura Rápida");
        stage.setResizable(false);
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Main method — launches the JavaFX application.
     *
     * @param args Command-line arguments (not used).
     */
    public static void main(String[] args) {
        launch();
    }
}
