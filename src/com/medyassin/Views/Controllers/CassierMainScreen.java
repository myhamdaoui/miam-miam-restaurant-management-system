package com.medyassin.Views.Controllers;

import com.jfoenix.controls.JFXButton;
import com.medyassin.Utilities.Utilities;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;

import javax.swing.border.Border;
import java.awt.event.ActionListener;
import java.awt.event.MouseListener;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class CassierMainScreen implements Initializable, EventHandler<MouseEvent> {
    @FXML
    private BorderPane borderPane;
    // Logout btn
    @FXML
    private JFXButton logoutBtn;

    @FXML
    private Circle UserImageMaskCircle;

    @FXML
    private Pane manageCustomers;

    @FXML
    private Pane makeOrder;

    @FXML
    private Pane viewAllOrders;

    @FXML
    private Pane pendingOrders;

    @FXML
    private Circle manageCustomersMask;



    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // Set Image for the circular mask
        UserImageMaskCircle.setFill(new ImagePattern(new Image("/com/medyassin/Img/user_img.jpg", false)));

        // Set no focus for logout btn
        logoutBtn.setDisableVisualFocus(true);

        // Set Image Icon for mask of 'Manage customers'
        manageCustomersMask.setFill(new ImagePattern(new Image("/com/medyassin/Img/icons/user.png", false)));

        // Set Mouse event on left menu
        makeOrder.setOnMouseEntered(this);
        makeOrder.setOnMouseExited(this);

        viewAllOrders.setOnMouseEntered(this);
        viewAllOrders.setOnMouseExited(this);

        pendingOrders.setOnMouseEntered(this);
        pendingOrders.setOnMouseExited(this);
    }

    @FXML
    private void logoutBtnAction(ActionEvent actionEvent) throws IOException {
        // Go to login page
        Scene currentScene = borderPane.getScene();
        AnchorPane anchorPane = FXMLLoader.load(getClass().getResource("/com/medyassin/Views/Fxmls/LoginScreen.fxml"));
        Utilities.switchScreen(currentScene, anchorPane, getClass().getResource("/com/medyassin/Views/Fxmls/LoginScreen.css").toExternalForm());
    }

    @FXML
    private void manageCustomersHover() {
        manageCustomers.setStyle("-fx-background-color: #1bb85a;");
    }

    @FXML
    private void manageCustomersHoverEnd() {
        manageCustomers.setStyle("-fx-background-color:  #22DB6C;");
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
}