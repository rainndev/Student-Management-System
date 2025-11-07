/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package studentmanagementsystem.controller;

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
import javafx.scene.control.TableView;
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

    /**
     * Initializes the controller class.
     */
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

        
        StudentService studentService = new StudentService();
        List<Student> student = studentService.getAllStudent();
        studentTableView.setItems(FXCollections.observableArrayList(student));
        
    }    
    
}
