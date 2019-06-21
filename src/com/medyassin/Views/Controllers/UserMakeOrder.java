package com.medyassin.Views.Controllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.validation.RequiredFieldValidator;
import com.medyassin.DatabaseControllers.ItemController;
import com.medyassin.DatabaseControllers.ManageCustomerController;
import com.medyassin.DatabaseControllers.NewOrderController;
import com.medyassin.DatabaseControllers.UserController;
import com.medyassin.Models.Customer;
import com.medyassin.Models.Item;
import com.medyassin.TableViewModels.AddNewOrderTVModel;
import com.medyassin.TableViewModels.CustomerTVModel;
import com.medyassin.Utilities.CustomAlert.CustomerAlert;
import com.medyassin.Utilities.Utilities;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class UserMakeOrder implements Initializable {
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
    private JFXButton logoutBtn;

    @FXML
    private Circle manageCustomersMask;

    @FXML
    private Circle makeOrderMask;

    @FXML
    private Circle viewAllOrdersMask;

    @FXML
    private Circle pendingOrdersMask;

    @FXML
    private Circle UserImageMaskCircle;

    @FXML
    private JFXTextField clientID;

    @FXML
    private TableColumn itemIDTC;

    @FXML
    private TableColumn itemAmountTC;

    @FXML
    private TableColumn itemPriceTC;

    @FXML
    private TableColumn itemQtTC;

    @FXML
    private TableColumn itemNameTC;

    @FXML
    private JFXTextField clientTel;

    @FXML
    private JFXComboBox<String> itemType;

    @FXML
    private JFXComboBox<String> itemName;

    @FXML
    private JFXComboBox<String> payementCB;

    @FXML
    private JFXComboBox<String> customersCB;

    @FXML
    private JFXTextField clientAdress;

    @FXML
    private JFXTextField qt;

    @FXML
    private  JFXTextField clientName;

    @FXML
    private JFXTextField orderID;

    @FXML
    private TableView itemsTable;

    @FXML
    private BorderPane borderPane;

    @FXML
    private Label totalLabel;

    @FXML
    private DatePicker orderDate;

    @FXML
    private Label nameLabel;

    private int total = 0;

    // data for the items table
    private ObservableList<AddNewOrderTVModel> data = FXCollections.observableArrayList();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        /*
        - Add icons to the sidebar
        - Add user image to the top bar
        - Add username to the top bar
        */
        setStaticItems();

        setVisibility();

        // Set Order ID
        setOrderID();

        // Set customers combobox and customer info
        setCustomersCB();

        // Set Item info
        setItemInfo();

        // Set Order Status
        setOrderStatusCB();

        // Set Items Table
        setItemsTable();
    }

    /* LOGIC HERE */

    private void setItemsTable() {
        // link columns with the model
        itemIDTC.setCellValueFactory(new PropertyValueFactory<AddNewOrderTVModel, String>("itemCode"));
        itemNameTC.setCellValueFactory(new PropertyValueFactory<AddNewOrderTVModel, String>("itemName"));
        itemPriceTC.setCellValueFactory(new PropertyValueFactory<AddNewOrderTVModel, String>("itemPrice"));
        itemQtTC.setCellValueFactory(new PropertyValueFactory<AddNewOrderTVModel, String>("itemQT"));
        itemAmountTC.setCellValueFactory(new PropertyValueFactory<AddNewOrderTVModel, String>("itemAmount"));

        refreshItemsTable();
    }

    private void refreshItemsTable() {
        itemsTable.setItems(data);
    }

    private void setOrderID() {
        try {
            orderID.setText(NewOrderController.getNextID()+ "");
        } catch(SQLException e) {
            e.printStackTrace();
        } catch(ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void setCustomersCB() {
        int[] customersIDs;
        ArrayList<Customer> customers;
        try {
            customers = ManageCustomerController.getAllCustomers();
            customersIDs = new int[customers.size()];
            int i = 0;
            customersCB.getItems().clear();
            for(Customer c: customers) {
                customersCB.getItems().add(c.getcName());
                customersIDs[i++] = Integer.parseInt(c.getcID());
            }

            // set input based on choice

            customersCB.setOnAction(e -> {
                // disable all inputs;
                clientID.setDisable(true);
                clientName.setDisable(true);
                clientTel.setDisable(true);
                clientAdress.setDisable(true);

                int selectedIndex = customersCB.getSelectionModel().getSelectedIndex();
                System.out.println(customersIDs);
                if(selectedIndex >= 0) {
                    int id = customersIDs[selectedIndex];
                    System.out.println("selectedIndex: " + id);
                    clientID.setText(id + "");
                    clientName.setText(customers.get(selectedIndex).getcName());
                    clientTel.setText(customers.get(selectedIndex).getcPhoneN());
                    clientAdress.setText(customers.get(selectedIndex).getcAddress());
                }
            });

        } catch (SQLException e1) {
            e1.printStackTrace();
        } catch (ClassNotFoundException e1) {
            e1.printStackTrace();
        }
    }

    private void setItemInfo() {
        try {
            // set category cb
            ArrayList<String> cats = ItemController.getCategories();

            for(String cat: cats) {
                itemType.getItems().add(cat);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void confirmOrder(ActionEvent event) {
        if(validateOrderInputs()) {
            // get inputs :Order information
            String orderIDInput = orderID.getText();
            String orderDateInput = orderDate.getValue().toString();
            String customerID = clientID.getText();
            String orderStatus = payementCB.getValue().equals("payez maintenant") ? "1" : "0";

            try {
                if(NewOrderController.addNewOrder(orderDateInput, customerID, orderStatus)) {
                    CustomerAlert.display("success", "Votre commande a été bien ajoutée");

                    for(AddNewOrderTVModel curr: (ObservableList<AddNewOrderTVModel>)itemsTable.getItems()) {
                        // Add order details to DB
                        if(!NewOrderController.addNewOrderDetails(orderID.getText() + "", curr.getItemCode(), curr.getItemQT())) {
                            try {
                                CustomerAlert.display("error", "Can't add order details");
                            } catch(IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }

                    clearInputs();

                } else {
                    CustomerAlert.display("error", "Veuillez verifier les infromations de la commande");
                }
            } catch(SQLException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void clearInputs() {
        orderDate.setValue(LocalDate.now());
        data = FXCollections.observableArrayList();
        itemsTable.setItems(data);
        totalLabel.setText("0 DH");
        setOrderID();
    }

    @FXML
    void addClient(ActionEvent event) throws IOException{

        // Add new Customer if inputs are enabled
        if(clientName.isDisable()) {
            // clear inputs
            try {
                clientID.setText(ManageCustomerController.getNextID() + "");
                clientName.setText("");
                clientAdress.setText("");
                clientTel.setText("");
            } catch (SQLException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }

            // enable inputs
            clientName.setDisable(false);
            clientAdress.setDisable(false);
            clientTel.setDisable(false);
        } else {
            if(validateInputs()) {
                String name = clientName.getText();
                String phoneN = clientAdress.getText();
                String address = clientTel.getText();

                //TODO Validation
                boolean validated = true;

                if(validated) {
                    try {
                        if (!ManageCustomerController.addNewCustomer(name, phoneN, address)) {
                            CustomerAlert.display("error", "Can't add new customer");
                        } else {
                            CustomerAlert.display("success", "Le client a été bien ajouté");
                        }
                    } catch (SQLException e) {
                        e.printStackTrace();
                    } catch (ClassNotFoundException e) {
                        e.printStackTrace();
                    }

                    // Update combobox
                    setCustomersCB();

                } else {
                    CustomerAlert.display("error", "Please enter valide data !");
                }
            }
        }

    }

    private boolean validateInputs() {
        RequiredFieldValidator validator = new RequiredFieldValidator();
        validator.setMessage("Ce champ est obligatoire");
        clientName.getValidators().add(validator);
        clientTel.getValidators().add(validator);
        clientAdress.getValidators().add(validator);

        if(!clientName.validate()) {
            return false;
        } else if(!clientTel.validate()) {
            return false;
        } else if(!clientAdress.validate()) {
            return false;
        }

        return true;
    }

    @FXML
    private void addItem(ActionEvent event) {
        if(validateItemInputs()) {
            try {
                // get inputs
                int itemCode = ItemController.getItemCodeFromName(itemName.getValue());
                Item item = ItemController.getItem(itemCode+ "");

                int amount = Integer.parseInt(item.getItemPrice()) * Integer.parseInt(qt.getText());

                AddNewOrderTVModel model = new AddNewOrderTVModel(item.getItemCode(), item.getItemName(), item.getItemPrice(), qt.getText(), amount + "");
                data.add(model);
                itemsTable.setItems(data);

                // Set total
                total = 0;
                for(AddNewOrderTVModel curr: (ObservableList<AddNewOrderTVModel>)itemsTable.getItems()) {
                    // total calc
                    total += Integer.parseInt(curr.getItemAmount());
                }

                totalLabel.setText(total + " DH");


            } catch (SQLException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

    @FXML
    private void itemTypeAction(ActionEvent e) throws SQLException, ClassNotFoundException {
        // clear
        itemName.getItems().clear();

        // set items cb
        ArrayList<String> items = ItemController.getItemOfCat(itemType.getValue());
        for(String item: items) {
            itemName.getItems().add(item);
        }
    }

    private void setOrderStatusCB() {
        payementCB.getItems().add("payez maintenant");
        payementCB.getItems().add("paiement à la livraison");
    }

    @FXML
    public void deleteOrderItem(ActionEvent e) throws SQLException, ClassNotFoundException, IOException {
        // get row index
        ObservableList ol = itemsTable.getSelectionModel().getSelectedCells();
        if(ol.size() > 0) {
            TablePosition<AddNewOrderTVModel, String> tp = (TablePosition<AddNewOrderTVModel, String>)ol.get(0);
            int index = tp.getRow();
            AddNewOrderTVModel orderData = (AddNewOrderTVModel)itemsTable.getSelectionModel().getSelectedItem();

            int amount = Integer.parseInt(orderData.getItemAmount());

            total -= amount;

            totalLabel.setText(total + "");

            // remove item from data
            data.remove(index);


            // set data again
            itemsTable.setItems(data);
        }
    }


    private boolean validateOrderInputs() {
        RequiredFieldValidator validator = new RequiredFieldValidator();
        validator.setMessage("Ce champ est obligatoire");
        payementCB.getValidators().add(validator);
        customersCB.getValidators().add(validator);

        if(orderDate.getValue() == null) {
            try {
                CustomerAlert.display("error", "Veuillez choisir une date pour la commande");
            } catch (IOException e) {
                e.printStackTrace();
            }
            return false;

        } else if(total == 0) {
            try {
                CustomerAlert.display("error", "Veuillez ajouter des produits");
            } catch (IOException e) {
                e.printStackTrace();
            }
            return false;
        } else if(!payementCB.validate()) {
            return false;
        } else if(!customersCB.validate()) {
            return false;
        }
        return true;
    }

    private boolean validateItemInputs() {
        RequiredFieldValidator validator = new RequiredFieldValidator();
        validator.setMessage("Ce champ est obligatoire");

        itemType.getValidators().add(validator);
        itemName.getValidators().add(validator);
        qt.getValidators().add(validator);

        if(!itemType.validate()) {
            return false;
        } else if(!itemName.validate()) {
            return false;
        } else if(!qt.validate()) {
            return false;
        }
        return true;
    }

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

    /* SECTION UserMakeOrder */



    private void setStaticItems() {
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
    private void logoutBtnAction(ActionEvent actionEvent) throws IOException {
        // Go to login page
        Scene currentScene = logoutBtn.getScene();
        AnchorPane anchorPane = FXMLLoader.load(getClass().getResource("/com/medyassin/Views/Fxmls/LoginScreen.fxml"));
        Utilities.switchScreen(currentScene, anchorPane, getClass().getResource("/com/medyassin/Views/Fxmls/LoginScreen.css").toExternalForm(), false, 600);
    }

    @FXML
    public void userManagClick(MouseEvent e) {
        Scene currentScene = manageCustomers.getScene();
        try {
            BorderPane target = FXMLLoader.load(getClass().getResource("/com/medyassin/Views/Fxmls/UserManageCustomers.fxml"));
            Utilities.switchScreen(currentScene, target,getClass().getResource("/com/medyassin/Views/Fxmls/UserManageCustomers.css").toExternalForm(), true, 600);
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
    public void allOrderClick(MouseEvent e) {
        Scene currentScene = manageCustomers.getScene();
        try {
            BorderPane target = FXMLLoader.load(getClass().getResource("/com/medyassin/Views/Fxmls/ViewAllOrders.fxml"));
            Utilities.switchScreen(currentScene, target,getClass().getResource("/com/medyassin/Views/Fxmls/ViewAllOrders.css").toExternalForm(), true, 600);
        } catch (IOException e1) {
            e1.printStackTrace();
        }
    }
}
