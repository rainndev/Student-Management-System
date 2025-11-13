package studentmanagementsystem.services;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import studentmanagementsystem.databases.DatabaseConnection;

public class TeacherSubjectService {
    private DatabaseConnection databaseConnection = new DatabaseConnection();
    
    
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
