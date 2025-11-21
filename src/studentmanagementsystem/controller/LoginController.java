/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package studentmanagementsystem.controller;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import studentmanagementsystem.databases.DatabaseConnection;
import studentmanagementsystem.Main;
import studentmanagementsystem.model.SessionManager;

/**
 * FXML Controller class
 *
 * @author rainndev
 */
public class LoginController implements Initializable {

    @FXML
    private TextField usernameField;
    @FXML
    private TextField passwordField;
    @FXML
    private Button btnLogin;
    @FXML
    private Label txtMessage;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
    }   
    
    private void goToDashboard(Event event) {
       try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/studentmanagementsystem/view/Dashboard.fxml"));
            Parent root = loader.load();            
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setTitle("Dashboard");
            stage.setScene(scene);
            stage.show();

        } catch (IOException ex) {
            System.getLogger(LoginController.class.getName())
                    .log(System.Logger.Level.ERROR, (String) null, ex);
        }
    }

    @FXML
    private void handleLogin(ActionEvent event) {
        String username = usernameField.getText().trim();
        String password = passwordField.getText().trim();
        
        if (username.isEmpty() || password.isEmpty()) {
            txtMessage.setText("Please fill the form");
            txtMessage.setStyle("-fx-text-fill: red;");
            txtMessage.setVisible(true);
            return; 
        }
        
        DatabaseConnection connectNow  = new DatabaseConnection();
        Connection connectDB = connectNow.getConnection();
        
        String verifyLogin = "SELECT * FROM user  WHERE username = ? AND password = ? ";
        
        try {
            PreparedStatement statement = connectDB.prepareStatement(verifyLogin);
            statement.setString(1, username);
            statement.setString(2, password);
            
            ResultSet result = statement.executeQuery();
            
            
              if (result.next()) {
                int id = result.getInt("id");
                String firstName = result.getString("first_name");
                String lastName = result.getString("last_name");
                
                SessionManager.setSession(id, firstName, lastName);
                
                txtMessage.setText("Login successful!");
                txtMessage.setStyle("-fx-text-fill: green;");
                goToDashboard(event);
                
            } else {
                txtMessage.setText("Invalid username or password.");
                txtMessage.setStyle("-fx-text-fill: red;");
            }
              txtMessage.setVisible(true); 
        } catch (Exception e) {
            e.printStackTrace();
            txtMessage.setText("Error connecting to database.");
        }
    }

    @FXML
    private void handleClickedCreateOne(MouseEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/studentmanagementsystem/view/SignUp.fxml"));
            Scene signupScene = new Scene(root);
            Main.mainStage.setScene(signupScene);
            Main.mainStage.setTitle("Create an Account"); 
            Main.mainStage.show();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
