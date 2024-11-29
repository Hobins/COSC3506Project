package COSC3506;

import javafx.application.Application;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

public class AdminFinancialsPage extends Application {

    @Override
    public void start(Stage primaryStage) {
        // Main layout
        BorderPane root = new BorderPane();
        root.setPadding(new Insets(10));
        root.setStyle("-fx-background-color: white;");

        // Left Navigation Panel
        VBox navigationPanel = new VBox(10);
        navigationPanel.setPadding(new Insets(10));
        navigationPanel.setStyle("-fx-border-color: lightgray; -fx-border-width: 1;");
        navigationPanel.setPrefWidth(200);

        Label adminLabel = new Label("Admin");
        adminLabel.setStyle("-fx-font-weight: bold; -fx-font-size: 16;");
        ListView<String> menuList = new ListView<>();
        menuList.getItems().addAll("Dashboard", "View Financials", "Manage Payments");
        menuList.setPrefHeight(200);

        navigationPanel.getChildren().addAll(adminLabel, menuList);

        root.setLeft(navigationPanel);

        // Center Panel (Dynamic Content Area)
        VBox centerPanel = new VBox(10);
        centerPanel.setPadding(new Insets(10));
        root.setCenter(centerPanel);

        Label title = new Label("Dashboard");
        title.setStyle("-fx-font-size: 18; -fx-font-weight: bold;");

        Label placeholder = new Label("Welcome to the Dashboard!");
        placeholder.setStyle("-fx-font-size: 14; -fx-text-fill: gray;");

        centerPanel.getChildren().addAll(title, placeholder);

        // Table for Financials
        TableView<FinancialData> financialTable = new TableView<>();
        financialTable.setPrefHeight(400);
        financialTable.setStyle("-fx-border-color: lightgray; -fx-border-width: 1;");

        TableColumn<FinancialData, String> categoryColumn = new TableColumn<>("Category");
        categoryColumn.setCellValueFactory(data -> data.getValue().categoryProperty());

        TableColumn<FinancialData, Double> amountColumn = new TableColumn<>("Amount");
        amountColumn.setCellValueFactory(data -> data.getValue().amountProperty().asObject());

        financialTable.getColumns().addAll(categoryColumn, amountColumn);

        // Add initial mock data to the table
        ObservableList<FinancialData> financialData = FXCollections.observableArrayList(
                new FinancialData("Materials", 200.0),
                new FinancialData("Labour", 400.0),
                new FinancialData("Tax", 150.0)
        );
        financialTable.setItems(financialData);

        // Add Data Input Fields
        HBox dataFields = new HBox(10);
        dataFields.setPadding(new Insets(10));
        dataFields.setStyle("-fx-border-color: lightgray; -fx-border-width: 1;");

        TextField categoryField = new TextField();
        categoryField.setPromptText("Enter Category");

        TextField amountField = new TextField();
        amountField.setPromptText("Enter Amount");

        Button addButton = new Button("Add");
        Button updateButton = new Button("Update");
        Button deleteButton = new Button("Delete");

        // Add Button Functionality
        addButton.setOnAction(e -> {
            try {
                String category = categoryField.getText();
                double amount = Double.parseDouble(amountField.getText());

                // Add new data to the table
                financialData.add(new FinancialData(category, amount));

                // Clear input fields
                categoryField.clear();
                amountField.clear();
            } catch (NumberFormatException ex) {
                Alert alert = new Alert(Alert.AlertType.ERROR, "Please enter a valid amount.", ButtonType.OK);
                alert.show();
            }
        });

        // Update Button Functionality
        updateButton.setOnAction(e -> {
            FinancialData selectedItem = financialTable.getSelectionModel().getSelectedItem();
            if (selectedItem != null) {
                try {
                    String newCategory = categoryField.getText();
                    double newAmount = Double.parseDouble(amountField.getText());

                    selectedItem.setCategory(newCategory);
                    selectedItem.setAmount(newAmount);

                    // Refresh the table
                    financialTable.refresh();

                    // Clear input fields
                    categoryField.clear();
                    amountField.clear();
                } catch (NumberFormatException ex) {
                    Alert alert = new Alert(Alert.AlertType.ERROR, "Please enter a valid amount.", ButtonType.OK);
                    alert.show();
                }
            } else {
                Alert alert = new Alert(Alert.AlertType.WARNING, "Please select a record to update.", ButtonType.OK);
                alert.show();
            }
        });

        // Delete Button Functionality
        deleteButton.setOnAction(e -> {
            FinancialData selectedItem = financialTable.getSelectionModel().getSelectedItem();
            if (selectedItem != null) {
                financialData.remove(selectedItem);
            } else {
                Alert alert = new Alert(Alert.AlertType.WARNING, "Please select a record to delete.", ButtonType.OK);
                alert.show();
            }
        });

        // Table Selection Listener
        financialTable.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                categoryField.setText(newSelection.getCategory());
                amountField.setText(String.valueOf(newSelection.getAmount()));
            }
        });

        dataFields.getChildren().addAll(categoryField, amountField, addButton, updateButton, deleteButton);

        // Handle Navigation
        menuList.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            centerPanel.getChildren().clear();

            if (newSelection.equals("Dashboard")) {
                title.setText("Dashboard");
                centerPanel.getChildren().addAll(title, placeholder);
            } else if (newSelection.equals("View Financials")) {
                title.setText("Financial Overview");
                centerPanel.getChildren().addAll(title, financialTable, dataFields);
            } else if (newSelection.equals("Manage Payments")) {
                title.setText("Payments");
                Label paymentsPlaceholder = new Label("Manage Payments Here!");
                paymentsPlaceholder.setStyle("-fx-font-size: 14; -fx-text-fill: gray;");
                centerPanel.getChildren().addAll(title, paymentsPlaceholder);
            }
        });

        // Scene and Stage
        Scene scene = new Scene(root, 800, 600);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Admin Financial Dashboard");
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}

class FinancialData {
    private final SimpleStringProperty category;
    private final SimpleDoubleProperty amount;

    public FinancialData(String category, double amount) {
        this.category = new SimpleStringProperty(category);
        this.amount = new SimpleDoubleProperty(amount);
    }

    public String getCategory() {
        return category.get();
    }

    public void setCategory(String category) {
        this.category.set(category);
    }

    public SimpleStringProperty categoryProperty() {
        return category;
    }

    public double getAmount() {
        return amount.get();
    }

    public void setAmount(double amount) {
        this.amount.set(amount);
    }

    public SimpleDoubleProperty amountProperty() {
        return amount;
    }
}
