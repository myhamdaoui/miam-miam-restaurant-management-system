package com.medyassin.Views.Controllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import com.medyassin.DatabaseControllers.LoginController;
import com.medyassin.Utilities.Utilities;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class LoginScreen implements Initializable {
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
        // Nothing until now
    }

    @FXML
    private void btnLoginAction(ActionEvent actionEvent) {
        //Get Username input
        String usernameInput = username.getText();

        //Get password input
        String passwordInput = password.getText();

        //Check values form database

        //login
        try {
            //Get Main Screen XML of the user[Cassier]
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/medyassin/Views/Fxmls/CassierMainScreen.fxml"));
            BorderPane userMainScreen = loader.load();
            // If user inputs are correct, the switch to the main screen
            if(LoginController.login(usernameInput, passwordInput)) {
                //get the current stage
                Scene currentScene = loginScreen.getScene();
                Utilities.switchScreen(currentScene, userMainScreen, getClass().getResource("./../Fxmls/CassierMainScreen.css").toExternalForm());
            } else {
                System.out.println("Please verify login information");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch(IOException e) {
            e.printStackTrace();
        }
    }
}
