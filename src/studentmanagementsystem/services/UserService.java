/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package studentmanagementsystem.services;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Date;
import java.sql.Statement;
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
    public List<User> getAllUser(){
        List<User> userList = new ArrayList<>();
        
        DatabaseConnection connection = new DatabaseConnection();
        Connection connectDB = connection.getConnection();
        
        String query =
        "SELECT * FROM user as U INNER JOIN role as R ON U.role_id = R.id";
        
        Statement statement = null;
        ResultSet result = null;
        
        try {
            statement = connectDB.createStatement();
            result = statement.executeQuery(query);
           
            
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
        } finally {
            try {
                if (result != null) result.close();
                if (statement != null) statement.close();
                if (connectDB != null) connectDB.close();
            } catch (Exception closeEx) {
                closeEx.printStackTrace();
            }
        }
        
        return userList;
    }
    
    public List<Role> getAllRole() {
        List<Role> roleList = new ArrayList<>();

           DatabaseConnection connection = new DatabaseConnection();
           Connection connectDB = connection.getConnection();

           String query =
           "SELECT * FROM  role";

           Statement statement = null;
           ResultSet result = null;

           try {
               statement = connectDB.createStatement();
               result = statement.executeQuery(query);


               while (result.next()) {
                   int roleId = result.getInt("role_id");
                   String roleName = result.getString("role_name");
                   Role role = new Role(roleId, roleName);
                   roleList.add(role);
               }

           } catch (Exception e) {
               e.printStackTrace();
           } finally {
               try {
                   if (result != null) result.close();
                   if (statement != null) statement.close();
                   if (connectDB != null) connectDB.close();
               } catch (Exception closeEx) {
                   closeEx.printStackTrace();
               }
           }

           return roleList;
    }
}
