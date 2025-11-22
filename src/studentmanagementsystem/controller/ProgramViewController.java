/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package studentmanagementsystem.controller;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;
import studentmanagementsystem.model.Program;
import studentmanagementsystem.services.ProgramService;

/**
 * FXML Controller class
 *
 * @author rainndev
 */
public class ProgramViewController implements Initializable {

    private ProgramService programService = new ProgramService();
    
    @FXML
    private TextField fieldSearchProgram;
    @FXML
    private Button btnSearchProgram;
    @FXML
    private Button btnEditProgram;
    @FXML
    private Button btnAddProgram;
    @FXML
    private TableColumn<Program, Number> columnId;
    @FXML
    private TableColumn<Program, String> columnProgramCode;
    @FXML
    private TableColumn<Program, String> columnProgramName;
    @FXML
    private TableColumn<Program, String> columnProgramDescription;
    @FXML
    private TableView<Program> programTableView;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        columnId.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getId()));
        columnProgramCode.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getProgramCode()));
        columnProgramName.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getProgramName()));
        columnProgramDescription.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getDescription()));
        
        programTableView.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
               fieldSearchProgram.setText(String.valueOf(newSelection.getId()));
            }
        });
            
        loadPrograms();
    }    

    
      
    public void loadPrograms() {
  
        List<Program> programList = programService.getAllPrograms();
        programTableView.setItems(FXCollections.observableArrayList(programList));
    }
    
    @FXML
    private void handleSearchProgram(ActionEvent event) {
        String searchQuery = fieldSearchProgram.getText();
        List<Program> programList = programService.getSearchedPrograms(searchQuery);
        programTableView.setItems(FXCollections.observableArrayList(programList)); 
    }

    @FXML
    private void openEditProgramDialog(ActionEvent event) {
         Program selectedProgram = programTableView.getSelectionModel().getSelectedItem();
        
        if (selectedProgram == null) {
            System.out.println("No Program selected!");
            return;
        }

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/studentmanagementsystem/view/EditProgramDialog.fxml"));
            Parent root = loader.load();
            EditProgramDialogController controller = loader.getController();
            controller.setProgram(selectedProgram);
           
            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setTitle("Edit Student");
            stage.setResizable(false);
            stage.setScene(new Scene(root));
            stage.showAndWait();
            loadPrograms();

        } catch (IOException e) {
            e.printStackTrace();
        }
    
    }

    @FXML
    private void openAddProgramDialog(ActionEvent event) {
        try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/studentmanagementsystem/view/AddProgramDialog.fxml"));
                Parent root = loader.load();
                
                Stage dialogStage = new Stage();
                dialogStage.setTitle("Add New Program");

                dialogStage.initModality(Modality.APPLICATION_MODAL);
                dialogStage.setResizable(false);
                Scene scene = new Scene(root);
                dialogStage.setScene(scene);

                dialogStage.showAndWait(); 
                loadPrograms();
        } catch (IOException e) {
                e.printStackTrace();
        }
    }
    
}
