package com.example.cosc3506project.screens;

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
        importData();   //imports user data to user table
    }

    // Setup the left panel once
    private void setupLeftPanel() {
        // Left panel (Admin menu)
        leftPanel.setPadding(new Insets(10));
        leftPanel.setPrefWidth(200);
        leftPanel.setStyle("-fx-border-color: #ccc; -fx-border-width: 1px;");

        Label adminLabel = new Label("Administrator");
        ListView<String> adminMenu = new ListView<>();
        adminMenu.getItems().addAll("Dashboard", "Manage Users", "User Roles", "Assign Permissions", "Financials");

        adminMenu.setOnMouseClicked(e -> {
            String selectedItem = adminMenu.getSelectionModel().getSelectedItem();
            if (selectedItem != null) {
                switch (selectedItem) {
                    case "Manage Users":
                        showManageUsersScreen();
                        break;
                    case "User Roles":
                        showUserRolesScreen();
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


    public BorderPane getAdminScreen() {
        root.setLeft(leftPanel);

        showManageUsersScreen();

        return root;
    }

    public void showUserRolesScreen() {
        VBox userRoles = new VBox(10);
        userRoles.setPadding(new Insets(20));
        Label title = new Label("User Roles");
        title.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");
        userRoles.getChildren().add(title);

        root.setCenter(userRoles);
    }

    public void showAssignPermissionsScreen() {
        VBox assignPerms = new VBox(10);
        assignPerms.setPadding(new Insets(20));
        Label permsTitle = new Label("Assign Permissions");
        permsTitle.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");

        TableView<User> table = new TableView<>();
        table.setEditable(true);

        // Define columns
        TableColumn<User, String> userIdCol = new TableColumn<>("User ID");
        userIdCol.setMinWidth(100);
        userIdCol.setCellValueFactory(new PropertyValueFactory<>("userId"));

        TableColumn<User, String> accountCol = new TableColumn<>("Account");
        accountCol.setMinWidth(100);
        accountCol.setCellValueFactory(new PropertyValueFactory<>("account"));

        TableColumn<User, Void> typeCol = new TableColumn<>("Account Type");
        typeCol.setMinWidth(100);
        typeCol.setCellValueFactory(new PropertyValueFactory<>("type"));

        TableColumn<User, Void> actionCol = new TableColumn<>("Action");
        actionCol.setMinWidth(80);

        actionCol.setCellFactory(param -> new TableCell<>() {
            private final Button updateButton = new Button("Update");
            {
                updateButton.setOnAction(event -> {
                    User user = getTableView().getItems().get(getIndex());
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

        table.getColumns().addAll(userIdCol, accountCol, typeCol, actionCol);

        // Sample data
        ObservableList<User> data = FXCollections.observableArrayList(
                new User("2000589", "John", "432-876-7869", "Active", "john@gmail.ca",
                        FXCollections.observableArrayList("Client", "Contractor", "Admin")),
                new User("2000590", "Sam", "324-876-7983", "Inactive", "sam@outlook.ca",
                        FXCollections.observableArrayList("Client", "Contractor", "Admin")),
                new User("2000591", "Bob", "432-654-3164", "Active", "bob@outlook.ca",
                        FXCollections.observableArrayList("Client", "Contractor", "Admin"))
        );
        table.setItems(data);

        assignPerms.getChildren().addAll(permsTitle, table);

        root.setCenter(assignPerms);
    }

    public void showFinancialsScreen() {
        VBox financials = new VBox(10);
        financials.setPadding(new Insets(20));
        Label title = new Label("Financials");
        title.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");
        financials.getChildren().add(title);

        root.setCenter(financials);
    }

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


    // User class
    public static class User {
        private String userId;
        private String account;
        private String contact;
        private String status;
        private String email;
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

        public void setAccount(String account) {
            this.account = account;
        }

        public void setContact(String contact) {
            this.contact = contact;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public ComboBox getType() {
            return type;
        }
    }
}



