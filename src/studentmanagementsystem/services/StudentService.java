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
import studentmanagementsystem.model.Role;
import studentmanagementsystem.model.Program;

/**
 * Service class for fetching and managing Student data, joining User and Program information.
 *
 * @author rainndev
 */
public class StudentService { 	
    private DatabaseConnection connection = new DatabaseConnection();
    
   public List<Student> getAllStudent() {
        List<Student> studentList = new ArrayList<>();
        String query =
           "SELECT " +
           // User Table (U)
           "U.id AS user_id, U.password, U.first_name, U.last_name, U.username, U.isActive AS user_active, U.role_id, " +
           // ROLE Table (R) - NEW COLUMNS ADDED HERE
           "R.role_name, " + // Use R.role_name instead of U.role_name
           // Student Table (S)
           "S.program_id, S.year_level, S.gender, S.birth_date, S.address, S.contact_number, S.profile_photo, S.isActive AS student_active, " +
           // Program Table (P)
           "P.program_code, P.program_name, P.description " +
           "FROM `Student` S " +
           "INNER JOIN `User` U ON S.user_id = U.id " +
           "INNER JOIN `Program` P ON S.program_id = P.id " +
           // NEW INNER JOIN FOR ROLE
           "INNER JOIN `Role` R ON U.role_id = R.id " +
           "WHERE U.role_id = 2";

        
        try(Connection connectDB = connection.getConnection();
            Statement statement = connectDB.createStatement();
             ResultSet result = statement.executeQuery(query);
                ){
            System.out.println(result);
            
            while (result.next()) {
                
                int roleId = result.getInt("role_id");
                String roleName = result.getString("role_name");
                Role role = new Role(roleId, roleName);
                
                int userId = result.getInt("user_id");
                String firstName = result.getString("first_name");
                String lastName = result.getString("last_name");
                String username = result.getString("username");
                String password = result.getString("password");
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
                        role,
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
                student.setPassword(password);
                studentList.add(student);
            }
            
        } catch (Exception e) {
            e.printStackTrace();
        }      
        return studentList;
    }
    
   public boolean addStudent(Student student) {
        String insertUserQuery = "INSERT INTO user (username, password, role_id, first_name, last_name, isActive, created_at) "
                    + "VALUES (?, ?, ?, ?, ?, ?, NOW())";

        String insertStudentQuery = "INSERT INTO student ("
                    + "user_id, program_id, year_level, gender, birth_date, address, contact_number, profile_photo, isActive"
                    + ") VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
       
       

        try(Connection connectDB = connection.getConnection();
            PreparedStatement userStmt = connectDB.prepareStatement(insertUserQuery, Statement.RETURN_GENERATED_KEYS);
            PreparedStatement studentStmt = connectDB.prepareStatement(insertStudentQuery);
                ){

            String username = student.getUsername();
            System.out.println("DEBUG: Attempting to insert user with username: " + username);

            userStmt.setString(1, student.getUsername());
            userStmt.setString(2, student.getPassword());
            userStmt.setInt(3, student.getRole().getRoleID());
            userStmt.setString(4, student.getFirstName());
            userStmt.setString(5, student.getLastName());
            userStmt.setInt(6, student.getIsActive());

            userStmt.executeUpdate();

           int userId = -1;
            try( ResultSet generatedKeys = userStmt.getGeneratedKeys();){
                if (generatedKeys.next()) {
                    userId = generatedKeys.getInt(1);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            
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
            return rowsInserted > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } 
    }
   
   public boolean editStudent(Student student) {
        String updateUserQuery = "UPDATE user SET username = ?, password = ?, role_id = ?, first_name = ?, last_name = ?, isActive = ? WHERE id = ?";
        String updateStudentQuery = "UPDATE student SET program_id = ?, year_level = ?, gender = ?, birth_date = ?, address = ?, contact_number = ?, profile_photo = ?, isActive = ? WHERE user_id = ?";
        
       
        try(Connection connectDB = connection.getConnection();
            PreparedStatement userStmt = connectDB.prepareStatement(updateUserQuery);
            PreparedStatement studentStmt = connectDB.prepareStatement(updateStudentQuery);
                ){
            
            userStmt.setString(1, student.getUsername());
            userStmt.setString(2, student.getPassword());
            userStmt.setInt(3, student.getRole().getRoleID());
            userStmt.setString(4, student.getFirstName());
            userStmt.setString(5, student.getLastName());
            userStmt.setInt(6, student.getIsActive());
            userStmt.setInt(7, student.getUserID());
            userStmt.executeUpdate();
           
            studentStmt.setInt(1, student.getProgram().getId());
            studentStmt.setInt(2, student.getYearLevel());
            studentStmt.setString(3, student.getGender());
            studentStmt.setDate(4, student.getBirthDate());
            studentStmt.setString(5, student.getAddress());
            studentStmt.setString(6, student.getContactNumber());
            studentStmt.setString(7, student.getProfilePhoto());
            studentStmt.setBoolean(8, true);
            studentStmt.setInt(9, student.getUserID());

            int rowsUpdated = studentStmt.executeUpdate();
            return rowsUpdated > 0;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
}

   public boolean deleteStudent(int ID){
        String deleteQuery = "DELETE FROM user WHERE id = ?";

        try(Connection connectDB = connection.getConnection();
            PreparedStatement stmt = connectDB.prepareStatement(deleteQuery);)
        {
           stmt.setInt(1, ID);  
           int rowsDeleted = stmt.executeUpdate();  
           return rowsDeleted > 0;
       } catch (Exception e) {
           e.printStackTrace();
           return false;
       }
   }
   
   public List<Student> getSearchedUser(String searchQuery) {
        List<Student> studentList = new ArrayList<>();
        String searchPattern = "%" + searchQuery + "%";
        
        
        String query =
           "SELECT " +
           // User Table (U)
           "U.id AS user_id, U.password, U.first_name, U.last_name, U.username, U.isActive AS user_active, U.role_id, " +
           // ROLE Table (R) - NEW COLUMNS ADDED HERE
           "R.role_name, " + // Use R.role_name instead of U.role_name
           // Student Table (S)
           "S.program_id, S.year_level, S.gender, S.birth_date, S.address, S.contact_number, S.profile_photo, S.isActive AS student_active, " +
           // Program Table (P)
           "P.program_code, P.program_name, P.description " +
           "FROM `Student` S " +
           "INNER JOIN `User` U ON S.user_id = U.id " +
           "INNER JOIN `Program` P ON S.program_id = P.id " +
           // NEW INNER JOIN FOR ROLE
           "INNER JOIN `Role` R ON U.role_id = R.id " +
           "WHERE CAST(user_id AS CHAR) LIKE ? OR U.first_name LIKE ? OR U.last_name LIKE ? and U.role_id = 2";
        
        try(Connection connectDB = connection.getConnection();
            Statement statement = connectDB.createStatement();
            PreparedStatement preparedStatement = connectDB.prepareStatement(query);
                ){
            preparedStatement.setString(1, searchPattern);
            preparedStatement.setString(2, searchPattern);
            preparedStatement.setString(3, searchPattern);
            
            try( ResultSet result = preparedStatement.executeQuery()
                    ){
                while (result.next()) {    
                int roleId = result.getInt("role_id");
                String roleName = result.getString("role_name");
                Role role = new Role(roleId, roleName);
                
                int userId = result.getInt("user_id");
                String firstName = result.getString("first_name");
                String lastName = result.getString("last_name");
                String password = result.getString("password");
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
                        role,
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
                student.setPassword(password);
                studentList.add(student);
            }
            } catch (Exception e) {
                e.printStackTrace();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }      
        return studentList;
    }
   
   public Student getStudentById(int ID) {
        Student student = new Student();
        String query =
        "SELECT " +
        "U.id AS user_id, U.password, U.first_name, U.last_name, U.username, U.isActive AS user_active, U.role_id, " +
        "R.role_name, " +
        "S.program_id, S.year_level, S.gender, S.birth_date, S.address, S.contact_number, S.profile_photo, S.isActive AS student_active, " +
        "P.program_code, P.program_name, P.description " +
        "FROM `User` U " +
        "LEFT JOIN `Student` S ON S.user_id = U.id " +
        "LEFT JOIN `Program` P ON S.program_id = P.id " +
        "LEFT JOIN `Role` R ON U.role_id = R.id " +
        "WHERE U.role_id = 2 AND U.id = ?";

        
        try(Connection connectDB = connection.getConnection();
            PreparedStatement stmt = connectDB.prepareStatement(query);
                ){
            
            stmt.setInt(1, ID);
            
            try(ResultSet result = stmt.executeQuery()) {
                 if (result.next()) {

                     int roleId = result.getInt("role_id");
                     String roleName = result.getString("role_name");
                     Role role = new Role(roleId, roleName);

                     int userId = result.getInt("user_id");
                     String firstName = result.getString("first_name");
                     String lastName = result.getString("last_name");
                     String username = result.getString("username");
                     String password = result.getString("password");
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

                     student = new Student(
                             role,
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
                     student.setPassword(password);
                     student.setUserId(userId);
                 }
            } catch (Exception e) {
                e.printStackTrace();
            }
            
        } catch (Exception e) {
            e.printStackTrace();
        }      
        return student;
    }
           }
