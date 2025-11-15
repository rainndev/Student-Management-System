/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package studentmanagementsystem.services;

import java.math.BigDecimal;
import studentmanagementsystem.databases.DatabaseConnection;
import studentmanagementsystem.model.StudentGradeData;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import studentmanagementsystem.model.Grade;


/**
 *
 * @author rainndev
 */
public class GradeService {
    private DatabaseConnection connection = new DatabaseConnection();
    
    public List<StudentGradeData> getGradesStudentById(int _id, int _semester, String _year) {   
        List<StudentGradeData> studentGradeDataList = new ArrayList<>();

        String query = "SELECT "
                + "G.id AS grade_id, "
                + "G.student_id, "
                + "G.teacher_subject_id, "
                + "G.grade, "
                + "G.remarks, "
                + "G.semester, "
                + "G.school_year, "
                + "S.id AS subject_id, "
                + "S.subject_code, "
                + "S.subject_name, "
                + "S.units, "
                + "U.first_name AS teacher_first_name, "
                + "U.last_name AS teacher_last_name "
                + "FROM grade AS G "
                + "INNER JOIN teacher_subject AS TS ON G.teacher_subject_id = TS.id "
                + "INNER JOIN subject AS S ON TS.subject_id = S.id "
                + "INNER JOIN teacher AS T ON T.user_id = TS.teacher_id "
                + "INNER JOIN user AS U ON U.id = T.user_id "
                + "WHERE G.student_id = ? AND G.semester = ? AND G.school_year = ?";

        try (Connection connectDB = connection.getConnection();
             PreparedStatement ps = connectDB.prepareStatement(query)) {

            ps.setInt(1, _id);
            ps.setInt(2, _semester);
            ps.setString(3, _year);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    StudentGradeData studentGradeData = new StudentGradeData(
                            rs.getInt("grade_id"),
                            rs.getInt("student_id"),
                            rs.getInt("teacher_subject_id"),
                            rs.getBigDecimal("grade"),
                            rs.getString("remarks"),
                            rs.getInt("semester"),
                            rs.getString("school_year"),
                            rs.getInt("subject_id"),
                            rs.getString("subject_code"),
                            rs.getString("subject_name"),
                            rs.getBigDecimal("units"),
                            rs.getString("teacher_first_name"),
                            rs.getString("teacher_last_name")
                    );
                    
                    studentGradeDataList.add(studentGradeData);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return studentGradeDataList;
    }
    
    
    public boolean addGrade(Grade grade) {
        String query =  "INSERT INTO grade(student_id, teacher_subject_id, grade, remarks, semester, school_year) VALUES(? , ? , ?, ?, ?, ?)";
        
        try(Connection connectDB = connection.getConnection();
               PreparedStatement gradeStmnt = connectDB.prepareStatement(query);){
               gradeStmnt.setInt(1, grade.getStudentId());
               gradeStmnt.setInt(2, grade.getTecaherSubjectId());
               gradeStmnt.setBigDecimal(3, grade.getGrade());
               gradeStmnt.setString(4, grade.getRemarks());
               gradeStmnt.setInt(5, grade.getSemester());
               gradeStmnt.setString(6, grade.getSchoolYear());
               
               int rowsAffected = gradeStmnt.executeUpdate();
               return rowsAffected > 0;  
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
