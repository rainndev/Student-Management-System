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
        // Initialization logic
    }

    private final Map<String, String> fxmlMap = Map.of(
        "Home", "/studentmanagementsystem/view/HomeView.fxml",
        "Students", "/studentmanagementsystem/view/StudentsView.fxml",
        "Teachers", "/studentmanagementsystem/view/StudentsView.fxml"
    );

    @FXML
    public void switchPage(MouseEvent event) {
    
        Node source = (Node) event.getSource();
  
        String pageKey = (String) source.getUserData(); 
        String fxmlPath = fxmlMap.get(pageKey);

        if (fxmlPath == null) {
            System.err.println("Error: No FXML path found for key: " + pageKey);
            return;
        }

        try {
            Parent newView = viewCache.get(pageKey);

            if (newView == null) {
                FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
                newView = loader.load();
                viewCache.put(pageKey, newView);
            }

            mainContentPane.setCenter(newView);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}