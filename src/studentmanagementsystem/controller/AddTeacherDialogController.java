/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package studentmanagementsystem.controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import studentmanagementsystem.model.Role;
import studentmanagementsystem.services.TeacherService;
import studentmanagementsystem.model.Teacher;
import studentmanagementsystem.util.Validator;

/**
 * FXML Controller class
 *
 * @author rainndev
 */
public class AddTeacherDialogController implements Initializable {

    @FXML
    private TextField fieldFirstName;
    @FXML
    private TextField fieldLastName;
    @FXML
    private TextField fieldDepartment;
    @FXML
    private TextField fieldContactNumber;
    @FXML
    private Label txtMessage;
    @FXML
    private Button btnAddTeacher;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void handleAddTeacher(ActionEvent event) {
        TeacherService teacherService = new TeacherService();
        String firstName = fieldFirstName.getText().trim();
        String lastName = fieldLastName.getText().trim();
        String department = fieldDepartment.getText();
        String contact = fieldContactNumber.getText().trim();
        String randomUsername = java.util.UUID.randomUUID().toString();
        String password = "123";                
        Role role = new Role(1); // 1 for Teacher
        int isActive = 1;
        
        
        txtMessage.setText("");
        
        if (!Validator.isRequired(firstName)) {
            txtMessage.setText("Error: First name is required.");
            return;
        }

        if (!Validator.isRequired(lastName)) {
            txtMessage.setText("Error: Last name is required.");
            return;
        }
        
        
        if (!Validator.isAlpha(department)) {
            txtMessage.setText("Error: Department must be String.");
            return;
        }
        
        if (!Validator.isRequired(department)) {
            txtMessage.setText("Error: Department is required.");
            return;
        }
        
        
        if (!Validator.isRequired(contact)) {
            txtMessage.setText("Error: Contact number is required.");
            return;
        }

        if (!Validator.isNumeric(contact)) {
            txtMessage.setText("Error: Contact number must be numeric.");
            return;
        }
        
        Teacher teacher = new Teacher(randomUsername, password, role, firstName, lastName, department, contact, isActive);
        boolean isSuccess = teacherService.addTeacher(teacher);
        
        if (isSuccess) {
            txtMessage.setText("");
            handleCloseDialog(event);
        } else {
            txtMessage.setText("Teacher  Added failed!");
        }
    }
    
    private void handleCloseDialog(ActionEvent event) {
         Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
         stage.close();
    }
    
}
