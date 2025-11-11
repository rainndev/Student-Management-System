/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package studentmanagementsystem.controller;

import java.net.URL;
import java.util.List;
import java.sql.Date;
import java.util.ResourceBundle;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import studentmanagementsystem.model.Teacher;
import studentmanagementsystem.model.User;
import studentmanagementsystem.services.TeacherService;
import studentmanagementsystem.services.UserService;


/**
 * FXML Controller class
 *
 * @author rainndev
 */
public class UsersViewController implements Initializable {

    @FXML
    private TextField fieldDeleteTeacher;
    @FXML
    private TableColumn<User, Number> columnId;
    @FXML
    private TableColumn<User, String> columnFirstName;
    @FXML
    private TableColumn<User, String> columnLastName;
    @FXML
    private TableColumn<User, String> columnRole;
    @FXML
    private TableColumn<User, String> columnActive;
    @FXML
    private TableView<User> usersTableView;
    @FXML
    private TableColumn<User, Date> columnCreatedAt;
    @FXML
    private Button btnSearchUser;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        columnId.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getUserID()));
        columnFirstName.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getFirstName()));
        columnLastName.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getLastName()));
        columnRole.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getRoleFormatted(cellData.getValue().getRole())));
        columnActive.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getActiveState(cellData.getValue().getIsActive())));
        columnCreatedAt.setCellValueFactory(cellData -> new SimpleObjectProperty<Date>(cellData.getValue().getCreatedAt()));

        loadUser();
    }    
    
    private void loadUser() {
        UserService userService = new UserService();
        List<User> userList = userService.getAllUser();
        usersTableView.setItems(FXCollections.observableArrayList(userList));
    }

    @FXML
    private void handleSearchUser(ActionEvent event) {
    }

    
}
