/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package studentmanagementsystem.services;

import studentmanagementsystem.databases.DatabaseConnection;
import java.sql.Connection;
import java.sql.Statement;
import java.sql.ResultSet;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author rainndev
 */
public class AnalyticsService {
    private DatabaseConnection connection = new DatabaseConnection();
    
    public int getTotalUsers() {
        int  total = 0;
        String query =  "SELECT COUNT(*) FROM user";
         
        try(Connection connectDB = connection.getConnection();
            Statement statement = connectDB.createStatement();){
            ResultSet result =  statement.executeQuery(query);
            
            if(result.next()) {
                total = result.getInt(1);
            }
                        
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        return total;
    }
    
    public int getTotalStudents() {
        int  total = 0;
        String query =  "SELECT COUNT(*) FROM student";
         
        try(Connection connectDB = connection.getConnection();
            Statement statement = connectDB.createStatement();){
            ResultSet result =  statement.executeQuery(query);
            
            if(result.next()) {
                total = result.getInt(1);
            }
                        
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        return total;
    }
    
    public int getTotalTeachers() {
        int  total = 0;
        String query =  "SELECT COUNT(*) FROM teacher";
         
        try(Connection connectDB = connection.getConnection();
            Statement statement = connectDB.createStatement();){
            ResultSet result =  statement.executeQuery(query);
            
            if(result.next()) {
                total = result.getInt(1);
            }
                        
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        return total;
    }
    
    public int getTotalPrograms() {
        int  total = 0;
        String query =  "SELECT COUNT(*) FROM program";
         
        try(Connection connectDB = connection.getConnection();
            Statement statement = connectDB.createStatement();){
            ResultSet result =  statement.executeQuery(query);
            
            if(result.next()) {
                total = result.getInt(1);
            }
                        
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        return total;
    }
    
    public int getTotalSubjects() {
        int  total = 0;
        String query =  "SELECT COUNT(*) FROM subject";
         
        try(Connection connectDB = connection.getConnection();
            Statement statement = connectDB.createStatement();){
            ResultSet result =  statement.executeQuery(query);
            
            if(result.next()) {
                total = result.getInt(1);
            }
                        
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        return total;
    }
    
    public int[] getActiveInactiveCount() {
        String query = 
            "SELECT SUM(isActive = 1) AS activeCount, " +
            "       SUM(isActive = 0) AS inactiveCount " +
            "FROM user";

        int active = 0;
        int inactive = 0;

        try (Connection conn = connection.getConnection();
             Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(query)) {

            if (rs.next()) {
                active = rs.getInt("activeCount");
                inactive = rs.getInt("inactiveCount");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return new int[]{active, inactive};
   }   
    
    
    public Map<String, Number> getStudentCountPerProgram() {
        Map<String, Number> studentCountPerProgram = new HashMap<>();
        String query = "SELECT COUNT(*) AS student_count, P.program_code FROM student AS S " +
                       "INNER JOIN program AS P ON P.id = S.program_id " +
                       "GROUP BY P.program_code;";

        try (Connection conn = connection.getConnection();
             Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(query)) {

            while (rs.next()) {
                String program = rs.getString("program_code");
                int studentCount = rs.getInt("student_count");

                studentCountPerProgram.put(program, studentCount);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return studentCountPerProgram;
    }
    
    
     public Map<String, Number> getAllRemarksCountSubject() {
        Map<String, Number> allRemarksCountSubject = new HashMap<>();
        String query = "SELECT COUNT(*) AS count, remarks FROM grade " +
                       "GROUP BY remarks;";
        
        try (Connection conn = connection.getConnection();
             Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(query)) {

            while (rs.next()) {
                String remarks = rs.getString("remarks");
                int count = rs.getInt("count");

                allRemarksCountSubject.put(remarks, count);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return allRemarksCountSubject;
    }
     
     
     public Map<String, int[]> getSubjectGradeBreakdown() {
        
        Map<String, int[]> subjectGradeMap = new HashMap<>();
        String query =  """
        SELECT 
            S.subject_code,
            SUM(CASE WHEN G.remarks = 'Passed' THEN 1 ELSE 0 END) AS passed_count,
            SUM(CASE WHEN G.remarks = 'Failed' THEN 1 ELSE 0 END) AS failed_count,
            SUM(CASE WHEN G.remarks = 'Incomplete' THEN 1 ELSE 0 END) AS incomplete_count,
            SUM(CASE WHEN G.remarks = 'Dropped' THEN 1 ELSE 0 END) AS dropped_count
        FROM 
            grade AS G
        INNER JOIN 
            teacher_subject AS TS ON TS.id = G.teacher_subject_id
        INNER JOIN 
            subject AS S ON S.id = TS.subject_id
        GROUP BY 
            S.subject_code
        ORDER BY 
            S.subject_code;
        """;
        
        try (Connection conn = connection.getConnection();
             Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(query)) {

            while (rs.next()) {
                String subjectCode = rs.getString("subject_code");
                int[] counts = new int[4];
                
                counts[0] = rs.getInt("passed_count");
                counts[1] = rs.getInt("failed_count");
                counts[2] = rs.getInt("incomplete_count");
                counts[3] = rs.getInt("dropped_count");
                
                subjectGradeMap.put(subjectCode, counts);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return subjectGradeMap;
       
    }
}
