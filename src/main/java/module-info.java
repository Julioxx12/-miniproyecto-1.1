module com.example.proyectoescriturarapida {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;


    opens com.example.proyectoescriturarapida to javafx.fxml;
    exports com.example.proyectoescriturarapida;
    exports com.example.proyectoescriturarapida.controller;
    opens com.example.proyectoescriturarapida.controller to javafx.fxml;
    exports com.example.proyectoescriturarapida.model;
    opens com.example.proyectoescriturarapida.model to javafx.fxml;
    exports com.example.proyectoescriturarapida.view;
    opens com.example.proyectoescriturarapida.view to javafx.fxml;
}