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
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import studentmanagementsystem.model.Program;
import studentmanagementsystem.model.Student;
import java.sql.Date;
import java.time.LocalDate;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import studentmanagementsystem.services.ProgramService;
import studentmanagementsystem.services.StudentService;


/**
 * FXML Controller class
 *
 * @author rainndev
 */
public class AddStudentDialogController implements Initializable {

    @FXML
    private TextField fieldFirstName;
    @FXML
    private TextField fieldLastName;
    @FXML
    private ComboBox<String> comboGender;
    @FXML
    private TextField fieldAddresss;
    @FXML
    private TextField fieldContactNumber;
    @FXML
    private TextField fieldYearLevel;
    @FXML
    private Button btnAddStudent;
    @FXML
    private ComboBox<Program> comboProgram;
    @FXML
    private DatePicker fieldbirthDate;
    @FXML
    private TextField fieldProfilePath;
    @FXML
    private Label txtMessage;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        ProgramService programService = new ProgramService();
        List<Program> programList = programService.getAllPrograms();
        comboProgram.getItems().addAll(programList);
        comboGender.getItems().addAll("Male", "Female");
    }

 
    @FXML
    private void handleAddStudent(ActionEvent event) {
        
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
        int roleID = 2;
        int isActive = 1;
        
        
        Student student = new Student(
           roleID,
           selectedProgram,
           yearLevel,
           gender,
           birthDate, 
           address,
           contact,
           isActive,
           firstName,
           lastName,
           randomUsername,
           profilePhoto     
        );
        
       
        
        int rowsInserted =  studentService.addStudent(student);
        
        if (rowsInserted > 0 ) {
            txtMessage.setText("Student  Added Succcesfully!");   
            handleCloseDialog(event);
        } else {
            txtMessage.setText("Student  Added failed!");
        }
        
        txtMessage.setVisible(true);
    }

    @FXML
    private void handleBirthDate(ActionEvent event) {
        Date birthDate = null;
        LocalDate localDate = fieldbirthDate.getValue();
        System.err.println("Local Date Birthdate field: " + localDate);
        if (localDate != null) {
            birthDate = Date.valueOf(localDate); 
            System.err.println("SQL Date Birthdate field: " + birthDate);
        }
    }


    private void handleCloseDialog(ActionEvent event) {
         Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
         stage.close();
    }

}
