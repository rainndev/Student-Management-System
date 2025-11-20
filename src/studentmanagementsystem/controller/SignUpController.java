/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package studentmanagementsystem.controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import studentmanagementsystem.Main;

/**
 * FXML Controller class
 *
 * @author rainndev
 */
public class SignUpController implements Initializable {

    @FXML
    private TextField firstNameField;
    @FXML
    private TextField lastNameField;
    @FXML
    private TextField usernameField;
    @FXML
    private TextField passwordField;
    @FXML
    private ComboBox<String> roleComboBox;
    @FXML
    private Label txtMessage;
    @FXML
    private Button btnSignup;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        roleComboBox.getItems().addAll("Student", "Teacher");
    }    


    @FXML
    private void handleClickedLogin(MouseEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/studentmanagementsystem/view/Login.fxml"));

            Scene loginScene = new Scene(root);
            loginScene.getStylesheets().add(getClass().getResource("/studentmanagementsystem/css/global.css").toExternalForm());
            // Switch the stage
            Main.mainStage.setScene(loginScene);
            Main.mainStage.setTitle("Log in");
            Main.mainStage.show();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleSignup(ActionEvent event) {
        
    }
}
