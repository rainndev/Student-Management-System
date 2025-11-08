/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package studentmanagementsystem.controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import studentmanagementsystem.model.Student;
import java.sql.Date; 
import java.util.List;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.stage.Modality;
import javafx.stage.Stage;
import studentmanagementsystem.services.StudentService;

/**
 * FXML Controller class
 *
 * @author rainndev
 */
public class StudentsViewController implements Initializable {

    @FXML
    private TableColumn<Student, Number> columnId;
    @FXML
    private TableColumn<Student, String> columnFirstName;
    @FXML
    private TableColumn<Student, String> columnLastName;
    @FXML
    private TableColumn<Student, String> columnGender;
    @FXML
    private TableColumn<Student, Date> columnBirthDate;
    @FXML
    private TableColumn<Student, String> columnAddress;
    @FXML
    private TableColumn<Student, String> columnContact;
    @FXML
    private TableColumn<Student, String> columnProgram;
    @FXML
    private TableColumn<Student, Number> columnYearLevel;
    @FXML
    private TableView<Student> studentTableView;
    @FXML
    private Button btnAddStudent;

    /**
     * Initializes the controller class.
     */
    
    @FXML
    private void openAddStudentDialog() {
            try {

                FXMLLoader loader = new FXMLLoader(getClass().getResource("/studentmanagementsystem/view/AddStudentDialog.fxml"));
                Parent root = loader.load();
                
                
              
                AddStudentDialogController dialogController = loader.getController();
                
                dialogController.setStudentsViewController(this);

                Stage dialogStage = new Stage();
                dialogStage.setTitle("Add New Student");

                dialogStage.initModality(Modality.APPLICATION_MODAL);

                Scene scene = new Scene(root);
                dialogStage.setScene(scene);

                dialogStage.showAndWait(); 



            } catch (IOException e) {
                e.printStackTrace();

            }
    }
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        columnId.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getUserId()));
        columnFirstName.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getFirstName()));
        columnLastName.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getLastName()));
        columnGender.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getGender()));
        columnBirthDate.setCellValueFactory(cellData -> new SimpleObjectProperty<Date>(cellData.getValue().getBirthDate()));
        columnAddress.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getAddress()));
        columnContact.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getContactNumber()));
        columnProgram.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getProgram()));
        columnYearLevel.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getYearLevel()));
        loadStudents();
    }    
    
    
    public void loadStudents() {
        StudentService studentService = new StudentService();
        List<Student> student = studentService.getAllStudent();
        studentTableView.setItems(FXCollections.observableArrayList(student));
    }

    private void handleRefreshTable(ActionEvent event) {
        loadStudents();
    }
    
}
