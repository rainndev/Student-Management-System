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


/**
 * FXML Controller class
 *
 * @author rainndev
 */
public class EditTeacherDialogController implements Initializable {

    private Teacher teacher;
    
    @FXML
    private TextField fieldFirstName;
    @FXML
    private TextField fieldLastName;
    @FXML
    private TextField fieldDepartment;
    @FXML
    private TextField fieldContactNumber;
    @FXML
    private Button btnEditStudent;
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
    private void handleEditTeacher(ActionEvent event) {
        TeacherService teacherService = new TeacherService();
        
        String firstName = fieldFirstName.getText();
        String lastName = fieldLastName.getText();
        String department = fieldDepartment.getText();
        String contactNumber = fieldContactNumber.getText();       
        String username = this.teacher.getUsername();
        String password = this.teacher.getPassword();
        Role role = this.teacher.getRole();
        
        Teacher teacher = new Teacher(username, password, role, firstName, lastName, department, contactNumber, 1);
        teacher.setUserID(this.teacher.getUserId());
        int rowsUpdated = teacherService.editTeacher(teacher);
        
        if (rowsUpdated > 0 ) {
          txtMessage.setText("Student  Edit Succcesfully!");
          handleCloseDialog(event);   
        } else {
          txtMessage.setText("Student  Edit failed!");
        }
        
        txtMessage.setVisible(true);
    }
    
    
    public void setTeacher(Teacher teacher){
        this.teacher = teacher;
        
        if (teacher != null) {
            fieldFirstName.setText(teacher.getFirstName());
            fieldLastName.setText(teacher.getLastName());
            fieldDepartment.setText(teacher.getDepartment());
            fieldContactNumber.setText(teacher.getContactNumber());
        }
    }
    
    private void handleCloseDialog(ActionEvent event) {
         Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
         stage.close();
    }
    
    
}
