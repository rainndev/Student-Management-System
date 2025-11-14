/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package studentmanagementsystem.controller;

import java.math.BigDecimal;
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
import studentmanagementsystem.model.Subject;
import studentmanagementsystem.services.SubjectService;

/**
 * FXML Controller class
 *
 * @author rainndev
 */
public class AddSubjectDialogController implements Initializable {

    @FXML
    private TextField fieldSubjectCode;
    @FXML
    private TextField fieldSubjectName;
    @FXML
    private TextField fieldSubjectUnits;
    @FXML
    private Button btnAddSubject;
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
    private void handleAddSubject(ActionEvent event) {
        SubjectService subjectService = new SubjectService(); 
        
        String subjectCode = fieldSubjectCode.getText();
        String subjectUnits = fieldSubjectUnits.getText();
        String subjectName = fieldSubjectName.getText();
        BigDecimal unitsBigDecimal = new BigDecimal(subjectUnits);
        
        Subject subject = new Subject(subjectCode, subjectName, unitsBigDecimal);
        boolean isSuccess =  subjectService.addSubject(subject);
        
        if (isSuccess) {
            txtMessage.setText("Subject Added Succcesfully!");   
            handleCloseDialog(event);
        } else {
            txtMessage.setText("Subject Added failed!");
        }
        
        txtMessage.setVisible(true);
    }
    
    private void handleCloseDialog(ActionEvent event) {
         Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
         stage.close();
    }
}
