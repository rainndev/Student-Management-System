/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package studentmanagementsystem.controller;

import java.net.URL;
import java.sql.Date;
import java.time.LocalDate;
import java.util.List;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.stage.Stage;
import studentmanagementsystem.model.Program;
import studentmanagementsystem.model.Role;
import studentmanagementsystem.model.Student;
import studentmanagementsystem.services.ProgramService;
import studentmanagementsystem.services.StudentService;

/**
 * FXML Controller class
 *
 * @author rainndev
 */
public class EditStudentDialogController implements Initializable {

    private Student student;
    
    @FXML
    private TextField fieldFirstName;
    @FXML
    private TextField fieldLastName;
    private ComboBox<String> comboGender;
    @FXML
    private DatePicker fieldbirthDate;
    @FXML
    private TextField fieldAddresss;
    @FXML
    private TextField fieldContactNumber;
    @FXML
    private ComboBox<Program> comboProgram;
    private TextField fieldYearLevel;
    @FXML
    private TextField fieldProfilePath;
    @FXML
    private Label txtMessage;
    @FXML
    private Button btnEditStudent;
    @FXML
    private Button btnDeleteStudent;
    @FXML
    private RadioButton rMale;
    @FXML
    private ToggleGroup gender;
    @FXML
    private RadioButton rFemale;
    @FXML
    private ComboBox<Integer> comboYearLevel;
   
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        ProgramService programService = new ProgramService();
        List<Program> programList = programService.getAllPrograms();
        comboProgram.getItems().addAll(programList);
        comboYearLevel.getItems().addAll(1, 2, 3, 4);
    }    

    @FXML
    private void handleBirthDate(ActionEvent event) {
    }

    @FXML
    private void handleEditStudent(ActionEvent event) {
        StudentService studentService = new StudentService();
        
        String firstName = fieldFirstName.getText().trim();
        String lastName = fieldLastName.getText().trim();
        String gender = comboGender.getValue().trim();
        String address = fieldAddresss.getText();
        String contact = fieldContactNumber.getText().trim();

        
        int yearLevel;
        try {
            yearLevel = Integer.parseInt(fieldYearLevel.getText().trim());
        } catch (NumberFormatException e) {
            txtMessage.setText("Error: Year Level must be a number.");
            return; 
        }
        
        Date birthDate = null;
        LocalDate localDate = fieldbirthDate.getValue();
        if (localDate != null) {
            birthDate = Date.valueOf(localDate); 
        }
        
        Program selectedProgram = comboProgram.getValue(); 
        String profilePhoto = fieldProfilePath.getText().trim();
        String randomUsername = java.util.UUID.randomUUID().toString();
        Role role = this.student.getRole();
      
        Student student = new Student(
           role,
           selectedProgram,
           yearLevel,
           gender,
           birthDate, 
           address,
           contact,
           1,
           firstName,
           lastName,
           randomUsername,
           profilePhoto     
        );

        // add the user id from student came from setStudent params
        student.setUserId(this.student.getUserID());
        
        boolean isSuccess =  studentService.editStudent(student);
        
        if (isSuccess) {
            handleCloseDialog(event);
        } else {
            txtMessage.setText("Student  Edit failed!");
        }
        
        txtMessage.setVisible(true);
    }
    
    public void setStudent(Student student) {
        this.student = student;

        if (student != null) {
            fieldFirstName.setText(student.getFirstName());
            fieldLastName.setText(student.getLastName());
            
            if (rMale.isSelected()) {
                rMale.setSelected(true);
            } else {
                rFemale.setSelected(true);
            }
            
            fieldbirthDate.setValue(student.getBirthDate().toLocalDate());
            fieldAddresss.setText(student.getAddress());
            fieldContactNumber.setText(student.getContactNumber());
            comboProgram.setValue(student.getProgram());
            comboYearLevel.setValue(student.getYearLevel());
            fieldProfilePath.setText(student.getProfilePhoto());
        }
    }
    
  

    
    @FXML
    private void handleDeleteStudent(ActionEvent event) {
        int studentID = student.getUserID();
        StudentService studentService = new StudentService();
        studentService.deleteStudent(studentID);
        handleCloseDialog(event);
    }
    
    private void handleCloseDialog(ActionEvent event) {
         Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
         stage.close();
    }
      
}
