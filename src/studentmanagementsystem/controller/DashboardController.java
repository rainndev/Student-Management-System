package studentmanagementsystem.controller;

import javafx.scene.input.MouseEvent; 
import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.layout.BorderPane;
import javafx.scene.Node; 

public class DashboardController implements Initializable {

    private Map<String, Parent> viewCache = new HashMap<>();
    
    @FXML
    private BorderPane mainContentPane;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
       switchTo("AdminAnalytics");
    }

    private final Map<String, String> fxmlMap = Map.of(
        "AdminAnalytics", "/studentmanagementsystem/view/AdminAnalyticsView.fxml",
        "Students", "/studentmanagementsystem/view/StudentsView.fxml",
        "Teachers", "/studentmanagementsystem/view/TeachersView.fxml",
        "Users", "/studentmanagementsystem/view/UsersView.fxml",
        "Subjects","/studentmanagementsystem/view/SubjectsView.fxml",
        "Programs","/studentmanagementsystem/view/ProgramsView.fxml"
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
}