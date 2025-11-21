package studentmanagementsystem.controller;

import javafx.scene.input.MouseEvent; 
import java.io.IOException;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.layout.BorderPane;
import javafx.scene.Node; 
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.util.Duration;
import studentmanagementsystem.Main;
import studentmanagementsystem.model.SessionManager;


public class DashboardController implements Initializable {

    private Map<String, Parent> viewCache = new HashMap<>();
    
    @FXML
    private BorderPane mainContentPane;
    @FXML
    private HBox hboxAnalytics;
    @FXML
    private HBox hboxUser;
    @FXML
    private HBox hboxStudents;
    @FXML
    private HBox hboxubjects;
    @FXML
    private HBox hboxProgram;
    @FXML
    private HBox hboxGrades;
    @FXML
    private HBox hboxTeacher;
    @FXML
    private Label timeLabel;
    @FXML
    private Label nameLabel;
    @FXML
    private HBox hboxLogout;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
       switchTo("AdminAnalytics");
       startUpdatingTime();
       String fullName = SessionManager.getFullName();
       nameLabel.setText("Hi, " + fullName);
    }

    private final Map<String, String> fxmlMap = Map.of(
        "AdminAnalytics", "/studentmanagementsystem/view/AdminAnalyticsView.fxml",
        "Students", "/studentmanagementsystem/view/StudentsView.fxml",
        "Teachers", "/studentmanagementsystem/view/TeachersView.fxml",
        "Users", "/studentmanagementsystem/view/UsersView.fxml",
        "Subjects","/studentmanagementsystem/view/SubjectsView.fxml",
        "Programs","/studentmanagementsystem/view/ProgramsView.fxml",
        "Grades","/studentmanagementsystem/view/GradesView.fxml"
    );

    @FXML
    public void handleSwitchPage(MouseEvent event) {
    
        Node source = (Node) event.getSource();
  
        String pageKey = (String) source.getUserData(); 
        String fxmlPath = fxmlMap.get(pageKey);

        switchTo(pageKey);
    }
    
    
    private void switchTo(String pageKey) {
        try {
                Parent view = viewCache.get(pageKey);
                String fxmlPath = fxmlMap.get(pageKey);
                
                FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
                view = loader.load();
                mainContentPane.setCenter(view);

        } catch (IOException e) {
                e.printStackTrace();
        }
    }
    
    
    public void startUpdatingTime() {
        DateTimeFormatter formatter =
                DateTimeFormatter.ofPattern("hh:mm a, MMMM dd, yyyy");

        Runnable updateTime = () -> {
            String formatted = LocalDateTime.now().format(formatter);
            timeLabel.setText(formatted);
        };

        // Update once immediately
        updateTime.run();

        // Update every 1 minute
        Timeline timeline = new Timeline(
                new KeyFrame(Duration.ZERO, e -> updateTime.run()),
                new KeyFrame(Duration.minutes(1))
        );

        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
    }
    

    @FXML
    private void handleLogOut(MouseEvent event) {
        SessionManager.clearSession();
        
        if (SessionManager.isActive()) {
            return;
        }
        
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/studentmanagementsystem/view/Login.fxml"));

            Scene loginScene = new Scene(root);
            Main.mainStage.setScene(loginScene);
            Main.mainStage.setTitle("Log in");
            Main.mainStage.show();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}