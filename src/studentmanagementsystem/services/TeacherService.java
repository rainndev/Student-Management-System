package studentmanagementsystem.services;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import studentmanagementsystem.databases.DatabaseConnection;
import studentmanagementsystem.model.Student;
import studentmanagementsystem.model.Teacher;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author rainndev
 */
public class TeacherService {
    public List<Teacher> getAllTeachers() {
        List<Teacher> teacherList = new ArrayList<>();
        
        DatabaseConnection connection = new DatabaseConnection();
        Connection connectDB = connection.getConnection();
      String query =
        "SELECT " +
        // User Table (U)
        "U.id AS user_id, U.first_name, U.password, U.last_name, U.username, U.isActive AS user_active, U.role_id, " +
        // Teacher Table (T)
        "T.department, T.contact_number " +
        "FROM Teacher T " +
        "INNER JOIN User U ON T.user_id = U.id " +
        "WHERE U.role_id = 1";


        Statement statement = null;
        ResultSet result = null;
        
        try {
            statement = connectDB.createStatement();
            result = statement.executeQuery(query);
           
            
            while (result.next()) {
                int userID = result.getInt("user_id");
                String firstName = result.getString("first_name");
                String lastName = result.getString("last_name");
                String userName = result.getString("username");
                int isActive = result.getInt("user_active");
                int roleId = result.getInt("role_id");
                
                String department = result.getString("department");
                String contactNumber = result.getString("contact_number");
                String password = result.getString("password");

                Teacher teacher = new Teacher(userName, password, roleId, firstName, lastName, department, contactNumber, isActive);
                teacher.setUserID(userID);
                teacherList.add(teacher);
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
        
        return teacherList;
    }
    
    
    public int addTeacher(Teacher teacher) {
        DatabaseConnection connection = new DatabaseConnection();
        Connection connectDB = connection.getConnection();

        PreparedStatement userStmt = null;
        PreparedStatement teacherStmt = null;
        ResultSet generatedKeys = null;

        try {

            String username = teacher.getUsername();
            System.out.println("DEBUG: Attempting to insert user with username: " + username);


            String insertUserQuery = "INSERT INTO user (username, password, role_id, first_name, last_name, isActive, created_at) "
                    + "VALUES (?, ?, ?, ?, ?, ?, NOW())";

            userStmt = connectDB.prepareStatement(insertUserQuery, Statement.RETURN_GENERATED_KEYS);
            userStmt.setString(1, teacher.getUsername());
            userStmt.setString(2, teacher.getPassword());
            userStmt.setInt(3, teacher.getRole());
            userStmt.setString(4, teacher.getFirstName());
            userStmt.setString(5, teacher.getLastName());
            userStmt.setInt(6, teacher.getIsActive());

            userStmt.executeUpdate();

            generatedKeys = userStmt.getGeneratedKeys();
            System.out.println("generated kets: " + generatedKeys);
            int userId = -1;
            if (generatedKeys.next()) {
                userId = generatedKeys.getInt(1);
            }

            String insertTeacherQuery = "INSERT INTO `teacher`(`user_id`, `department`, `contact_number`, `isActive`) VALUES (? , ? , ? , ? )";

            teacherStmt = connectDB.prepareStatement(insertTeacherQuery);
            teacherStmt.setInt(1, userId);
            teacherStmt.setString(2, teacher.getDepartment());
            teacherStmt.setString(3, teacher.getContactNumber());
            teacherStmt.setInt(4, teacher.getIsActive());

            int rowsInserted = teacherStmt.executeUpdate();
            return rowsInserted;


        } catch (Exception e) {
            e.printStackTrace();
             return 0;
        } finally {
            try {
                if (generatedKeys != null) generatedKeys.close();
                if (userStmt != null) userStmt.close();
                if (teacherStmt != null) teacherStmt.close();
                if (connectDB != null) connectDB.close();
            } catch (Exception closeEx) {
                closeEx.printStackTrace();
            }
        }
    }
}
