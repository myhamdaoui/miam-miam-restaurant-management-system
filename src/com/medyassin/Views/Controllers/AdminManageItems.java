package com.medyassin.Views.Controllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.validation.RequiredFieldValidator;
import com.medyassin.DatabaseControllers.ItemController;
import com.medyassin.DatabaseControllers.ManageCustomerController;
import com.medyassin.DatabaseControllers.UserController;
import com.medyassin.Models.Customer;
import com.medyassin.Models.Item;
import com.medyassin.TableViewModels.CustomerTVModel;
import com.medyassin.TableViewModels.ItemTVModel;
import com.medyassin.TableViewModels.UserTVModel;
import com.medyassin.Utilities.CustomAlert.CustomerAlert;
import com.medyassin.Utilities.Utilities;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class AdminManageItems implements Initializable, EventHandler<MouseEvent> {
    @FXML
    private Pane manageCustomers;

    @FXML
    private Pane pendingOrders;

    @FXML
    private Pane viewAllOrders;

    @FXML
    private Pane makeOrder;

    @FXML
    private Pane manageUsers;

    @FXML
    private TableView customersTable;

    @FXML
    private JFXButton logoutBtn;

    @FXML
    private JFXTextField userImageTF;

    @FXML
    private JFXPasswordField userPassTF;

    @FXML
    private Circle manageCustomersMask;

    @FXML
    private Circle makeOrderMask;

    @FXML
    private Circle viewAllOrdersMask;

    @FXML
    private Circle pendingOrdersMask;

    @FXML
    private Circle manageUsersMask;

    @FXML
    private JFXButton confirmBtn;

    @FXML
    private Circle UserImageMaskCircle;

    @FXML
    private BorderPane borderPane;

    @FXML
    private JFXTextField userNameTF;

    @FXML
    private JFXTextField userRoleTF;

    @FXML
    private TableColumn itemCodeTC;

    @FXML
    private TableColumn itemNameTC;

    @FXML
    private TableColumn itemPriceTC;

    @FXML
    private TableColumn itemTypeTC;

    @FXML
    private JFXTextField userIDTF;

    @FXML
    private JFXComboBox<String> actionCB;

    @FXML
    private Rectangle itemImageMask;

    @FXML
    private Label nameLabel;

    @FXML JFXComboBox<String> itemTypeFilter;

    // Data for the Customer table
    private ObservableList<ItemTVModel> data = FXCollections.observableArrayList();


    /*
    - Show sections based on user role
     */
    public void setVisibility() {
        try {
            if(UserController.getUserRole().equals("admin")) {
                makeOrder.setMaxHeight(0);
                makeOrder.setVisible(false);
            } else {
                manageUsers.setMaxHeight(0);
                manageUsers.setVisible(false);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        /*
        - Add icons to the sidebar
        - Add user image to the top bar
        - Add username to the top bar
        */
        setStaticItems();

        setVisibility();

        setItemTypeFilter();

        // Set Customer Table
        setUsersTable();


        confirmBtn.setOnAction(e -> {
            if(actionCB.getValue() == null) {
                try {
                    CustomerAlert.display("error", "Veuillez choisir un opération");
                } catch (IOException e1) {
                    e1.printStackTrace();
                }

            } else if(actionCB.getValue().equals("Ajouter")) {
                try {
                    addNewUser();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            } else if(actionCB.getValue().equals("Supprimer")) {
                deleteCustomer();

            } else if(actionCB.getValue().equals("Mise à jour")) {
                //TODO
                updateCustomer();
            }

        });

        // Set Combo box
        actionCB.getItems().addAll("Mise à jour", "Supprimer", "Ajouter");
        actionCB.valueProperty().addListener((observable, oldValue, newValue) -> {
            if(newValue.equals("Ajouter")) {
                // Set id
                setIDTF();
                // Clear inputs
                clearCustomerForm();
            }
        });

        // Set next ID for TextField
        setIDTF();

        // Set row selection listener
        customersTable.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if(newSelection != null) {
                ItemTVModel i = (ItemTVModel) newSelection;

                String imgName = i.getItemImg();
                System.out.println(imgName);
                itemImageMask.setFill(new ImagePattern(new Image("file:img/items/" + imgName, false)));

            }
        });
    }

    private void setItemTypeFilter() {
        try {
            ArrayList<String> cats = ItemController.getCategories();
            for(String cat: cats) {
                itemTypeFilter.getItems().add(cat);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

    }

    private void setStaticItems()  {
        // Set Image for the circular mask

        try {
            String url = "/com/medyassin/Img/" + UserController.getUserImg();
            System.out.println(url);
            UserImageMaskCircle.setFill(new ImagePattern(new Image(url, false)));

            // set name
            nameLabel.setText("Bienvenue " + UserController.getUserName());
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }


        // Set no focus for logout btn
        logoutBtn.setDisableVisualFocus(true);

        // Set Image Icon for mask of 'Manage customers' ...
        manageCustomersMask.setFill(new ImagePattern(new Image("/com/medyassin/Img/icons/user.png", false)));
        makeOrderMask.setFill(new ImagePattern(new Image(getClass().getResource("/com/medyassin/Img/icons/order.png").toExternalForm(), false)));
        viewAllOrdersMask.setFill(new ImagePattern(new Image("/com/medyassin/Img/icons/view.png", false)));
        pendingOrdersMask.setFill(new ImagePattern(new Image("/com/medyassin/Img/icons/pending.png", false)));
        manageUsersMask.setFill(new ImagePattern(new Image("/com/medyassin/Img/icons/user.png", false)));
    }

    private void deleteCustomer() {
        int id = Integer.parseInt(userIDTF.getText());

        try {
            if(UserController.deleteUser(id)) {
                CustomerAlert.display("success", "L'utilisateur a été bien supprimé");
            } else {
                try {
                    CustomerAlert.display("error", "Can't delete this user or user doesn't exists");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Refresh
        refreshTable();

        // Clear inputs
        clearCustomerForm();
    }

    private void updateCustomer() {
        int id = Integer.parseInt(userIDTF.getText());
        String userName = userNameTF.getText();
        String userRole = userRoleTF.getText();
        String userImage = userImageTF.getText();
        String password = userPassTF.getText();

        try {
            boolean success = UserController.updateUser(id, userName, password, userRole, userImage);
            if(!success) {
                try {
                    CustomerAlert.display("error", "Update user has failed !");
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            } else {
                try {
                    CustomerAlert.display("success", "Update user has been done successfully !");
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        // Refresh
        refreshTable();
    }

    private void clearCustomerForm() {
        userNameTF.setText("");
        userRoleTF.setText("");
        userImageTF.setText("");
        userPassTF.setText("");
    }

    private void refreshTable() {
        data = FXCollections.observableArrayList();
        try {
            data = ItemController.getAllItems();
            customersTable.setItems(data);
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void setUsersTable() {
        itemCodeTC.setCellValueFactory(new PropertyValueFactory<CustomerTVModel, String>("itemCode"));
        itemNameTC.setCellValueFactory(new PropertyValueFactory<CustomerTVModel, String>("itemName"));
        itemPriceTC.setCellValueFactory(new PropertyValueFactory<CustomerTVModel, String>("itemPrice"));
        itemTypeTC.setCellValueFactory(new PropertyValueFactory<CustomerTVModel, Rectangle>("itemType"));

        refreshTable();
    }

    private void setIDTF() {

        try {
            userIDTF.setText(UserController.getNextID() + "");
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

    }
    private void addNewUser() throws IOException {
        String userName = userNameTF.getText();
        String userRole = userRoleTF.getText();
        String userImage = userImageTF.getText();
        String password = userPassTF.getText();

        //Validate inputs
        boolean validated = validateInputs();


        if(validated) {
            try {
                if (!UserController.addNewUser(userName, password, userRole, userImage)) {
                    CustomerAlert.display("error", "Can't add new user");
                } else {
                    CustomerAlert.display("success", "L'utilisateur a été bien ajouté");
                }
            } catch (SQLException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }

            // Refresh
            refreshTable();

            // Clear inputs
            clearCustomerForm();

            // Set next id
            setIDTF();
        }

    }

    private boolean validateInputs() {
        RequiredFieldValidator validator = new RequiredFieldValidator();
        validator.setMessage("Ce champ est obligatoire");
        userNameTF.getValidators().add(validator);
        userRoleTF.getValidators().add(validator);
        userImageTF.getValidators().add(validator);
        userPassTF.getValidators().add(validator);

        if(!userNameTF.validate()) {
            return false;
        } else if(!userRoleTF.validate()) {
            return false;
        } else if(!userImageTF.validate()) {
            return false;
        } else if(!userPassTF.validate()) {
            return false;
        }

        return true;
    }

    @FXML
    private void logoutBtnAction(ActionEvent actionEvent) throws IOException {
        // Go to login page
        Scene currentScene = borderPane.getScene();
        AnchorPane anchorPane = FXMLLoader.load(getClass().getResource("/com/medyassin/Views/Fxmls/LoginScreen.fxml"));
        Utilities.switchScreen(currentScene, anchorPane, getClass().getResource("/com/medyassin/Views/Fxmls/LoginScreen.css").toExternalForm(), false, 600);

    }

    @Override
    public void handle(MouseEvent event) {
        Pane pane = (Pane) event.getSource();
        if(event.getEventType().toString().equals("MOUSE_ENTERED")) {
            pane.setStyle("-fx-background-color:  #4E4E4E;");
        } else if(event.getEventType().toString().equals("MOUSE_EXITED")) {
            pane.setStyle("-fx-background-color:  #343434;");
        }
    }

    @FXML
    public void manageUsersClick(MouseEvent e) {
        Scene currentScene = manageCustomers.getScene();
        try {
            BorderPane target = FXMLLoader.load(getClass().getResource("/com/medyassin/Views/Fxmls/UserManageCustomers.fxml"));
            Utilities.switchScreen(currentScene, target,getClass().getResource("/com/medyassin/Views/Fxmls/UserManageCustomers.css").toExternalForm(), true, 600);
        } catch (IOException e1) {
            e1.printStackTrace();
        }
    }

    @FXML
    public void makeOrderClick(MouseEvent e) {
        Scene currentScene = manageCustomers.getScene();
        try {
            BorderPane target = FXMLLoader.load(getClass().getResource("/com/medyassin/Views/Fxmls/UserMakeOrder.fxml"));
            Utilities.switchScreen(currentScene, target,getClass().getResource("/com/medyassin/Views/Fxmls/UserMakeOrder.css").toExternalForm(), true, 710);
        } catch (IOException e1) {
            e1.printStackTrace();
        }
    }

    @FXML
    public void allOrderClick(MouseEvent e) {
        Scene currentScene = manageCustomers.getScene();
        try {
            BorderPane target = FXMLLoader.load(getClass().getResource("/com/medyassin/Views/Fxmls/ViewAllOrders.fxml"));
            Utilities.switchScreen(currentScene, target,getClass().getResource("/com/medyassin/Views/Fxmls/ViewAllOrders.css").toExternalForm(), true,  600);
        } catch (IOException e1) {
            e1.printStackTrace();
        }
    }

    @FXML
    public void pendingClick(MouseEvent e) {
        Scene currentScene = manageCustomers.getScene();
        try {
            BorderPane target = FXMLLoader.load(getClass().getResource("/com/medyassin/Views/Fxmls/PendingOrders.fxml"));
            Utilities.switchScreen(currentScene, target,getClass().getResource("/com/medyassin/Views/Fxmls/PendingOrders.css").toExternalForm(), true, 600);
        } catch (IOException e1) {
            e1.printStackTrace();
        }
    }
}