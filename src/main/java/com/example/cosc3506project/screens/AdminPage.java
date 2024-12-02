package com.example.cosc3506project.screens;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.*;
import java.io.*;

public class AdminPage {

    BorderPane root = new BorderPane();  // Root layout for the screen
    VBox leftPanel = new VBox(10);  // The left panel menu

    protected static final String FILE_PATH = "user_data.csv";

    ObservableList<AdminPage.User> data = FXCollections.observableArrayList();

    /**
     * This imports the data from a csv file
     */
    protected void importData() {
        File file = new File(FILE_PATH);

        if (file.exists()) {
            try (BufferedReader br = new BufferedReader(new FileReader(file))) {
                String line;
                while ((line = br.readLine()) != null) {
                    String[] details = line.split(" ");
                    if (details.length == 5) {
                        String id = details[0].trim();
                        String name = details[1].trim();
                        String contact = details[2].trim();
                        String status = details[3].trim();
                        String email = details[4].trim();

                        data.add(new AdminPage.User(id, name, contact, status, email));

                    }
                }
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }

    /**
     * This saves the data to the csv file.
     *
     */
    private void saveData() {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(FILE_PATH))) {
            for (User user : data) {
                bw.write(user.userId + " " + user.account + " " +
                        user.contact + " " +  user.status + " " +  user.email);
                bw.newLine();
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    };


    /**
     * Constructor for the ProjectManagementSystem
     */
    public AdminPage() {
        setupLeftPanel();
        importData();
    }

    /**
     * This sets-up the left panel for the whole Admin menu
     *
     */
    private void setupLeftPanel() {

        leftPanel.setPadding(new Insets(10));
        leftPanel.setPrefWidth(200);
        leftPanel.setStyle("-fx-border-color: #ccc; -fx-border-width: 1px;");

        Label adminLabel = new Label("Administrator");
        ListView<String> adminMenu = new ListView<>();
        adminMenu.getItems().addAll("Manage Users", "Assign Permissions", "Financials");

        adminMenu.setOnMouseClicked(e -> {
            String selectedItem = adminMenu.getSelectionModel().getSelectedItem();
            if (selectedItem != null) {
                switch (selectedItem) {
                    case "Manage Users":
                        showManageUsersScreen();
                        break;
                    case "Assign Permissions":
                        showAssignPermissionsScreen();
                        break;
                    case "Financials":
                        showFinancialsScreen();
                        break;
                    default:
                        break;
                }
            }
        });

        leftPanel.getChildren().addAll(adminLabel, adminMenu);
    }

    /**
     * This is called when the user is logged in and confirmed to be a Admin
     *
     */
    public BorderPane getAdminScreen() {
        root.setLeft(leftPanel);

        showManageUsersScreen();

        return root;
    }

    /**
     * This displays a  permissions screen for the admin to change the permissions of the users
     *
     */
    public void showAssignPermissionsScreen() {
        VBox assignPerms = new VBox(10);
        assignPerms.setPadding(new Insets(20));
        Label permsTitle = new Label("Assign Permissions");
        permsTitle.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");

        TableView<User> table = new TableView<>();
        table.setEditable(true);

        TableColumn<User, String> userIdCol = new TableColumn<>("User ID");
        userIdCol.setMinWidth(100);
        userIdCol.setCellValueFactory(new PropertyValueFactory<>("userId"));

        TableColumn<User, String> accountCol = new TableColumn<>("Account");
        accountCol.setMinWidth(100);
        accountCol.setCellValueFactory(new PropertyValueFactory<>("account"));

        TableColumn<User, String> accTypeCol = new TableColumn<>("Account");
        accTypeCol.setMinWidth(100);
        accTypeCol.setCellValueFactory(new PropertyValueFactory<>("userType"));

        TableColumn<User, Void> typeCol = new TableColumn<>("Change Account Type");
        typeCol.setMinWidth(100);
        typeCol.setCellValueFactory(new PropertyValueFactory<>("type"));

        TableColumn<User, Void> actionCol = new TableColumn<>("Action");
        actionCol.setMinWidth(80);

        actionCol.setCellFactory(param -> new TableCell<>() {
            private final Button updateButton = new Button("Update");
            {
                updateButton.setOnAction(event -> {
                    User user = getTableView().getItems().get(getIndex());
                    user.setUserType((String) user.getType().getValue());
                    accTypeCol.setCellValueFactory(new PropertyValueFactory<>("userType"));
                    table.refresh();
                });
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    setGraphic(updateButton);
                }
            }
        });

        table.getColumns().addAll(userIdCol, accountCol, accTypeCol, typeCol, actionCol);

        ObservableList<User> data = FXCollections.observableArrayList(
                new User("2000589", "John", "432-876-7869", "Active", "john@gmail.ca", "Contractor",
                        FXCollections.observableArrayList("Client", "Contractor", "Admin")),
                new User("2000590", "Sam", "324-876-7983", "Inactive", "sam@outlook.ca", "Client",
                        FXCollections.observableArrayList("Client", "Contractor", "Admin")),
                new User("2000591", "Bob", "432-654-3164", "Active", "bob@outlook.ca", "Admin",
                        FXCollections.observableArrayList("Client", "Contractor", "Admin"))
        );
        table.setItems(data);

        assignPerms.getChildren().addAll(permsTitle, table);

        root.setCenter(assignPerms);
    }

    /**
     * This displays a Financial screen for the admin to manage the financials of a certain record.
     *
     */
    public void showFinancialsScreen() {
        VBox financials = new VBox(10);
        financials.setPadding(new Insets(20));
        Label title = new Label("Financials");
        title.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");
        financials.getChildren().add(title);

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

        financials.getChildren().addAll(financialTable, dataFields);

        root.setCenter(financials);
    }

    /**
     * This displays a screen for the admin to manage the users of the system.
     * The admin can edit or delete users from the system.
     *
     */
    private void showManageUsersScreen() {
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
                    saveData();     //preserve changes
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


        Button addUser = new Button("Add User");
        TextArea newUser = new TextArea();
        newUser.setPromptText("Enter user details in above column order separated by space");
        // newUser.setEditable(true);

        addUser.setOnAction(event -> {
            String input = newUser.getText();
            String[] details = input.split(" ");
            if (details.length == 5) {
                String id = details[0];
                String name = details[1];
                String contact = details[2];
                String status = details[3];
                String email = details[4];


                data.add(new User(id, name, contact, status, email));
                saveData();     //preserve changes
                newUser.clear();

            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText(null);
                alert.setContentText("Invalid user details");
            }

        });
        table.setItems(data);

        centerPanel.getChildren().addAll(centerTitle, table, addUser, newUser);

        root.setCenter(centerPanel);
    }


    public static class User {
        private String userId;
        private String account;
        private String contact;
        private String status;
        private String email;
        private String userType;
        private ComboBox type;

        public User(String userId, String account, String contact, String status, String email, ObservableList data) {
            this.userId = userId;
            this.account = account;
            this.contact = contact;
            this.status = status;
            this.email = email;
            this.type = new ComboBox(data);
            type.setPromptText("Account Type");
            type.setEditable(true);
        }

        public User(String userId, String account, String contact, String status, String email, String userType, ObservableList data) {
            this.userId = userId;
            this.account = account;
            this.contact = contact;
            this.status = status;
            this.email = email;
            this.userType = userType;
            this.type = new ComboBox(data);
            type.setPromptText("Account Type");
            type.setEditable(true);
        }

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

        public String getUserType(){
            return userType;
        }

        public void setAccount(String account) {
            this.account = account;
        }

        public void setContact(String contact) {
            this.contact = contact;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public void setUserType(String userType) {
            this.userType = userType;
        }

        public ComboBox getType() {
            return type;
        }
    }

    public static class FinancialData {
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

}



