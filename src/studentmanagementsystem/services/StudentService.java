package studentmanagementsystem.services;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Date; 
import java.util.ArrayList;
import java.util.List;
import studentmanagementsystem.databases.DatabaseConnection;
import studentmanagementsystem.model.Student;

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
            "U.id AS user_id, U.first_name, U.last_name, U.username, U.isActive AS user_active, " +
            // Student Table (S)
            "S.program_id, S.year_level, S.gender, S.birth_date, S.address, S.contact_number, S.profile_photo, S.isActive AS student_active, " +
            // Program Table (P)
            "P.program_code, P.program_name " +
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
                String firstName = result.getString("first_name");
                String lastName = result.getString("last_name");
                String username = result.getString("username");
                int isActive = result.getInt("student_active"); 
                     
                int yearLevel = result.getInt("year_level");
                String gender = result.getString("gender");
                Date birthDate = result.getDate("birth_date");
                String address = result.getString("address");
                String contactNumber = result.getString("contact_number");
                String profilePhotoPath = result.getString("profile_photo");
                
              
                String programName = result.getString("program_name");
                Student student = new Student(userId, programName, yearLevel, gender, birthDate, address, contactNumber, profilePhotoPath, isActive, firstName, lastName, username );
                
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
}