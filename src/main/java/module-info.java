module com.example.cosc3506project {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires eu.hansolo.tilesfx;
    requires jetty.servlet.api;

    opens com.example.cosc3506project to javafx.fxml;
    exports com.example.cosc3506project;
}