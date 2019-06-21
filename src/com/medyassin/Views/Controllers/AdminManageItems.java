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
    private Circle manageItemsMask;

    @FXML
    private TableView customersTable;

    @FXML
    private JFXButton logoutBtn;

    @FXML
    private JFXTextField itemCodeTF;


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
    private JFXTextField itemNameTF;

    @FXML
    private JFXTextField itemPriceTF;

    @FXML
    private JFXTextField itemImageTF;

    @FXML
    private TableColumn itemCodeTC;

    @FXML
    private TableColumn itemNameTC;

    @FXML
    private TableColumn itemPriceTC;

    @FXML
    private TableColumn itemTypeTC;

    @FXML
    private JFXTextField itemTypeTF;

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
        System.out.println("Here i am in items");
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
                    addNewItem();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            } else if(actionCB.getValue().equals("Supprimer")) {
                deleteItem();

            } else if(actionCB.getValue().equals("Mise à jour")) {
                //TODO
                updateItem();
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

                itemCodeTF.setText(i.getItemCode());
                itemNameTF.setText(i.getItemName());
                itemPriceTF.setText(i.getItemPrice());
                itemImageTF.setText(i.getItemImg());
                itemTypeTF.setText(i.getItemType());

            }
        });
    }

    private void setItemTypeFilter() {
        try {
            itemTypeFilter.getItems().add("all");
            ArrayList<String> cats = ItemController.getCategories();
            for(String cat: cats) {
                itemTypeFilter.getItems().add(cat);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        itemTypeFilter.valueProperty().addListener((observable, oldValue, newValue) -> {
            refreshTable(newValue);
        });

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
        makeOrderMask.setFill(new ImagePattern(new Image("/com/medyassin/Img/icons/order.png", false)));
        viewAllOrdersMask.setFill(new ImagePattern(new Image("/com/medyassin/Img/icons/view.png", false)));
        pendingOrdersMask.setFill(new ImagePattern(new Image("/com/medyassin/Img/icons/pending.png", false)));
        manageUsersMask.setFill(new ImagePattern(new Image("/com/medyassin/Img/icons/user.png", false)));
        manageItemsMask.setFill(new ImagePattern(new Image("/com/medyassin/Img/icons/product.png", false)));
    }

    private void deleteItem() {
        int id = Integer.parseInt(itemCodeTF.getText());

        try {
            if(ItemController.deleteItem(id)) {
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
        refreshTable("all");

        // Clear inputs
        clearCustomerForm();
    }

    private void updateItem() {
        int id = Integer.parseInt(itemCodeTF.getText());
        String itemName = itemNameTF.getText();
        String itemPrice = itemPriceTF.getText();
        String itemImage = itemImageTF.getText();
        String itemType = itemTypeTF.getText();

        try {
            boolean success = ItemController.updateItem(id, itemName, itemImage, itemType, itemPrice);
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
        refreshTable("all");
    }

    private void clearCustomerForm() {
        itemNameTF.setText("");
        itemPriceTF.setText("");
        itemImageTF.setText("");
    }

    private void refreshTable(String itemType) {
        data = FXCollections.observableArrayList();
        try {
            data = ItemController.getAllItems(itemType);
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

        refreshTable("all");
    }

    private void setIDTF() {

        try {
            itemCodeTF.setText(ItemController.getNextID() + "");
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

    }
    private void addNewItem() throws IOException {
        String itemName = itemNameTF.getText();
        String itemPrice = itemPriceTF.getText();
        String itemImage = itemImageTF.getText();
        String itemType = itemTypeTF.getText();

        //Validate inputs
        boolean validated = validateInputs();


        if(validated) {
            try {
                if (!ItemController.addNewItem(itemName, itemPrice, itemImage, itemType)) {
                    CustomerAlert.display("error", "Can't add new user");
                } else {
                    CustomerAlert.display("success", "Le produit a été bien ajouté");
                }
            } catch (SQLException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }

            // Refresh
            refreshTable("all");

            // Clear inputs
            clearCustomerForm();

            // Set next id
            setIDTF();
        }

    }

    private boolean validateInputs() {
        RequiredFieldValidator validator = new RequiredFieldValidator();
        validator.setMessage("Ce champ est obligatoire");
        itemImageTF.getValidators().add(validator);
        itemNameTF.getValidators().add(validator);
        itemPriceTF.getValidators().add(validator);
        itemTypeTF.getValidators().add(validator);

        if(!itemNameTF.validate()) {
            return false;
        } else if(!itemPriceTF.validate()) {
            return false;
        } else if(!itemImageTF.validate()) {
            return false;
        } else if(!itemTypeTF.validate()) {
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
            BorderPane target = FXMLLoader.load(getClass().getResource("/com/medyassin/Views/Fxmls/ManageUsers.fxml"));
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

    @FXML
    public void manageCustomersClick(MouseEvent e) {

        Scene currentScene = manageCustomers.getScene();
        try {
            BorderPane target = FXMLLoader.load(getClass().getResource("/com/medyassin/Views/Fxmls/UserManageCustomers.fxml"));
            Utilities.switchScreen(currentScene, target,getClass().getResource("/com/medyassin/Views/Fxmls/UserManageCustomers.css").toExternalForm(), true, 600);
        } catch (IOException e1) {
            e1.printStackTrace();
        }
    }




}