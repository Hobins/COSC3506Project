package com.example.cosc3506project.screens;
// admin page code
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.*;
import javafx.util.Callback;

public class ProjectManagementSystem {

    public BorderPane getAdminScreen() {

        // Main layout
        BorderPane root = new BorderPane();

        // Left panel (Admin menu)
        VBox leftPanel = new VBox(10);
        leftPanel.setPadding(new Insets(10));
        leftPanel.setPrefWidth(200);
        leftPanel.setStyle("-fx-border-color: #ccc; -fx-border-width: 1px;");

        Label adminLabel = new Label("Administrator");
        ListView<String> adminMenu = new ListView<>();
        adminMenu.getItems().addAll("Dashboard", "Manage Users", "Edit/Delete Users", "User Roles", "Assign Permissions", "Financials");

        leftPanel.getChildren().addAll(adminLabel, adminMenu);

        // Center panel (Edit/Delete Users)
        VBox centerPanel = new VBox(10);
        centerPanel.setPadding(new Insets(20));
        centerPanel.setAlignment(Pos.TOP_LEFT);

        Label centerTitle = new Label("Edit/Delete Users");
        centerTitle.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");

        TableView<User> table = new TableView<>();
        table.setEditable(true);

        // Define columns
        TableColumn<User, String> userIdCol = new TableColumn<>("User ID");
        userIdCol.setMinWidth(100);
        userIdCol.setCellValueFactory(new PropertyValueFactory<>("userId"));

        TableColumn<User, String> accountCol = new TableColumn<>("Account");
        accountCol.setMinWidth(100);
        accountCol.setCellValueFactory(new PropertyValueFactory<>("account"));

        TableColumn<User, String> contactCol = new TableColumn<>("Contact");
        contactCol.setMinWidth(150);
        contactCol.setCellValueFactory(new PropertyValueFactory<>("contact"));

        TableColumn<User, String> statusCol = new TableColumn<>("Status");
        statusCol.setMinWidth(100);
        statusCol.setCellValueFactory(new PropertyValueFactory<>("status"));

        TableColumn<User, String> emailCol = new TableColumn<>("Email");
        emailCol.setMinWidth(200);
        emailCol.setCellValueFactory(new PropertyValueFactory<>("email"));

        // Add delete button column
        TableColumn<User, Void> actionCol = new TableColumn<>("Action");
        actionCol.setMinWidth(80);
        actionCol.setCellFactory(param -> new TableCell<>() {
            private final Button deleteButton = new Button("Delete");

            {
                deleteButton.setOnAction(event -> {
                    User user = getTableView().getItems().get(getIndex());
                    table.getItems().remove(user);
                });
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    setGraphic(deleteButton);
                }
            }
        });

        table.getColumns().addAll(userIdCol, accountCol, contactCol, statusCol, emailCol, actionCol);

        // Sample data
        ObservableList<User> data = FXCollections.observableArrayList(
                new User("2000589", "John", "432-876-7869", "Active", "john@gmail.ca"),
                new User("2000589", "Sam", "324-876-7983", "Inactive", "sam@outlook.ca")
        );
        table.setItems(data);

        centerPanel.getChildren().addAll(centerTitle, table);

        // Add panels to root layout
        root.setLeft(leftPanel);
        root.setCenter(centerPanel);

        return root;

    }

    // User class
    public static class User {
        private String userId;
        private String account;
        private String contact;
        private String status;
        private String email;

        public User(String userId, String account, String contact, String status, String email) {
            this.userId = userId;
            this.account = account;
            this.contact = contact;
            this.status = status;
            this.email = email;
        }

        public String getUserId() {
            return userId;
        }

        public String getAccount() {
            return account;
        }

        public String getContact() {
            return contact;
        }

        public String getStatus() {
            return status;
        }

        public String getEmail() {
            return email;
        }
    }
}
