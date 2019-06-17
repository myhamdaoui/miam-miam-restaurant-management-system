package com.medyassin.Views.Controllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import com.medyassin.DatabaseControllers.LoginController;
import com.medyassin.Utilities.CustomAlert.CustomerAlert;
import com.medyassin.Utilities.Utilities;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;

import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class LoginScreen implements Initializable {
    @FXML
    private Label toast;

    @FXML
    private AnchorPane loginScreen;

    @FXML
    private JFXButton loginBtn;

    @FXML
    private JFXTextField username;

    @FXML
    private JFXPasswordField password;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    private void login() {
        //Get Username input
        String usernameInput = username.getText();

        //Get password input
        String passwordInput = password.getText();

        //Check values form database

        //login
        try {
            //Get Main Screen XML of the user[Cassier]
            BorderPane userMainScreen = FXMLLoader.load((getClass().getResource("/com/medyassin/Views/Fxmls/UserManageCustomers.fxml")));

            // If user inputs are correct, the switch to the main screen
            if(LoginController.login(usernameInput, passwordInput)) {
                //get the current stage
                Scene currentScene = loginScreen.getScene();
                Utilities.switchScreen(currentScene, userMainScreen, getClass().getResource("./../Fxmls/UserManageCustomers.css").toExternalForm(), true);
            } else {
                //Utilities.showToast("Please verify login information", toast);
                try {
                    CustomerAlert.display("error", "Please verify login information");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch(IOException e) {
            e.printStackTrace();
        }
    }


    @FXML
    private void btnLoginAction(ActionEvent actionEvent) {
        login();
    }
}
