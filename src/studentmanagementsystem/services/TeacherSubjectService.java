package studentmanagementsystem.services;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import studentmanagementsystem.databases.DatabaseConnection;
import studentmanagementsystem.model.TeacherSubject;
import studentmanagementsystem.model.TeacherSubjectComboBox;

public class TeacherSubjectService {
    private DatabaseConnection databaseConnection = new DatabaseConnection();
    
    public List<TeacherSubjectComboBox> getAllTeacherSubjectComboBox() {
        List<TeacherSubjectComboBox> teacherSubjectList = new ArrayList<>();
        
        String query = "SELECT TS.id as teacher_subject_id, "
                    + "U.first_name as teacher_first_name, "
                    + "U.last_name as teacher_last_name, "
                    + "S.subject_code, "
                    + "s.subject_name FROM teacher_subject AS TS "
                    + "INNER JOIN teacher as T  ON T.user_id = TS.teacher_id " 
                    + "INNER JOIN user as U ON U.id = T.user_id "
                    + "INNER JOIN subject as S ON s.id = TS.subject_id;";
            try(Connection connectDB = databaseConnection.getConnection();
             Statement statement = connectDB.createStatement();
             ResultSet result = statement.executeQuery(query);
                ){
            
            while (result.next()) {
                
                int teacherSubjectId = result.getInt("teacher_subject_id");
                String teacherFirstName = result.getString("teacher_first_name");
                String teacherLastName = result.getString("teacher_last_name");
                String subjectCode = result.getString("subject_code");
                String subjectName = result.getString("subject_name");
             
                TeacherSubjectComboBox teacherSubjectCombo = new TeacherSubjectComboBox(teacherSubjectId, teacherFirstName, teacherLastName, subjectCode, subjectName);
                teacherSubjectList.add(teacherSubjectCombo);
            }
            
        } catch (Exception e) {
            e.printStackTrace();
        }      
        return teacherSubjectList;
    }
    
    public List<TeacherSubjectComboBox> getAllTeacherSubjectStudentID(int ID) {
        List<TeacherSubjectComboBox> teacherSubjectList = new ArrayList<>();
        String query = """
                     SELECT DISTINCT
                         TS.id AS teacher_subject_id,
                         U.first_name AS teacher_first_name,
                         U.last_name AS teacher_last_name,
                         S.subject_code,
                         S.subject_name
                     FROM grade AS G
                     INNER JOIN teacher_subject AS TS ON G.teacher_subject_id = TS.id
                     INNER JOIN teacher AS T ON T.user_id = TS.teacher_id
                     INNER JOIN user AS U ON U.id = T.user_id
                     INNER JOIN subject AS S ON S.id = TS.subject_id
                     WHERE G.student_id = ?;
                     """;
        
        
        try(Connection connectDB = databaseConnection.getConnection();
             PreparedStatement preparedStatement = connectDB.prepareStatement(query);){
            
            preparedStatement.setInt(1, ID);
            
            try(ResultSet result = preparedStatement.executeQuery();){
                while (result.next()) {
                    int teacherSubjectId = result.getInt("teacher_subject_id");
                    String teacherFirstName = result.getString("teacher_first_name");
                    String teacherLastName = result.getString("teacher_last_name");
                    String subjectCode = result.getString("subject_code");
                    String subjectName = result.getString("subject_name");

                    TeacherSubjectComboBox teacherSubjectCombo = new TeacherSubjectComboBox(teacherSubjectId, teacherFirstName, teacherLastName, subjectCode, subjectName);
                    teacherSubjectList.add(teacherSubjectCombo);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }      
        return teacherSubjectList;              
    }
    
    public boolean editSubjectTeacher(int teacherId, int subjectId, int teacherSubjectId) {
        String query = "UPDATE teacher_subject SET teacher_id= ?, subject_id = ? WHERE id = ?";
        try (Connection connection = databaseConnection.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, teacherId);
            preparedStatement.setInt(2, subjectId);
            preparedStatement.setInt(3, teacherSubjectId);
            preparedStatement.executeUpdate();
            return true;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
     
     
    public boolean addSubjectTeacher(int teacherId, int subjectId) {
        String query = "INSERT INTO Teacher_Subject (teacher_id, subject_id) VALUES (?, ?)";
        try (Connection connection = databaseConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setInt(1, teacherId);
            preparedStatement.setInt(2, subjectId);
            preparedStatement.executeUpdate();
            return true;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
