/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package studentmanagementsystem.controller;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.beans.property.SimpleIntegerProperty;
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
import studentmanagementsystem.model.Teacher;
import studentmanagementsystem.services.TeacherService;

/**
 * FXML Controller class
 *
 * @author rainndev
 */
public class TeachersViewController implements Initializable {

    @FXML
    private TextField fieldDeleteTeacher;
    @FXML
    private Button btnDeleteTeacher;
    @FXML
    private Button btnEditTeacher;
    @FXML
    private Button btnAddTeacher;
    @FXML
    private TableView<Teacher> teacherTableView;
    @FXML
    private TableColumn<Teacher, Number> columnId;
    @FXML
    private TableColumn<Teacher, String> columnFirstName;
    @FXML
    private TableColumn<Teacher, String> columnLastName;
    @FXML
    private TableColumn<Teacher, String> columnDepartment;
    @FXML
    private TableColumn<Teacher, String> columnContact;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        columnId.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getUserId()));
        columnFirstName.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getFirstName()));
        columnLastName.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getLastName()));
        columnDepartment.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getDepartment()));
        columnContact.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getContactNumber()));
        
        teacherTableView.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
               fieldDeleteTeacher.setText(String.valueOf(newSelection.getUserId()));
            }
        });
         
         
        loadTeachers();
    }    
    
    
    public void loadTeachers() {
        TeacherService teacherService = new TeacherService();
        List<Teacher> teacherList = teacherService.getAllTeachers();
        teacherTableView.setItems(FXCollections.observableArrayList(teacherList));
    }
    
    
    @FXML
    private void openAddTeacherDialog() {  
        try {

                FXMLLoader loader = new FXMLLoader(getClass().getResource("/studentmanagementsystem/view/AddTeacherDialog.fxml"));
                Parent root = loader.load();
                
                Stage dialogStage = new Stage();
                dialogStage.setTitle("Add New Teacher");
                dialogStage.initModality(Modality.APPLICATION_MODAL);

                Scene scene = new Scene(root);
                dialogStage.setScene(scene);

                dialogStage.showAndWait(); 
                loadTeachers();



            } catch (IOException e) {
                e.printStackTrace();

            }
    }

    @FXML
    private void handleDeleteStudent(ActionEvent event) {
        TeacherService teacherService = new TeacherService();
        int id = Integer.parseInt(fieldDeleteTeacher.getText());
        
        teacherService.deleteTeacher(id);
        loadTeachers();
    }

    @FXML
    private void openEditStudentDialog(ActionEvent event) {
        Teacher selectedTeacher = teacherTableView.getSelectionModel().getSelectedItem();
        
        if (selectedTeacher == null) {
            System.out.println("No teacher selected!");
            return;
        }

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/studentmanagementsystem/view/EditTeacherDialog.fxml"));
            Parent root = loader.load();
            EditTeacherDialogController controller = loader.getController();
            controller.setTeacher(selectedTeacher);
           
            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setTitle("Edit Teacher");
            stage.setScene(new Scene(root));
            stage.showAndWait();
            loadTeachers();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void openAddStudentDialog(ActionEvent event) {
    }
    
}
