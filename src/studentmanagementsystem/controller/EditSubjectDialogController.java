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
public class EditSubjectDialogController implements Initializable {

    private Subject subject;
    
    @FXML
    private TextField fieldSubjectCode;
    @FXML
    private TextField fieldSubjectName;
    @FXML
    private TextField fieldSubjectUnits;
    @FXML
    private Button btnEditSubject;
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
    private void handleEditSubject(ActionEvent event) {
        SubjectService subjectService = new SubjectService();
        String subjectCode = fieldSubjectCode.getText();
        String subjectUnits = fieldSubjectUnits.getText();
        String subjectName = fieldSubjectName.getText();
        BigDecimal unitsBigDecimal = new BigDecimal(subjectUnits);
        int subjectID = this.subject.getSubjectId();
        
        Subject subject = new Subject(subjectCode, subjectName, unitsBigDecimal);
        subject.setSubjectId(subjectID);
        
        int rowsInserted =  subjectService.editSubject(subject);
        
        if (rowsInserted > 0 ) {
            txtMessage.setText("Subject edit Succcesfully!");   
            handleCloseDialog(event);
        } else {
            txtMessage.setText("Subject edit failed!");
        }
        
        txtMessage.setVisible(true);
        
    }
    
    public void setSubject(Subject subject) {        
        this.subject = subject;
        fieldSubjectCode.setText(subject.getSubjectCode());
        fieldSubjectUnits.setText(String.valueOf(subject.getSubjectUnits()));
        fieldSubjectName.setText(subject.getSubjectName());
    }
    
    private void handleCloseDialog(ActionEvent event) {
         Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
         stage.close();
    }
    
}
