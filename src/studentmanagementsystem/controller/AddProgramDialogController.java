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
import studentmanagementsystem.services.ProgramService;
import studentmanagementsystem.model.Program;
import studentmanagementsystem.util.Validator;

/**
 * FXML Controller class
 *
 * @author rainndev
 */
public class AddProgramDialogController implements Initializable {

    @FXML
    private TextField fieldProgramCode;
    @FXML
    private TextField fieldProgramName;
    private TextField fieldProgarmDescription;
    @FXML
    private Button btnAddProgram;
    @FXML
    private Label txtMessage;
    @FXML
    private TextField fieldProgramDescription;

    /**
     * Initializes the controller class.
     */
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void handleAddProgram(ActionEvent event) {
        ProgramService programService = new ProgramService();
        
        String programCode = fieldProgramCode.getText();
        String programName = fieldProgramName.getText();
        String description = fieldProgramDescription.getText();
        
        txtMessage.setText("");
        if (!Validator.isRequired(programCode)) {
            txtMessage.setText("Error: Program code is required");   
            return;
        }
        
        if (!Validator.isRequired(programName)) {
            txtMessage.setText("Error: Program name is required");   
            return;
        }
        
        Program program = new Program(programCode, programName, description);
        boolean isAddSuccess =  programService.addProgram(program);
        
        if (isAddSuccess) {
            txtMessage.setText("Program Added Succcesfully!");   
            handleCloseDialog(event);
        } else {
            txtMessage.setText("Program Added failed!");
        }
    }
    
    private void handleCloseDialog(ActionEvent event) {
         Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
         stage.close();
    }
    
}
