package studentmanagementsystem.services;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import studentmanagementsystem.databases.DatabaseConnection;
import studentmanagementsystem.model.Teacher;
import studentmanagementsystem.model.Role;
import studentmanagementsystem.model.Subject;



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
              "U.id AS user_id, U.first_name, U.password, U.last_name, U.username, " +
              "U.isActive AS user_active, U.role_id, R.role_name, " +
              // Teacher Table (T)
              "T.department, T.contact_number, " +
              // Subject Table (S)
              "S.id AS subject_id, S.subject_code, S.subject_name, S.units, " +
              // Teacher Subject Table (TS)
              "TS.id AS teacher_subject_id " + 
              "FROM Teacher T " +
              "INNER JOIN User U ON T.user_id = U.id " +
              "INNER JOIN Role R ON U.role_id = R.id " +
              // Use LEFT JOIN for subjects
              "LEFT JOIN Teacher_Subject TS ON T.user_id = TS.teacher_id " +
              "LEFT JOIN Subject S ON TS.subject_id = S.id " +
              "WHERE U.role_id = 1";

        try(Statement statement = connectDB.createStatement();){
            try(ResultSet result = statement.executeQuery(query);) {
                while (result.next()) {      
                    int roleId = result.getInt("role_id");
                    String roleName = result.getString("role_name");
                    Role role = new Role(roleId, roleName);
                    
                    int subjectID = result.getInt("subject_id");
                    String subjectName = result.getString("subject_name");
                    String subjectCode = result.getString("subject_code");
                    BigDecimal subjectUnits = result.getBigDecimal("units");
                    Subject subject = new Subject(subjectID, subjectCode, subjectName, subjectUnits);
                    
                    int teacherSubjectId = result.getInt("teacher_subject_id");
                    
                    int userID = result.getInt("user_id");
                    String firstName = result.getString("first_name");
                    String lastName = result.getString("last_name");
                    String userName = result.getString("username");
                    int isActive = result.getInt("user_active");
                    String department = result.getString("department");
                    String contactNumber = result.getString("contact_number");
                    String password = result.getString("password");

                    Teacher teacher = new Teacher(userName, password, role, firstName, lastName, department, contactNumber, isActive);
                    teacher.setSubject(subject);
                    teacher.setUserId(userID);
                    teacher.setTeacherSubjectId(teacherSubjectId);
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
              // User Table (U)
              "U.id AS user_id, U.first_name, U.password, U.last_name, U.username, " +
              "U.isActive AS user_active, U.role_id, R.role_name, " +
              // Teacher Table (T)
              "T.department, T.contact_number, " +
              // Subject Table (S)
              "S.id AS subject_id, S.subject_code, S.subject_name, S.units, " +
              // Teacher Subject Table (TS)
              "TS.id AS teacher_subject_id " + 
              "FROM Teacher T " +
              "INNER JOIN User U ON T.user_id = U.id " +
              "INNER JOIN Role R ON U.role_id = R.id " +
              // Use LEFT JOIN for subjects
              "LEFT JOIN Teacher_Subject TS ON T.user_id = TS.teacher_id " +
              "LEFT JOIN Subject S ON TS.subject_id = S.id " +
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
                    
                    int subjectID = result.getInt("subject_id");
                    String subjectName = result.getString("subject_name");
                    String subjectCode = result.getString("subject_code");
                    BigDecimal subjectUnits = result.getBigDecimal("units");
                    Subject subject = new Subject(subjectID, subjectCode, subjectName, subjectUnits);
                    
                    int teacherSubjectId = result.getInt("teacher_subject_id");
                    
                    int userID = result.getInt("user_id");
                    String firstName = result.getString("first_name");
                    String lastName = result.getString("last_name");
                    String userName = result.getString("username");
                    int isActive = result.getInt("user_active");
                    String department = result.getString("department");
                    String contactNumber = result.getString("contact_number");
                    String password = result.getString("password");

                    Teacher teacher = new Teacher(userName, password, role, firstName, lastName, department, contactNumber, isActive);
                    teacher.setSubject(subject);
                    teacher.setUserId(userID);
                    teacher.setTeacherSubjectId(teacherSubjectId);
                    teacherList.add(teacher);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return teacherList;
    }
    
}
