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
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import studentmanagementsystem.model.Student;
import studentmanagementsystem.model.Grade;
import studentmanagementsystem.model.StudentGradeData;
import studentmanagementsystem.model.TeacherSubjectComboBox;
import studentmanagementsystem.services.GradeService;
import studentmanagementsystem.services.TeacherSubjectService;

/**
 * FXML Controller class
 *
 * @author rainndev
 */
public class EditStudentGradeDialogController implements Initializable {

    private Student student;
    private GradeService gradeService = new GradeService();
    
    @FXML
    private Label txtMessage;
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
    private ComboBox<String> comboRemarks;
    @FXML
    private ComboBox<String> comboTableSchoolYear;
    @FXML
    private Button btnEditGrade;
    @FXML
    private Button btnDeleteGrade;
    @FXML
    private TabPane tabPaneView;
    @FXML
    private AnchorPane tab1stSemester;
    @FXML
    private Tab tab2ndSemester;

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
        
        
        
        tableView1stSemester.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
              setDataField(newSelection);
            }
        });
        
           
        tableView2ndSemester.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
              setDataField(newSelection);
            }
        });
        
        
        
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
            comboTableSchoolYear.getItems().add(schoolYear);
        }
        
        //remarks
        comboRemarks.getItems().addAll(
            "PASSED",
            "FAILED",
            "INCOMPLETE",
            "DROPPED"
        );
        
        String currentSchoolYear = getCurrentSchoolYear();
        comboTableSchoolYear.setValue(currentSchoolYear);
        
        comboTableSchoolYear.valueProperty().addListener((obs, oldValue, newValue) -> {
            loadGrades(newValue);
        });
    }    

    
    public void loadGrades(String year) {
        int studentId = this.student.getUserID();
        List<StudentGradeData> studentGradeData1st = gradeService.getGradesStudentById(studentId, 1, year);
        tableView1stSemester.setItems(FXCollections.observableArrayList(studentGradeData1st));
        
        List<StudentGradeData> studentGradeData2nd = gradeService.getGradesStudentById(studentId, 2, year);
        tableView2ndSemester.setItems(FXCollections.observableArrayList(studentGradeData2nd));
    }
       
    public void setStudent(Student student) {    
        this.student = student;
        String currentSchoolYear = getCurrentSchoolYear();
        loadGrades(comboTableSchoolYear.getValue());
    }
    
    private String getCurrentSchoolYear() {
        LocalDate today = LocalDate.now();
        int year = today.getYear();
        return year + "-" + (year + 1);
    }

    @FXML
    private void handleAddGrades(ActionEvent event) {
        int studentId = this.student.getUserID();
        BigDecimal grade = new BigDecimal(fieldGrade.getText());
        int teacherSubjectId = comboTeacherSubject.getValue().getTeacherSubjectId();
        int semester = comboSemester.getValue();
        String schoolYear = comboTableSchoolYear.getValue();
        String remarks = comboRemarks.getValue();
        
        Grade gradeData = new Grade(studentId, teacherSubjectId, grade, remarks, semester, schoolYear);
        boolean isSuccessAdd = gradeService.addGrade(gradeData);
        
        if (isSuccessAdd) {
            fieldGrade.setText("");
            comboTeacherSubject.setValue(null);
            comboSemester.setValue(null);
            comboRemarks.setValue(null);
            txtMessage.setText("Grade added successfully");
            loadGrades(schoolYear);
        } else {
            txtMessage.setText("Grade added failed");
        }
        
        txtMessage.setVisible(true);
    }
    
    private void setDataField(StudentGradeData studentGradeData) {        
        int teacherSubjectId = studentGradeData.getTeacherSubjectId();
        String teacherFirstName = studentGradeData.getTeacherFirstName();
        String teacherLastName = studentGradeData.getTeacherLastName();
        String subjectCode = studentGradeData.getSubjectCode();
        String subjectName = studentGradeData.getSubjectName();
        
        
        fieldGrade.setText(String.valueOf(studentGradeData.getGrade()));
        
        TeacherSubjectComboBox teacherSubjectCombo = new TeacherSubjectComboBox(teacherSubjectId, teacherFirstName, teacherLastName, subjectCode, subjectName);
        comboTeacherSubject.setValue(teacherSubjectCombo);
        comboRemarks.setValue(studentGradeData.getRemarks());
        comboSemester.setValue(studentGradeData.getSemester());
    }

    @FXML
    private void handleEditGrades(ActionEvent event) {
        Tab activeTab = tabPaneView.getSelectionModel().getSelectedItem();
        StudentGradeData selected;

        // determine which table is active
        if (activeTab.getText().equals("1st Semester")) {
            selected = tableView1stSemester.getSelectionModel().getSelectedItem();
        } else {
            selected = tableView2ndSemester.getSelectionModel().getSelectedItem();
        }


        if (selected == null) {
            txtMessage.setText("Please select a grade to edit.");
            txtMessage.setVisible(true);
            return;
        }

        Grade grade = new Grade(
            selected.getGradeId(),
            selected.getStudentId(),
            comboTeacherSubject.getValue().getTeacherSubjectId(),
            new BigDecimal(fieldGrade.getText()),
            comboRemarks.getValue(),
            comboSemester.getValue(),
            comboTableSchoolYear.getValue()
        );

        boolean success = gradeService.editGrade(grade);

        if (!success) {
            txtMessage.setText("Failed to update grade.");
        } else {
            txtMessage.setText("Grade updated successfully!");
            loadGrades(comboTableSchoolYear.getValue());
        }

        txtMessage.setVisible(true);
    }

    
    private boolean processGradeEdit(StudentGradeData data, String label) {
        if (data == null) return true;

        Grade grade = new Grade(
                    data.getGradeId(),
                    data.getStudentId(),
                    comboTeacherSubject.getValue().getTeacherSubjectId(),
                    new BigDecimal(fieldGrade.getText()),
                    comboRemarks.getValue(),
                    comboSemester.getValue(),
                    comboTableSchoolYear.getValue()
        );

        boolean success = gradeService.editGrade(grade);

        if (!success) {
            txtMessage.setText("Failed to update " + label + " grade.");
            txtMessage.setVisible(true);
            return false;
        }

        return true;
    }

    @FXML
    private void handleDeleteGrade(ActionEvent event) {
        Tab activeTab = tabPaneView.getSelectionModel().getSelectedItem();
        StudentGradeData selected;
        
           // determine which table is active
        if (activeTab.getText().equals("1st Semester")) {
            selected = tableView1stSemester.getSelectionModel().getSelectedItem();
        } else {
            selected = tableView2ndSemester.getSelectionModel().getSelectedItem();
        }


        if (selected == null) {
            txtMessage.setText("Please select a grade to edit.");
            txtMessage.setVisible(true);
            return;
        }
        boolean success = gradeService.deleteGrade(selected.getGradeId());
        if (!success) {
            txtMessage.setText("Failed to delete grade.");
        } else {
            txtMessage.setText("Grade deleted successfully!");
            loadGrades(comboTableSchoolYear.getValue()); // refresh
        }

        txtMessage.setVisible(true);
        
    }
}
