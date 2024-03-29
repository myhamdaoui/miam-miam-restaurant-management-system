package com.medyassin.Views.Controllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.validation.RequiredFieldValidator;
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
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;

import javax.swing.*;
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

    @FXML
    private Circle heroImgCircle;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        heroImgCircle.setFill(new ImagePattern(new Image("/com/medyassin/Img/back1.jpg", false)));

    }

    private boolean validateInputs() {
        RequiredFieldValidator validator = new RequiredFieldValidator();
        validator.setMessage("Ce champ est obligatoire");
        username.getValidators().add(validator);
        password.getValidators().add(validator);

        if(username.getText().equals("")) {
            username.validate();
        }
        else if(password.getText().equals("")) {
            username.validate();
            password.validate();
        } else {
            return true;
        }
        return false;
    }

    private void login() {
        System.out.println("hhhhhhh");
        //Get Username input
        String usernameInput = username.getText();

        //Get password input
        String passwordInput = password.getText();

        if(validateInputs()){
            //login
            try {

                // If user inputs are correct, the switch to the main screen
                if(LoginController.login(usernameInput, passwordInput)) {
                    //Check user role
                    String role = LoginController.getUserRole(usernameInput);
                    System.out.println(role);
                    if(role.equals("admin")) {


                        //Get Main Screen XML of the user[Adminadmi]
                        BorderPane userMainScreen = FXMLLoader.load((getClass().getResource("/com/medyassin/Views/Fxmls/ManageUsers.fxml")));

                        //get the current stage
                        Scene currentScene = loginScreen.getScene();
                        Utilities.switchScreen(currentScene, userMainScreen, getClass().getResource("/com/medyassin/Views/Fxmls/UserManageCustomers.css").toExternalForm(), true, 600);
                    } else {

                        System.out.println("cassier");

                        //Get Main Screen XML of the user[Cassier]
                        BorderPane userMainScreen = FXMLLoader.load((getClass().getResource("/com/medyassin/Views/Fxmls/UserManageCustomers.fxml")));


                        //get the current stage
                        Scene currentScene = loginScreen.getScene();
                        Utilities.switchScreen(currentScene, userMainScreen, getClass().getResource("/com/medyassin/Views/Fxmls/UserManageCustomers.css").toExternalForm(), true, 600);
                    }
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
    }


    @FXML
    private void btnLoginAction(ActionEvent actionEvent) {
        login();
    }
}
