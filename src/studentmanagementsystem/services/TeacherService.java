package studentmanagementsystem.services;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import studentmanagementsystem.databases.DatabaseConnection;
import studentmanagementsystem.model.Teacher;
import studentmanagementsystem.model.Role;


/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author rainndev
 */
public class TeacherService {
    private DatabaseConnection connection = new DatabaseConnection();
    private Connection connectDB = connection.getConnection();
    
    
    public List<Teacher> getAllTeachers() {
        List<Teacher> teacherList = new ArrayList<>();
        String query =
        "SELECT " +
        // User Table (U)
        "U.id AS user_id, U.first_name, U.password, U.last_name, U.username, U.isActive AS user_active, U.role_id, R.role_name, " +
        // Teacher Table (T)
        "T.department, T.contact_number " +
        "FROM Teacher T " +
        "INNER JOIN User U ON T.user_id = U.id " +
        "INNER JOIN role R ON U.role_id = R.id " + 
        "WHERE U.role_id = 1";
        
        try(Statement statement = connectDB.createStatement();){
            try(ResultSet result = statement.executeQuery(query);) {
                while (result.next()) {

                    int roleId = result.getInt("role_id");
                    String roleName = result.getString("role_name");
                    Role role = new Role(roleId, roleName);


                    int userID = result.getInt("user_id");
                    String firstName = result.getString("first_name");
                    String lastName = result.getString("last_name");
                    String userName = result.getString("username");
                    int isActive = result.getInt("user_active");
                    String department = result.getString("department");
                    String contactNumber = result.getString("contact_number");
                    String password = result.getString("password");

                    Teacher teacher = new Teacher(userName, password, role, firstName, lastName, department, contactNumber, isActive);
                    teacher.setUserId(userID);
                    teacherList.add(teacher);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
           
            
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        return teacherList;
    }
    
    
    public int addTeacher(Teacher teacher) {
        String insertUserQuery = "INSERT INTO user (username, password, role_id, first_name, last_name, isActive, created_at) "
                    + "VALUES (?, ?, ?, ?, ?, ?, NOW())";
        
        String insertTeacherQuery = "INSERT INTO `teacher`(`user_id`, `department`, `contact_number`, `isActive`) VALUES (? , ? , ? , ? )";
        
        try( PreparedStatement userStmt = connectDB.prepareStatement(insertUserQuery, Statement.RETURN_GENERATED_KEYS);
             PreparedStatement teacherStmt = connectDB.prepareStatement(insertTeacherQuery);   
                ){
            
            userStmt.setString(1, teacher.getUsername());
            userStmt.setString(2, teacher.getPassword());
            userStmt.setInt(3, teacher.getRole().getRoleID());
            userStmt.setString(4, teacher.getFirstName());
            userStmt.setString(5, teacher.getLastName());
            userStmt.setInt(6, teacher.getIsActive());

            userStmt.executeUpdate();
            int userId = -1;
            
            try(ResultSet generatedKeys = userStmt.getGeneratedKeys();){
                if (generatedKeys.next()) {
                    userId = generatedKeys.getInt(1);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            
            teacherStmt.setInt(1, userId);
            teacherStmt.setString(2, teacher.getDepartment());
            teacherStmt.setString(3, teacher.getContactNumber());
            teacherStmt.setInt(4, teacher.getIsActive());

            int rowsInserted = teacherStmt.executeUpdate();
            return rowsInserted;


        } catch (Exception e) {
            e.printStackTrace();
             return 0;
        } 
    }
    
    public int editTeacher(Teacher teacher) {
        String updateUserQuery = "UPDATE user SET username = ?, password = ?, role_id = ?, first_name = ?, last_name = ?, isActive = ? WHERE id = ?";
        String updateTeacherQuery = "UPDATE teacher SET department = ?, contact_number = ?, isActive = ? WHERE user_id = ?";
  
        try( PreparedStatement userStmt = connectDB.prepareStatement(updateUserQuery);
             PreparedStatement teacherStmt = connectDB.prepareStatement(updateTeacherQuery); ){        
         
            userStmt.setString(1, teacher.getUsername());
            userStmt.setString(2, teacher.getPassword());
            userStmt.setInt(3, teacher.getRole().getRoleID());
            userStmt.setString(4, teacher.getFirstName());
            userStmt.setString(5, teacher.getLastName());
            userStmt.setInt(6, teacher.getIsActive());
            userStmt.setInt(7, teacher.getUserID());
            userStmt.executeUpdate();
            
            teacherStmt.setString(1, teacher.getDepartment());
            teacherStmt.setString(2, teacher.getContactNumber());
            teacherStmt.setInt(3, teacher.getIsActive());
            teacherStmt.setInt(4, teacher.getUserID());

            int rowsUpdated = teacherStmt.executeUpdate();
            return rowsUpdated;
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }
    
    public int deleteTeacher(int ID){
        String deleteQuery = "DELETE FROM user WHERE id = ?";
        
        try(PreparedStatement stmt = connectDB.prepareStatement(deleteQuery);){
           stmt.setInt(1, ID);
           return stmt.executeUpdate();         
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }
    
    public List<Teacher> getSearchedTeacher(String searchQuery) {
        List<Teacher> teacherList = new ArrayList<>();     

        String searchPattern = "%" + searchQuery + "%";

        String query =
            "SELECT " +
            "U.id AS user_id, U.first_name, U.password, U.last_name, U.username, " +
            "U.isActive AS user_active, U.role_id, R.role_name, " +
            "T.department, T.contact_number " +
            "FROM Teacher T " +
            "INNER JOIN User U ON T.user_id = U.id " +
            "INNER JOIN role R ON U.role_id = R.id " + 
            "WHERE (CAST(user_id AS CHAR) LIKE ? OR U.first_name LIKE ? OR U.last_name LIKE ?) " +
            "AND U.role_id = 1";

        try (PreparedStatement preparedStatement = connectDB.prepareStatement(query)) {
            preparedStatement.setString(1, searchPattern);
            preparedStatement.setString(2, searchPattern);
            preparedStatement.setString(3, searchPattern);

            try (ResultSet result = preparedStatement.executeQuery()) {
                while (result.next()) {
                    int roleId = result.getInt("role_id");
                    String roleName = result.getString("role_name");
                    Role role = new Role(roleId, roleName);

                    int userID = result.getInt("user_id");
                    String firstName = result.getString("first_name");
                    String lastName = result.getString("last_name");
                    String userName = result.getString("username");
                    int isActive = result.getInt("user_active");
                    String department = result.getString("department");
                    String contactNumber = result.getString("contact_number");
                    String password = result.getString("password");

                    Teacher teacher = new Teacher(userName, password, role, firstName, lastName, department, contactNumber, isActive);
                    teacher.setUserId(userID);
                    teacherList.add(teacher);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return teacherList;
    }
    
}
