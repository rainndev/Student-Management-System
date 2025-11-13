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


/**
 * FXML Controller class
 *
 * @author rainndev
 */
public class EditProgramDialogController implements Initializable {

    private ProgramService programService = new ProgramService();
    private Program program;
    
    @FXML
    private TextField fieldProgramCode;
    @FXML
    private TextField fieldProgramName;
    @FXML
    private TextField fieldProgramDescription;
    @FXML
    private Button btnEditProgram;
    @FXML
    private Button btnDeleteProgram;
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
    private void handleEditProgram(ActionEvent event) {
        int programId = this.program.getId();
        String programCode = fieldProgramCode.getText();
        String programName = fieldProgramName.getText();
        String programDescription = fieldProgramDescription.getText();
        Program program = new Program(programId, programCode, programName, programDescription);
        boolean isEditSuccess = programService.editProgram(program);
        
        if (isEditSuccess) {
            txtMessage.setText("Program edit successfuly!");
            handleCloseDialog(event);
        } else {
            txtMessage.setText("Program failed successfuly!");
        }
   }

    @FXML
    private void handleDeleteProgram(ActionEvent event) {
        int programId = this.program.getId();
        programService.deleteProgram(programId);
        handleCloseDialog(event);
    }
    
    public void setProgram(Program program) {
        this.program = program;    
        fieldProgramCode.setText(this.program.getProgramCode());
        fieldProgramName.setText(this.program.getProgramName());
        fieldProgramDescription.setText(this.program.getDescription());
    }
    
    private void handleCloseDialog(ActionEvent event) {
         Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
         stage.close();
    }
    
}
