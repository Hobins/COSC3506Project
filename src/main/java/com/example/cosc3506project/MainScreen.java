package com.example.cosc3506project;

import com.example.cosc3506project.screens.LoginScreen;
import javafx.application.Application;
import javafx.scene.Node;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;

public class MainScreen extends Application {

    private BorderPane mainLayout;

    @Override
    public void start(Stage stage) {
        // Start the WebServer in a separate thread
        Thread webServerThread = new Thread(() -> {
            try {
                WebServer.main(null);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        webServerThread.setDaemon(true); // Stops the thread when the JavaFX app exits
        webServerThread.start();

        // Create the main layout
        mainLayout = new BorderPane();
        stage.setTitle("Construction Project Management System");

        // Set the initial screen content (e.g., Login screen)
        mainLayout.setCenter(LoginScreen.getScreen(this));

        // Create and display the scene
        Scene scene = new Scene(mainLayout, 1000, 800);
        stage.setScene(scene);
        stage.show();
    }

    public Node setScreen(Node newScreen) {
        mainLayout.setCenter(newScreen);
        return newScreen;
    }

    public static void main(String[] args) {
        launch();
    }
}