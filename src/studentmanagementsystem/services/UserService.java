/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package studentmanagementsystem.services;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Date;
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import studentmanagementsystem.databases.DatabaseConnection;
import studentmanagementsystem.model.User;
import studentmanagementsystem.model.Role;


/**
 *
 * @author rainndev
 */
public class UserService {
    private DatabaseConnection connection = new DatabaseConnection();
    
    public boolean addUser(User user) {
        String query = "INSERT INTO user(username, password, role_id, first_name, last_name) VALUES(? , ? , ? , ? , ?)";
        
        try(Connection connectDB = connection.getConnection();
            PreparedStatement preparedStatement = connectDB.prepareStatement(query)){
            preparedStatement.setString(1, user.getUsername());
            preparedStatement.setString(2, user.getPassword());
            preparedStatement.setInt(3, user.getRole().getRoleID());
            preparedStatement.setString(4, user.getFirstName());
            preparedStatement.setString(5, user.getLastName());
            
            int rowsInserted = preparedStatement.executeUpdate();
            
            return rowsInserted > 0;
        } catch (Exception e) {
            return false;
        }
    }
    
    public List<User> getAllUser(){
        List<User> userList = new ArrayList<>();
        
        String query =
        "SELECT * FROM user as U INNER JOIN role as R ON U.role_id = R.id";
        
        
        try(Connection connectDB = connection.getConnection();
            Statement statement = connectDB.createStatement();) {
            try(ResultSet result = statement.executeQuery(query);){
                while (result.next()) {
                    int roleId = result.getInt("role_id");
                    String roleName = result.getString("role_name");
                    Role role = new Role(roleId, roleName);

                    int userID = result.getInt("id");
                    String firstName = result.getString("first_name");
                    String passWord = result.getString("password");
                    String lastName = result.getString("last_name");
                    String userName = result.getString("username");
                    int isActive = result.getInt("isActive");
                    Date createdAt = result.getDate("created_at");

                    User user = new User(userName, passWord, role, firstName, lastName, isActive);
                    user.setUserId(userID);
                    user.setCreatedAt(createdAt);
                    userList.add(user);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }     
        } catch (Exception e) {
            e.printStackTrace();
        } 
        
        return userList;
    }
    
    public List<Role> getAllRole() {
        List<Role> roleList = new ArrayList<>();
        
           String query =
           "SELECT * FROM  role";

           try(Connection connectDB = connection.getConnection();
               Statement statement = connectDB.createStatement();) {
               try(ResultSet result = statement.executeQuery(query);) {
                    while (result.next()) {
                        int roleId = result.getInt("role_id");
                        String roleName = result.getString("role_name");
                        Role role = new Role(roleId, roleName);
                        roleList.add(role);
                    }  
               } catch (Exception e) {
                   e.printStackTrace();
               }
           } catch (Exception e) {
               e.printStackTrace();
           } 
           
           return roleList;
    }
    
    public List<User> getSearchedUser(String searchQuery) {
        List<User> searchedUserList = new ArrayList<>();

        String query =
            "SELECT * FROM user AS U " +
            "INNER JOIN role AS R ON U.role_id = R.id " +
            "WHERE CAST(U.id AS CHAR) LIKE ? OR U.first_name LIKE ? OR U.last_name LIKE ?";

        // Create the search parameter with wildcards
        String searchPattern = "%" + searchQuery + "%";


        try (Connection connectDB = connection.getConnection();
            PreparedStatement preparedStatement = connectDB.prepareStatement(query)
        ) {

            preparedStatement.setString(1, searchPattern);
            preparedStatement.setString(2, searchPattern);
            preparedStatement.setString(3, searchPattern);

            try (ResultSet result = preparedStatement.executeQuery()) {
                while (result.next()) {
                    int roleId = result.getInt("role_id");
                    String roleName = result.getString("role_name");
                    Role role = new Role(roleId, roleName);

                    int userID = result.getInt("id");
                    String firstName = result.getString("first_name");
                    String passWord = result.getString("password");
                    String lastName = result.getString("last_name");
                    String userName = result.getString("username");
                    int isActive = result.getInt("isActive");
                    Date createdAt = result.getDate("created_at");

                    User user = new User(userName, passWord, role, firstName, lastName, isActive);
                    user.setUserId(userID);
                    user.setCreatedAt(createdAt);
                    searchedUserList.add(user);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } 
    
    return searchedUserList;
}
    
    
    public boolean toggleEnableUser(int Id, int isActive) {
       String query = "UPDATE user SET isActive = ? WHERE id = ?";
        
        try(Connection connectDB = connection.getConnection();
            PreparedStatement preparedStatement = connectDB.prepareStatement(query)){
            preparedStatement.setInt(1, isActive);
            preparedStatement.setInt(2, Id);
            
            int rowsInserted = preparedStatement.executeUpdate();
            return rowsInserted > 0;
        } catch (Exception e) {
            return false;
        }
    }
    
    
    public boolean deleteUser(int ID) {
        String query = "DELETE FROM user WHERE id = ?";
        
        try(Connection connectDB = connection.getConnection();
            PreparedStatement preparedStatement = connectDB.prepareStatement(query)){
            preparedStatement.setInt(1, ID);
            
            int rowsInserted = preparedStatement.executeUpdate();
            return rowsInserted > 0;
        } catch (Exception e) {
            return false;
        }
    }
    
    
    public boolean addUserWithDefaultStudent(User user) {

        String insertUserQuery =
            "INSERT INTO user(username, password, role_id, first_name, last_name) VALUES(?, ?, ?, ?, ?)";

        String insertStudentQuery =
            "INSERT INTO student(user_id, program_id, year_level, gender, birth_date, address, contact_number, profile_photo, isActive) " +
            "VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection connectDB = connection.getConnection();
             PreparedStatement userStmt = connectDB.prepareStatement(insertUserQuery, PreparedStatement.RETURN_GENERATED_KEYS);
             PreparedStatement studentStmt = connectDB.prepareStatement(insertStudentQuery)) {

            // ---------- Insert USER ----------
            userStmt.setString(1, user.getUsername());
            userStmt.setString(2, user.getPassword());
            userStmt.setInt(3, user.getRole().getRoleID());
            userStmt.setString(4, user.getFirstName());
            userStmt.setString(5, user.getLastName());

            int rowsUser = userStmt.executeUpdate();

            if (rowsUser == 0) {
                return false; // user insert failed
            }

            // ---------- Get Generated user.id ----------
            ResultSet generatedKeys = userStmt.getGeneratedKeys();

            int userId;
            if (generatedKeys.next()) {
                userId = generatedKeys.getInt(1);
            } else {
                return false; // no ID generated
            }

            // ---------- Insert STUDENT with default values ----------
            studentStmt.setInt(1, userId);       
            studentStmt.setInt(2, 1);              
            studentStmt.setInt(3, 1);              
            studentStmt.setString(4, "Male");     
            studentStmt.setDate(5, new java.sql.Date(System.currentTimeMillis()));
            studentStmt.setString(6, "N/A");       
            studentStmt.setString(7, "N/A");       
            studentStmt.setString(8, "");          
            studentStmt.setBoolean(9, true);  

            int rowsStudent = studentStmt.executeUpdate();

            return rowsStudent > 0;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    
    public User getUserById(int ID) {
            User user = null;

            String query =
                "SELECT * FROM user AS U " +
                "INNER JOIN role AS R ON U.role_id = R.id " +
                "WHERE U.id = ?";

            try (Connection connectDB = connection.getConnection();
                PreparedStatement preparedStatement = connectDB.prepareStatement(query)
            ) {

                preparedStatement.setInt(1, ID);

                try (ResultSet result = preparedStatement.executeQuery()) {
                    while (result.next()) {
                        int roleId = result.getInt("role_id");
                        String roleName = result.getString("role_name");
                        Role role = new Role(roleId, roleName);

                        int userID = result.getInt("id");
                        String firstName = result.getString("first_name");
                        String passWord = result.getString("password");
                        String lastName = result.getString("last_name");
                        String userName = result.getString("username");
                        int isActive = result.getInt("isActive");
                        Date createdAt = result.getDate("created_at");

                        user = new User(userName, passWord, role, firstName, lastName, isActive);
                        user.setUserId(userID);
                        user.setCreatedAt(createdAt);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
                return  null;
            } 

            return user;
    }
}
