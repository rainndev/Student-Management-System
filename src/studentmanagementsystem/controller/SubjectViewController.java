/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package studentmanagementsystem.controller;

import java.io.IOException;
import java.math.BigDecimal;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;
import studentmanagementsystem.model.Student;
import studentmanagementsystem.model.Subject;
import studentmanagementsystem.services.StudentService;
import studentmanagementsystem.services.SubjectService;

/**
 * FXML Controller class
 *
 * @author rainndev
 */
public class SubjectViewController implements Initializable {

    private SubjectService subjectService = new SubjectService();
    
    private TextField fieldDeleteSubject;
    @FXML
    private Button btnEditSubject;
    @FXML
    private Button btnAddSubject;
    @FXML
    private TableColumn<Subject, Number> columnId;
    @FXML
    private TableColumn<Subject, String> columnSubjectCode;
    @FXML
    private TableColumn<Subject, String> columnSubjectName;
    @FXML
    private TableColumn<Subject, BigDecimal> columnSubjectUnits;
    @FXML
    private TableView<Subject> subjectTableView;
    @FXML
    private Button btnSearchSubject;
    @FXML
    private TextField fieldSearchSubject;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {    
        columnId.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getSubjectId()));
        columnSubjectCode.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getSubjectCode()));
        columnSubjectName.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getSubjectName()));
        columnSubjectUnits.setCellValueFactory(cellData -> new SimpleObjectProperty<BigDecimal>(cellData.getValue().getSubjectUnits()));
        
        subjectTableView.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
               fieldSearchSubject.setText(String.valueOf(newSelection.getSubjectId()));
            }
        });
           
        loadSubjects();
    }    
    
    public void loadSubjects() {
        List<Subject> subjectList = subjectService.getAllSubject();
        subjectTableView.setItems(FXCollections.observableArrayList(subjectList));
    }


    private void handleDeleteStudent(ActionEvent event) {
        Subject selectedSubject = subjectTableView.getSelectionModel().getSelectedItem();
        int subjectID = selectedSubject.getSubjectId();
        subjectService.deleteSubject(subjectID);
        loadSubjects();
    }

    @FXML
    private void openAddTeacherDialog(ActionEvent event) {
                 try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/studentmanagementsystem/view/AddSubjectDialog.fxml"));
                Parent root = loader.load();
                
                Stage dialogStage = new Stage();
                dialogStage.setTitle("Add New Subject");

                dialogStage.initModality(Modality.APPLICATION_MODAL);

                Scene scene = new Scene(root);
                dialogStage.setScene(scene);

                dialogStage.showAndWait(); 
                loadSubjects();
         } catch (IOException e) {
                e.printStackTrace();
         }
    }

    @FXML
    private void handleSearchSuject(ActionEvent event) {
        String searchQuery = fieldSearchSubject.getText();
        List<Subject> subjectList = subjectService.getSearchedSubject(searchQuery);
        subjectTableView.setItems(FXCollections.observableArrayList(subjectList)); 
    }

    @FXML
    private void openEditSubjectDialog(ActionEvent event) {
        Subject selectedSubject = subjectTableView.getSelectionModel().getSelectedItem();
        
        if (selectedSubject == null) {
            System.out.println("No student selected!");
            return;
        }

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/studentmanagementsystem/view/EditSubjectDialog.fxml"));
            Parent root = loader.load();
            EditSubjectDialogController controller = loader.getController();
            controller.setSubject(selectedSubject);
           
            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setTitle("Edit Student");
            stage.setScene(new Scene(root));
            stage.showAndWait();
            loadSubjects();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }   
}
