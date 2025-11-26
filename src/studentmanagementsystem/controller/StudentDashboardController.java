/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package studentmanagementsystem.controller;

import java.math.BigDecimal;
import java.net.URL;
import java.time.LocalDate;
import java.util.List;
import java.util.ResourceBundle;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.AnchorPane;
import studentmanagementsystem.model.SessionManager;
import studentmanagementsystem.model.StudentGradeData;
import studentmanagementsystem.model.Student;
import studentmanagementsystem.services.GradeService;
import studentmanagementsystem.services.StudentService;

/**
 * FXML Controller class
 *
 * @author rainndev
 */
public class StudentDashboardController implements Initializable {

    private GradeService gradeService = new GradeService();
    private StudentService studentService = new StudentService();

    @FXML
    private TabPane tabPaneView;
    @FXML
    private AnchorPane tab1stSemester;
    @FXML
    private TableView<StudentGradeData> tableView1stSemester;
    @FXML
    private TableColumn<StudentGradeData, BigDecimal> column1stGrade;
    @FXML
    private TableColumn<StudentGradeData, String> column1stSubjectCode;
    @FXML
    private TableColumn<StudentGradeData, String> column1stSubjectName;
    @FXML
    private TableColumn<StudentGradeData, BigDecimal> column1stUnits;
    @FXML
    private TableColumn<StudentGradeData, String> column1stTeacher;
    @FXML
    private TableColumn<StudentGradeData, String> column1stRemarks;
    @FXML
    private TableView<StudentGradeData> tableView2ndSemester;
    @FXML
    private TableColumn<StudentGradeData, BigDecimal> column2ndGrade;
    @FXML
    private TableColumn<StudentGradeData, String> column2ndSubjectCode;
    @FXML
    private TableColumn<StudentGradeData, String> column2ndSubjectName;
    @FXML
    private TableColumn<StudentGradeData, BigDecimal> column2ndUnits;
    @FXML
    private TableColumn<StudentGradeData, String> column2ndTeacher;
    @FXML
    private TableColumn<StudentGradeData, String> column2ndRemarks;
    @FXML
    private ComboBox<String> comboTableSchoolYear;
    @FXML
    private Tab tab2ndSemester;
    @FXML
    private Label lblFullName;
    @FXML
    private Label lblProgram;
    @FXML
    private Label lblYearLevel;
    @FXML
    private Label lblGender;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        column1stGrade.setCellValueFactory(cellData -> new SimpleObjectProperty<BigDecimal>(cellData.getValue().getGrade()));
        column1stSubjectCode.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getSubjectCode()));
        column1stSubjectName.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getSubjectName()));
        column1stUnits.setCellValueFactory(cellData -> new SimpleObjectProperty<BigDecimal>(cellData.getValue().getSubjectUnits()));
        column1stTeacher.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getTeacherFullName()));
        column1stRemarks.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getRemarks()));
        
        column2ndGrade.setCellValueFactory(cellData -> new SimpleObjectProperty<BigDecimal>(cellData.getValue().getGrade()));
        column2ndSubjectCode.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getSubjectCode()));
        column2ndSubjectName.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getSubjectName()));
        column2ndUnits.setCellValueFactory(cellData -> new SimpleObjectProperty<BigDecimal>(cellData.getValue().getSubjectUnits()));
        column2ndTeacher.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getTeacherFullName()));
        column2ndRemarks.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getRemarks()));

        Student student = studentService.getStudentById(SessionManager.getUserId());
        String fullName = student.getFullName();
        String programName = student.getProgram().getProgramName();
        int yearLevel = student.getYearLevel();
        String gender = student.getGender();
        
        
        lblFullName.setText(
            (fullName == null || fullName.isEmpty()) ? "Unknown Student" : fullName
        );

        lblProgram.setText(
            (programName == null || programName.isEmpty()) ? "Unknown Program" : programName
        );

        lblYearLevel.setText(
            yearLevel == 0 ? "Not specified" : String.valueOf(yearLevel)
        );

        lblGender.setText(
            (gender == null || gender.isEmpty()) ? "n/a" : gender
        );
        
        //school year
        int startYear = 2020;  
        int endYear = 2099;     //max

        for (int y = startYear; y < endYear; y++) {
            String schoolYear = y + "-" + (y + 1);
            comboTableSchoolYear.getItems().add(schoolYear);
        }
        
        
        String currentSchoolYear = getCurrentSchoolYear();
        comboTableSchoolYear.setValue(currentSchoolYear);
        
        loadGrades(currentSchoolYear);
        
        
        comboTableSchoolYear.valueProperty().addListener((obs, oldValue, newValue) -> {
            loadGrades(newValue);
        });
        
        
        System.out.println("Student ID:" + SessionManager.getUserId());
        System.out.println("Current Session:" + SessionManager.getFullName());
    }    
    
    
    public void loadGrades(String year) {
        int studentId = SessionManager.getUserId();
        List<StudentGradeData> studentGradeData1st = gradeService.getGradesStudentById(studentId, 1, year);
        tableView1stSemester.setItems(FXCollections.observableArrayList(studentGradeData1st));
        
        List<StudentGradeData> studentGradeData2nd = gradeService.getGradesStudentById(studentId, 2, year);
        tableView2ndSemester.setItems(FXCollections.observableArrayList(studentGradeData2nd));
    }
    
    private String getCurrentSchoolYear() {
        LocalDate today = LocalDate.now();
        int year = today.getYear();
        return year + "-" + (year + 1);
    }
}
