/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package studentmanagementsystem.services;
import studentmanagementsystem.model.UserStudentDetails;
import java.sql.ResultSet;
import java.sql.Connection;
import java.sql.PreparedStatement;
import studentmanagementsystem.databases.DatabaseConnection;
import studentmanagementsystem.model.UserTeacherDetails;
import studentmanagementsystem.model.Role;
import studentmanagementsystem.model.User;
/**
 *
 * @author rainndev
 */
public class UserDetailsService {
    private DatabaseConnection connection = new DatabaseConnection();
    
    public UserStudentDetails getBasicStudentDetails(int id) {
         String query = """
                        SELECT 
                                R.id AS role_id, 
                                R.role_name, 
                                U.id AS user_id, 
                                U.username, 
                                U.first_name, 
                                U.last_name, 
                                U.created_at, 
                                U.password,
                                U.isActive,
                                S.year_level, 
                                P.program_code
                            FROM user AS U
                            LEFT JOIN role AS R ON U.role_id = R.id
                            LEFT JOIN student AS S ON U.id = S.user_id
                            LEFT JOIN program AS P ON S.program_id = P.id
                            WHERE U.id = ?;
                        """;

         try (Connection connectDB = connection.getConnection();
              PreparedStatement preparedStatement = connectDB.prepareStatement(query)) {

             preparedStatement.setInt(1, id);

             try (ResultSet result = preparedStatement.executeQuery()) {

                 if (!result.next()) {
                     return null;
                 }

                 Role role = new Role(
                         result.getInt("role_id"),
                         result.getString("role_name")
                 );

                 User user = new User(
                         result.getString("username"),
                         result.getString("password"),
                         role,
                         result.getString("first_name"),
                         result.getString("last_name"),
                         result.getInt("isActive"),
                         result.getDate("created_at")
                 );

                 int yearLevel = result.getInt("year_level");
                 String programName = result.getString("program_code");

                 return new UserStudentDetails(
                         user,
                         yearLevel,
                         programName
                 );
             }

         } catch (Exception e) {
             e.printStackTrace();
             return null;
         }
     }
    
    public UserTeacherDetails getBasicTeacherDetails(int id) {
         String query = """
                    SELECT 
                          R.id AS role_id, 
                          R.role_name, 
                          U.id AS user_id, 
                          U.username, 
                          U.first_name, 
                          U.last_name, 
                          U.created_at, 
                          U.password,
                          U.isActive,
                          T.department,
                          COUNT(S.id) AS subject_count_handled
                      FROM user AS U
                      LEFT JOIN role AS R ON U.role_id = R.id
                      LEFT JOIN teacher AS T ON U.id = T.user_id
                      LEFT JOIN teacher_subject AS TS ON T.user_id = TS.teacher_id
                      LEFT JOIN subject AS S ON TS.subject_id = S.id
                      WHERE U.id = ?
                      GROUP BY 
                          R.id, 
                          R.role_name, 
                          U.id, 
                          U.username, 
                          U.first_name, 
                          U.last_name, 
                          U.created_at, 
                          U.password,
                          U.isActive,
                          T.department;
                        """;

         try (Connection connectDB = connection.getConnection();
              PreparedStatement preparedStatement = connectDB.prepareStatement(query)) {

             preparedStatement.setInt(1, id);

             try (ResultSet result = preparedStatement.executeQuery()) {

                 if (!result.next()) {
                     return null;
                 }

                 Role role = new Role(
                         result.getInt("role_id"),
                         result.getString("role_name")
                 );

                 User user = new User(
                         result.getString("username"),
                         result.getString("password"),
                         role,
                         result.getString("first_name"),
                         result.getString("last_name"),
                         result.getInt("isActive"),
                         result.getDate("created_at")
                 );

                 int subjectCountHandled = result.getInt("subject_count_handled");
                 String department = result.getString("department");

                 return new UserTeacherDetails(
                         user,
                         subjectCountHandled,
                         department
                 );
             }

         } catch (Exception e) {
             e.printStackTrace();
             return null;
         }
     }
    
    
}
