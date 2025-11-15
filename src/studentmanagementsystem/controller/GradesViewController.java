/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package studentmanagementsystem.controller;

import java.io.IOException;
import java.net.URL;
import java.sql.Date;
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
import studentmanagementsystem.services.StudentService;

/**
 * FXML Controller class
 *
 * @author rainndev
 */
public class GradesViewController implements Initializable {

    @FXML
    private TextField fieldSearchStudent;
    @FXML
    private Button btnSearchStudent;
    @FXML
    private Button btnAddStudent1;
    @FXML
    private TableView<Student> studentTableView;
    @FXML
    private TableColumn<Student, Number> columnId;
    @FXML
    private TableColumn<Student, String> columnFirstName;
    @FXML
    private TableColumn<Student, String> columnLastName;
    @FXML
    private TableColumn<Student, String> columnGender;
    @FXML
    private TableColumn<Student, String> columnProgram;
    @FXML
    private TableColumn<Student, Number> columnYearLevel;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        columnId.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getUserID()));
        columnFirstName.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getFirstName()));
        columnLastName.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getLastName()));
        columnGender.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getGender()));
        columnProgram.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getProgram().getProgramName()));
        columnYearLevel.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getYearLevel()));
        loadStudents();
        
        
        studentTableView.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
               fieldSearchStudent.setText(String.valueOf(newSelection.getUserID()));
            }
        });
    }    

    @FXML
    private void handleSearchStudent(ActionEvent event) {
        StudentService studentService = new StudentService();
        String searchQuery = fieldSearchStudent.getText();
        
        List<Student> studentList = studentService.getSearchedUser(searchQuery);
        studentTableView.setItems(FXCollections.observableArrayList(studentList));
    }


    
    public void loadStudents() {
        StudentService studentService = new StudentService();
        List<Student> student = studentService.getAllStudent();
        studentTableView.setItems(FXCollections.observableArrayList(student));
    }
    
    
    @FXML
    private void openEditGradeDialog(ActionEvent event) {
        Student selectedStudent = studentTableView.getSelectionModel().getSelectedItem();
        
        if (selectedStudent == null) {
            System.out.println("No student selected!");
            return;
        }

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/studentmanagementsystem/view/EditStudentGradeDialog.fxml"));
            Parent root = loader.load();
            EditStudentGradeDialogController controller = loader.getController();
            System.out.println("Student id: " + selectedStudent.getUserID());
            controller.setStudent(selectedStudent);
           
            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setTitle("Edit Student Grade");
            stage.setScene(new Scene(root));
            stage.showAndWait();
            loadStudents();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
