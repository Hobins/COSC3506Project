package com.example.cosc3506project;

import com.example.cosc3506project.screens.LoginScreen;
import javafx.application.Application;
import javafx.scene.Node;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;

public class MainScreen extends Application {

    private BorderPane mainLayout;

    /**
     * Start the JavaFX application and display the initial screen.
     * Starts the server in a separate thread.
     * @param stage The primary stage for this application.
     */
    @Override
    public void start(Stage stage) {

        Thread webServerThread = new Thread(() -> {
            try {
                WebServer.main(null);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        webServerThread.setDaemon(true);
        webServerThread.start();

        mainLayout = new BorderPane();
        stage.setTitle("Construction Project Management System");

        mainLayout.setCenter(LoginScreen.getScreen(this));

        Scene scene = new Scene(mainLayout, 1000, 800);
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Set the screen content to the new screen.
     * @param newScreen The new screen to display.
     * @return The new screen.
     */
    public Node setScreen(Node newScreen) {
        mainLayout.setCenter(newScreen);
        return newScreen;
    }

    /**
     * Launch the JavaFX application.
     * @param args Command line arguments.
     */
    public static void main(String[] args) {
        launch();
    }
}