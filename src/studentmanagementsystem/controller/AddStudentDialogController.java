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
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.stage.Stage;
import studentmanagementsystem.model.Role;
import studentmanagementsystem.services.ProgramService;
import studentmanagementsystem.services.StudentService;
import studentmanagementsystem.util.Validator;


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
    private TextField fieldAddresss;
    @FXML
    private TextField fieldContactNumber;
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
    @FXML
    private RadioButton rFemale;
    @FXML
    private ToggleGroup gender;
    @FXML
    private RadioButton rMale;
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
        rMale.setSelected(true);
    }

 
    @FXML
    private void handleAddStudent(ActionEvent event) {

        StudentService studentService = new StudentService();

        // Basic field values
        String firstName = fieldFirstName.getText().trim();
        String lastName = fieldLastName.getText().trim();
        String gender = rFemale.isSelected() ? "Female" : "Male";
        String address = fieldAddresss.getText().trim();
        String contact = fieldContactNumber.getText().trim();
        String profilePhoto = fieldProfilePath.getText().trim();
        Integer yearLevel = comboYearLevel.getValue();
        LocalDate localDate = fieldbirthDate.getValue();
        Program selectedProgram = comboProgram.getValue();
        
        
        txtMessage.setText("");
        if (!Validator.isRequired(firstName)) {
            txtMessage.setText("Error: First name is required.");
            return;
        }

        if (!Validator.isRequired(lastName)) {
            txtMessage.setText("Error: Last name is required.");
            return;
        }

        if (!Validator.isRequired(address)) {
            txtMessage.setText("Error: Address is required.");
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
        
       
        Date birthDate = Date.valueOf(localDate);
        
        // Ready to create student
        String randomUsername = java.util.UUID.randomUUID().toString();
        Role role = new Role(2);
        int isActive = 1;

        Student student = new Student(
            role,
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
        
        student.setPassword("123");
        
        boolean isSuccess = studentService.addStudent(student);

        if (isSuccess) {
            txtMessage.setText("");
            handleCloseDialog(event);
        } else {
            txtMessage.setText("Error: Failed to add student.");
        }
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
