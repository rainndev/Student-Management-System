/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package studentmanagementsystem.controller;

import java.net.URL;
import java.util.Map;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.StackedBarChart;
import javafx.scene.chart.XYChart;
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
    @FXML
    private BarChart<String, Number> barChart;
    @FXML
    private CategoryAxis Department;
    @FXML
    private PieChart pieChart;
    @FXML
    private StackedBarChart<String, Number> stackedRemarksChart;

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
        
        
        initializeBarChart();
        initializePieChart();
        initializeStackedBarChart();
   }
    
    
    private void initializeBarChart(){
        Map<String, Number> studentCountPerProgram = analyticsService.getStudentCountPerProgram();
        barChart.setAnimated(true);
        
        //bar chart
        XYChart.Series<String, Number> series = new XYChart.Series<>();
        for (Map.Entry<String, Number> entry : studentCountPerProgram.entrySet()) {
            String program = entry.getKey();
            Number count = entry.getValue();
            series.getData().add(new XYChart.Data<>(program, count));
        }
        
        series.setName("Total Students per Program"); 
        barChart.getData().add(series);
    }
    
    private void initializePieChart() {
        Map<String, Number> allRemarksCountSubject = analyticsService.getAllRemarksCountSubject();
         //Pie chart
        for (Map.Entry<String, Number> entry : allRemarksCountSubject.entrySet()) {
            pieChart.getData().add(
                new PieChart.Data(entry.getKey(), entry.getValue().doubleValue())
            );
        }

        pieChart.setLegendVisible(true);   // show legend
        pieChart.setLabelsVisible(true);   // show percentage/value inside slices
        pieChart.setStartAngle(90);        // rotate start if you want
    }
    
    
    private void initializeStackedBarChart() {
        Map<String, int[]> subjectGradeBreakdown = analyticsService.getSubjectGradeBreakdown();
        // Series for each remark type
        XYChart.Series<String, Number> passed = new XYChart.Series<>();
        passed.setName("Passed");

        XYChart.Series<String, Number> failed = new XYChart.Series<>();
        failed.setName("Failed");

        XYChart.Series<String, Number> incomplete = new XYChart.Series<>();
        incomplete.setName("Incomplete");

        XYChart.Series<String, Number> dropped = new XYChart.Series<>();
        dropped.setName("Dropped");

        final int PASSED = 0;
        final int FAILED = 1;
        final int INCOMPLETE = 2;
        final int DROPPED = 3;


        for (Map.Entry<String, int[]> entry : subjectGradeBreakdown.entrySet()) {
                String subject = entry.getKey(); 
                int[] counts = entry.getValue();

                passed.getData().add(new XYChart.Data<>(subject, counts[PASSED]));
                failed.getData().add(new XYChart.Data<>(subject, counts[FAILED]));
                incomplete.getData().add(new XYChart.Data<>(subject, counts[INCOMPLETE]));
                dropped.getData().add(new XYChart.Data<>(subject, counts[DROPPED]));
        }


        stackedRemarksChart.getData().addAll(passed, failed, incomplete, dropped);
    }
}
