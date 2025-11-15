/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package studentmanagementsystem.controller;

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
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import studentmanagementsystem.model.Student;
import studentmanagementsystem.model.StudentGradeData;
import studentmanagementsystem.model.TeacherSubjectComboBox;
import studentmanagementsystem.services.GradeService;
import studentmanagementsystem.services.StudentService;
import studentmanagementsystem.services.TeacherSubjectService;

/**
 * FXML Controller class
 *
 * @author rainndev
 */
public class EditStudentGradeDialogController implements Initializable {

    private Student student;
    private GradeService gradeService;
    
    @FXML
    private Label txtMessage;
    @FXML
    private Text txtSchoolYear;
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
    private TextField fieldGrade;
    @FXML
    private ComboBox<TeacherSubjectComboBox> comboTeacherSubject;
    @FXML
    private ComboBox<Integer> comboSemester;
    @FXML
    private Button btnAddGrades;
    @FXML
    private ComboBox<String> comboSchoolYear;
    @FXML
    private ComboBox<String> comboRemarks;

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
        
        
        //semester
        Integer[] semester = {1, 2, 3};
        comboSemester.getItems().addAll(semester);
        
        
        //all  teacher and their assigned subject
        TeacherSubjectService teacherSubjectService =  new TeacherSubjectService();
        List<TeacherSubjectComboBox> teacherSubjectComboList = teacherSubjectService.getAllTeacherSubjectComboBox();
        comboTeacherSubject.getItems().addAll(teacherSubjectComboList);
        
        
        //school year
        int startYear = 2020;  
        int endYear = 2099;     //max

        for (int y = startYear; y < endYear; y++) {
            String schoolYear = y + "-" + (y + 1);
            comboSchoolYear.getItems().add(schoolYear);
        }
        
        //remarks
        comboRemarks.getItems().addAll(
            "PASSED",
            "FAILED",
            "INCOMPLETE",
            "DROPPED"
        );
    }    

    
    public void loadGrades() {
        int studentId = this.student.getUserID();
        GradeService gradeService = new GradeService();
        List<StudentGradeData> studentGradeData = gradeService.getGradesStudentById(studentId, 1, "2024-2025");
        tableView1stSemester.setItems(FXCollections.observableArrayList(studentGradeData));
    }
       
    public void setStudent(Student student) {
        this.student = student;
        loadGrades();
    }

    @FXML
    private void handleAddGrades(ActionEvent event) {
    }
}
