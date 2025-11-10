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
import studentmanagementsystem.services.TeacherService;
import studentmanagementsystem.model.Teacher;

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
    private Button btnAddStudent;
    @FXML
    private Label txtMessage;

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
        int role = 1; //1 = Teacher in role table
        
        Teacher teacher = new Teacher(randomUsername, password, role,firstName, lastName, department, contact, 1);
        int rowsInserted = teacherService.addTeacher(teacher);
        
        if (rowsInserted > 0 ) {
            txtMessage.setText("Teacher  Added Succcesfully!");   
            handleCloseDialog(event);
        } else {
            txtMessage.setText("Teacher  Added failed!");
        }
        
        txtMessage.setVisible(true);
    }
    
    private void handleCloseDialog(ActionEvent event) {
         Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
         stage.close();
    }
    
}
