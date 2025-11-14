/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package studentmanagementsystem.controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.text.Text;
import studentmanagementsystem.services.AnalyticsService;

/**
 * FXML Controller class
 *
 * @author rainndev
 */
public class AdminAnalyticsView implements Initializable {

    private AnalyticsService analyticsService= new AnalyticsService();
    
    @FXML
    private Text txtTotalUsers;
    @FXML
    private Text txtTotalStudents;
    @FXML
    private Text txtTotalTeachers;
    @FXML
    private Text txtTotalPrograms;
    @FXML
    private Text txtTotalSubjects;
    @FXML
    private Text txtTotalInactiveUsers;
    @FXML
    private Text txtTotalActiveUsers;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        int totalUsers = analyticsService.getTotalUsers();
        int totalStudents = analyticsService.getTotalStudents();
        int totalTeachers = analyticsService.getTotalTeachers();
        int totalPrograms = analyticsService.getTotalPrograms();
        int totalSubject = analyticsService.getTotalSubjects();
        int[] totalActiveInactiveUsers = analyticsService.getActiveInactiveCount();

        
        txtTotalUsers.setText(String.valueOf(totalUsers));
        txtTotalStudents.setText(String.valueOf(totalStudents));
        txtTotalTeachers.setText(String.valueOf(totalTeachers));
        txtTotalPrograms.setText(String.valueOf(totalPrograms));
        txtTotalSubjects.setText(String.valueOf(totalSubject));
        txtTotalActiveUsers.setText(String.valueOf(totalActiveInactiveUsers[0]));
        txtTotalInactiveUsers.setText(String.valueOf(totalActiveInactiveUsers[1]));
    }    
    
}
