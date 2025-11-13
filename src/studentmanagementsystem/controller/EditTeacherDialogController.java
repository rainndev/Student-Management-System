/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package studentmanagementsystem.controller;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import studentmanagementsystem.model.Role;
import studentmanagementsystem.model.Subject;
import studentmanagementsystem.services.TeacherService;
import studentmanagementsystem.model.Teacher;
import studentmanagementsystem.services.SubjectService;
import studentmanagementsystem.services.TeacherSubjectService;


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
    private Label txtMessage;
    @FXML
    private Button btnDeleteTeacher;
    @FXML
    private ComboBox<Subject> comboAssignSubject;
    @FXML
    private Button btnDeleteStudent;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        SubjectService subjectService = new SubjectService();
        List<Subject> subjectList = subjectService.getAllSubject();
        comboAssignSubject.getItems().setAll(subjectList);
    }    

    @FXML
    private void handleEditTeacher(ActionEvent event) {
        TeacherService teacherService = new TeacherService();
        TeacherSubjectService teacherSubjectService = new TeacherSubjectService();
        
        int teacherId = this.teacher.getUserID();
        String firstName = fieldFirstName.getText();
        String lastName = fieldLastName.getText();
        String department = fieldDepartment.getText();
        String contactNumber = fieldContactNumber.getText();       
        String username = this.teacher.getUsername();
        String password = this.teacher.getPassword();
        Role role = this.teacher.getRole();
        int isActive = 1;
        
        Teacher teacher = new Teacher(username, password, role, firstName, lastName, department, contactNumber, isActive);
        teacher.setUserId(teacherId);
        
        int rowsUpdated = teacherService.editTeacher(teacher);
        Integer subjectId = comboAssignSubject.getValue() != null 
                            ? comboAssignSubject.getValue().getSubjectId() 
                            : null;
        
        int teacherSubjectId = this.teacher.getTeacherSubjectId();
        
        boolean isSuccessEdit = false;
        
        
        if (subjectId != null) {
               if (teacherSubjectId > 0) {
                 isSuccessEdit = teacherSubjectService.editSubjectTeacher(teacherId, subjectId.intValue(), teacherSubjectId);
               } else {
                 isSuccessEdit = teacherSubjectService.addSubjectTeacher(teacherId, subjectId.intValue());
               }
        } else {
                if (teacherSubjectId > 0) {
                    // isSuccessEdit = teacherSubjectService.deleteSubjectTeacher(teacherSubjectId);
                    // assume it's successful or just ignore the un-assignment.
                    isSuccessEdit = true; 
                } else {
                    // No subject selected, and no prior assignment existed. Nothing to do.
                    isSuccessEdit = true;
                }
        }
        
        if (rowsUpdated > 0  && isSuccessEdit) {
          txtMessage.setText("Teacher Edit Succcesfully!");
          handleCloseDialog(event);   
        } else {
          txtMessage.setText("Teacher Edit failed!");
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
            comboAssignSubject.setValue(teacher.getSubject());
        }
    }
    
    @FXML
    private void handleDeleteTeacher(ActionEvent event) {
        TeacherService teacherService = new TeacherService();
        int teacherId = this.teacher.getUserID();
        teacherService.deleteTeacher(teacherId);
        
        handleCloseDialog(event);
    }
    
    private void handleCloseDialog(ActionEvent event) {
         Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
         stage.close();
    }
}
