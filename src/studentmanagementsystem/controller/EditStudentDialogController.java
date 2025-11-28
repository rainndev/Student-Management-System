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
import studentmanagementsystem.util.Validator;

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
    @FXML
    private DatePicker fieldbirthDate;
    @FXML
    private TextField fieldAddresss;
    @FXML
    private TextField fieldContactNumber;
    @FXML
    private ComboBox<Program> comboProgram;
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
    @FXML
    private Label lblbStudentTitle;
   
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
        
        if (!Validator.isRequired(fieldFirstName.getText())) {
            txtMessage.setText("Error: First name is required.");
            return;
        }

        if (!Validator.isRequired(fieldLastName.getText())) {
            txtMessage.setText("Error: Last name is required.");
            return;
        }

        if (!Validator.isRequired(fieldAddresss.getText())) {
            txtMessage.setText("Error: Address is required.");
            return;
        }

        if (!Validator.isRequired(fieldContactNumber.getText())) {
            txtMessage.setText("Error: Contact number is required.");
            return;
        }

        if (!Validator.isNumeric(fieldContactNumber.getText())) {
            txtMessage.setText("Error: Contact number must be numeric.");
            return;
        }

        if (!Validator.isSelected(comboYearLevel)) {
            txtMessage.setText("Error: Please select a year level.");
            return;
        }

        if (!Validator.isSelected(comboProgram)) {
            txtMessage.setText("Error: Please select a program.");
            return;
        }

        if (!Validator.isDateSelected(fieldbirthDate.getValue())) {
            txtMessage.setText("Error: Please select a birth date.");
            return;
        }
        
        
        StudentService studentService = new StudentService();
        
        String firstName = fieldFirstName.getText().trim();
        String lastName = fieldLastName.getText().trim();
        String gender = rFemale.isSelected() ? "Female" : "Male";
        String address = fieldAddresss.getText();
        String contact = fieldContactNumber.getText().trim();
        Integer yearLevel = comboYearLevel.getValue();
        
        
        Date birthDate = null;
        LocalDate localDate = fieldbirthDate.getValue();
        if (localDate != null) {
            birthDate = Date.valueOf(localDate); 
        }
        
        Program selectedProgram = comboProgram.getValue(); 
        String profilePhoto = fieldProfilePath.getText() == null
        ? "" : fieldProfilePath.getText().trim();
        
        String username = this.student.getUsername().isEmpty() || this.student.getUsername() == null 
                ? java.util.UUID.randomUUID().toString() : this.student.getUsername();
        
        
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
           username,
           profilePhoto     
        );

        // add the user id and pass from student came from setStudent params
        student.setUserId(this.student.getUserID());
        student.setPassword(this.student.getPassword());
        
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

        System.out.println("Edit current student id: " + student.getUserID());
        
        if (student != null) {
            fieldFirstName.setText(student.getFirstName());
            fieldLastName.setText(student.getLastName());
            
            
            if (rMale.isSelected()) {
                rMale.setSelected(true);
            } else if (rFemale.isSelected()) {
                rFemale.setSelected(true);
            } else {
                rMale.setSelected(true);
            }
            
            if (student.getBirthDate() != null) {
                fieldbirthDate.setValue(student.getBirthDate().toLocalDate());
            } else {
                fieldbirthDate.setValue(null); 
            }
            
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
      
    
    public void setDialogTitle(String value) {
        lblbStudentTitle.setText(value);
        
        if ("My Information".equals(lblbStudentTitle.getText())) {
            btnDeleteStudent.setVisible(false);
            btnDeleteStudent.setDisable(true);
        }
    }
}
