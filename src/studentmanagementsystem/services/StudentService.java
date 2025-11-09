package studentmanagementsystem.services;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.sql.Date; 
import java.util.ArrayList;
import java.util.List;
import studentmanagementsystem.databases.DatabaseConnection;
import studentmanagementsystem.model.Student;
import studentmanagementsystem.model.User;
import studentmanagementsystem.model.Program;

/**
 * Service class for fetching and managing Student data, joining User and Program information.
 *
 * @author rainndev
 */
public class StudentService { 	
    public List<Student> getAllStudent() {
        List<Student> studentList = new ArrayList<>();
        
        DatabaseConnection connection = new DatabaseConnection();
        Connection connectDB = connection.getConnection();
        String query = 
            "SELECT " +
            // User Table (U)
            "U.id AS user_id, U.first_name, U.last_name, U.username, U.isActive AS user_active, U.role_id, " +
            // Student Table (S)
            "S.program_id, S.year_level, S.gender, S.birth_date, S.address, S.contact_number, S.profile_photo, S.isActive AS student_active, " +
            // Program Table (P)
            "P.program_code, P.program_name, P.description " +
            "FROM `Student` S " +
            "INNER JOIN `User` U ON S.user_id = U.id " +
            "INNER JOIN `Program` P ON S.program_id = P.id " +
            "WHERE U.role_id = 2"; //retrieved only for students

        Statement statement = null;
        ResultSet result = null;
        
        try {
            statement = connectDB.createStatement();
            result = statement.executeQuery(query);
            System.out.println(result);
            
            while (result.next()) {
                
           
                int userId = result.getInt("user_id");
                int roleId = result.getInt("role_id");
                String firstName = result.getString("first_name");
                String lastName = result.getString("last_name");
                String username = result.getString("username");
                int isActive = result.getInt("student_active"); 
                String profilePhoto = result.getString("profile_photo");
               
                
                int yearLevel = result.getInt("year_level");
                String gender = result.getString("gender");
                Date birthDate = result.getDate("birth_date");
                String address = result.getString("address");
                String contactNumber = result.getString("contact_number");      
                String description = result.getString("description");
                
                int programID = result.getInt("program_id");      
                String programName = result.getString("program_name");
                String programCode = result.getString("program_code");
                Program program = new Program(programID, programCode, programName, description);
               
                Student student = new Student(
                        roleId,
                        program, 
                        yearLevel,
                        gender, 
                        birthDate, 
                        address, 
                        contactNumber,        
                        isActive, 
                        firstName, 
                        lastName, 
                        username,        
                        profilePhoto
                );
                student.setUserId(userId);
                
                studentList.add(student);
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
        
        return studentList;
    }
    
   public int addStudent(Student student) {
    DatabaseConnection connection = new DatabaseConnection();
    Connection connectDB = connection.getConnection();

    PreparedStatement userStmt = null;
    PreparedStatement studentStmt = null;
    ResultSet generatedKeys = null;

    try {
        
        String username = student.getUsername();
        System.out.println("DEBUG: Attempting to insert user with username: " + username);


        String insertUserQuery = "INSERT INTO user (username, password, role_id, first_name, last_name, isActive, created_at) "
                + "VALUES (?, ?, ?, ?, ?, ?, NOW())";

        userStmt = connectDB.prepareStatement(insertUserQuery, Statement.RETURN_GENERATED_KEYS);
        userStmt.setString(1, student.getUsername());
        userStmt.setString(2, student.getPassword());
        userStmt.setInt(3, student.getRole());
        userStmt.setString(4, student.getFirstName());
        userStmt.setString(5, student.getLastName());
        userStmt.setInt(6, student.getIsActive());
        
        userStmt.executeUpdate();

        generatedKeys = userStmt.getGeneratedKeys();
        System.out.println("generated kets: " + generatedKeys);
        int userId = -1;
        if (generatedKeys.next()) {
            userId = generatedKeys.getInt(1);
        }

        String insertStudentQuery = "INSERT INTO student ("
                + "user_id, program_id, year_level, gender, birth_date, address, contact_number, profile_photo, isActive"
                + ") VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

        studentStmt = connectDB.prepareStatement(insertStudentQuery);
        studentStmt.setInt(1, userId);
        studentStmt.setInt(2, student.getProgram().getId());
        studentStmt.setInt(3, student.getYearLevel());
        studentStmt.setString(4, student.getGender());
        studentStmt.setDate(5, student.getBirthDate());
        studentStmt.setString(6, student.getAddress());
        studentStmt.setString(7, student.getContactNumber());
        studentStmt.setString(8, student.getProfilePhoto());
        studentStmt.setBoolean(9, true);

        int rowsInserted = studentStmt.executeUpdate();
        return rowsInserted;
       

    } catch (Exception e) {
        e.printStackTrace();
         return 0;
    } finally {
        try {
            if (generatedKeys != null) generatedKeys.close();
            if (userStmt != null) userStmt.close();
            if (studentStmt != null) studentStmt.close();
            if (connectDB != null) connectDB.close();
        } catch (Exception closeEx) {
            closeEx.printStackTrace();
        }
    }
}
   
   public int editStudent(Student student) {
            DatabaseConnection connection = new DatabaseConnection();
        Connection connectDB = connection.getConnection();

        PreparedStatement userStmt = null;
        PreparedStatement studentStmt = null;

        try {
            String updateUserQuery = "UPDATE user SET username = ?, password = ?, role_id = ?, first_name = ?, last_name = ?, isActive = ? WHERE id = ?";
            userStmt = connectDB.prepareStatement(updateUserQuery);
            userStmt.setString(1, student.getUsername());
            userStmt.setString(2, student.getPassword());
            userStmt.setInt(3, student.getRole());
            userStmt.setString(4, student.getFirstName());
            userStmt.setString(5, student.getLastName());
            userStmt.setInt(6, student.getIsActive());
            userStmt.setInt(7, student.getUserId());
            userStmt.executeUpdate();

            String updateStudentQuery = "UPDATE student SET program_id = ?, year_level = ?, gender = ?, birth_date = ?, address = ?, contact_number = ?, profile_photo = ?, isActive = ? WHERE user_id = ?";
            studentStmt = connectDB.prepareStatement(updateStudentQuery);
            studentStmt.setInt(1, student.getProgram().getId());
            studentStmt.setInt(2, student.getYearLevel());
            studentStmt.setString(3, student.getGender());
            studentStmt.setDate(4, student.getBirthDate());
            studentStmt.setString(5, student.getAddress());
            studentStmt.setString(6, student.getContactNumber());
            studentStmt.setString(7, student.getProfilePhoto());
            studentStmt.setBoolean(8, true);
            studentStmt.setInt(9, student.getUserId());

            int rowsUpdated = studentStmt.executeUpdate();
            return rowsUpdated;

        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        } finally {
            try {
                if (userStmt != null) userStmt.close();
                if (studentStmt != null) studentStmt.close();
                if (connectDB != null) connectDB.close();
            } catch (Exception closeEx) {
                closeEx.printStackTrace();
            }
        }
}

}