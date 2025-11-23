/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package studentmanagementsystem.controller;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import studentmanagementsystem.model.TeacherSubjectComboBox;
import studentmanagementsystem.model.User;
import studentmanagementsystem.model.UserStudentDetails;
import studentmanagementsystem.model.UserTeacherDetails;
import studentmanagementsystem.services.TeacherSubjectService;
import studentmanagementsystem.services.UserDetailsService;
import studentmanagementsystem.services.UserService;

/**
 * FXML Controller class
 *
 * @author rainndev
 */
public class UserDetailsDialogController implements Initializable {

    
    private User user;
    private UserDetailsService userDetailsService = new UserDetailsService();
    private UserService userService = new UserService();
 
    @FXML
    private Label labelFullname;
    @FXML
    private Label labelUsername;
    @FXML
    private TableView<TeacherSubjectComboBox> tableViewTeachersSujbject;
    @FXML
    private Label labelRole;
    @FXML
    private Label labelCreatedAt;
    @FXML
    private Label labelActive;
    @FXML
    private Button btnActiveInactive;
    @FXML
    private Button btnDelete;
    @FXML
    private Label labelExtraTitle1;
    @FXML
    private Label labelExtraDesc1;
    @FXML
    private Label labelExtraTitle2;
    @FXML
    private Label labelExtraDesc2;
    @FXML
    private TableColumn<TeacherSubjectComboBox, String> columnTeacher;
    @FXML
    private TableColumn<TeacherSubjectComboBox, String> columnSubject;
    private Circle profileCircle;
    @FXML
    private Circle profileBg;
    @FXML
    private ImageView profileImage;
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        columnTeacher.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getTeacherFullName()));
        columnSubject.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getSubjectCode()));
        
    }    
    
        
    public void setUser(User user) {
      this.user = user;

      Image student = new Image("/studentmanagementsystem/assets/students.png");
      Image teacher = new Image("/studentmanagementsystem/assets/teachers.png");
      
      profileImage.setImage(user.getRole().getRoleID() == 2 ? student : teacher);
      
      if (user == null) {
          return;
      }
      
      switch (user.getRole().getRoleID()) {
            case 1:
                //1 is teacher
                initTeacher();
                break;
            case 2:
                //2 is student
                initStudent();
                break;
            default:
                // else for Admin
                labelExtraTitle1.setText("-");
                labelExtraDesc1.setText("n/a");
                labelExtraTitle2.setText("-");
                labelExtraDesc2.setText("n/a");
                break;
      }
    }
    
    
    private void initStudent() {
        UserStudentDetails userStudentDetails = userDetailsService.getBasicStudentDetails(user.getUserID());
        if (userStudentDetails == null) {
            labelFullname.setText("No details found");
            labelUsername.setText("n/a");
            labelRole.setText("n/a");
            labelCreatedAt.setText("n/a");
            labelActive.setText("n/a");
            labelExtraTitle1.setText("n/a");
            labelExtraDesc1.setText("Program");
            labelExtraTitle2.setText("n/a");
            labelExtraDesc2.setText("Year level");
            return;
        }
        
        TeacherSubjectService teacherSubjectService = new TeacherSubjectService();
        List<TeacherSubjectComboBox> teacherSubjectList = teacherSubjectService.getAllTeacherSubjectStudentID(user.getUserID());
        tableViewTeachersSujbject.setItems(FXCollections.observableArrayList(teacherSubjectList));
        
        
       
        labelFullname.setText(userStudentDetails.getUser().getFullName());
        labelUsername.setText(userStudentDetails.getUser().getUsername());
        labelRole.setText(userStudentDetails.getUser().getRole().getRoleName());
        labelCreatedAt.setText(userStudentDetails.getUser().getCreatedAt().toString());
        labelActive.setText(userStudentDetails.getUser().getActiveState(userStudentDetails.getUser().getIsActive()));
      
      
        labelExtraTitle1.setText(userStudentDetails.getProgramCode());
        labelExtraDesc1.setText("Program");
          
        labelExtraTitle2.setText(String.valueOf(userStudentDetails.getYearLevel()));
        labelExtraDesc2.setText("Year Level");
        
        btnActiveInactive.setText(
          userStudentDetails.getUser().getIsActive() == 1 ? "Disable" : "Enable"
        );
    } 
    
    private void initTeacher() {
        UserTeacherDetails userTeacherDetails = userDetailsService.getBasicTeacherDetails(user.getUserID());
          
        if (userTeacherDetails == null) {
            labelFullname.setText("No details found");
            labelUsername.setText("n/a");
            labelRole.setText("n/a");
            labelCreatedAt.setText("n/a");
            labelActive.setText("n/a");
            labelExtraTitle1.setText("n/a");
            labelExtraDesc1.setText("Department");
            labelExtraTitle2.setText("n/a");
            labelExtraDesc2.setText("Subjects");
            return;
        }
        
        
          labelFullname.setText(userTeacherDetails.getUser().getFullName());
          labelUsername.setText(userTeacherDetails.getUser().getUsername());
          labelRole.setText(userTeacherDetails.getUser().getRole().getRoleName());
          labelCreatedAt.setText(userTeacherDetails.getUser().getCreatedAt().toString());
          labelActive.setText(userTeacherDetails.getUser().getActiveState(userTeacherDetails.getUser().getIsActive()));
          
          labelExtraTitle1.setText(userTeacherDetails.getDepartment());
          labelExtraDesc1.setText("Department");
          
          labelExtraTitle2.setText(String.valueOf(userTeacherDetails.getSubjectCounts()));
          labelExtraDesc2.setText("Subjects");
          
          btnActiveInactive.setText(
            userTeacherDetails.getUser().getIsActive() == 1 ? "Disable" : "Enable"
          );
          
    }

    @FXML
    private void handleToggleEnableUser(ActionEvent event) {
         int isActive = this.user.getIsActive() == 1 ? 0 : 1;
         
         boolean isSuccess = userService.toggleEnableUser(this.user.getUserID(), isActive);
         btnActiveInactive.setText(isSuccess ? "Success" : "Failed");
    }

    @FXML
    private void handleDeleteUser(ActionEvent event) {
         boolean isSuccess = userService.deleteUser(this.user.getUserID());
         btnDelete.setText(isSuccess ? "Success" : "Failed");
    }
}
