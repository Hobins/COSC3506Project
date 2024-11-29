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
    requires org.eclipse.jetty.server;
    requires org.eclipse.jetty.servlet;
    requires org.json;

    opens com.example.cosc3506project to javafx.fxml, javafx.graphics;
    opens com.example.cosc3506project.servlets to java.base;
    opens com.example.cosc3506project.screens to javafx.fxml, javafx.graphics;

    exports com.example.cosc3506project;
    exports com.example.cosc3506project.screens;
    exports com.example.cosc3506project.servlets;

}