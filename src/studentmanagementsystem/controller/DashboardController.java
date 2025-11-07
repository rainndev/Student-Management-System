package studentmanagementsystem.controller;

// REMOVE: import java.awt.event.MouseEvent;
import javafx.scene.input.MouseEvent; // Import the correct JavaFX MouseEvent
import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;
// REMOVE: import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.layout.BorderPane;
import javafx.scene.Node; // Added to safely handle the event source type

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

    @FXML // <--- CRITICAL: MUST BE ANNOTATED WITH @FXML
    public void switchPage(MouseEvent event) {
        // 1. Get the source node
        Node source = (Node) event.getSource();
        
        // 2. Safely get the page key from the Node's user data
        // You MUST set fx:id and userData="Home" on your FXML navigation element!
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