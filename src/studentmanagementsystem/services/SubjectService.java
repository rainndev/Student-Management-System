/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package studentmanagementsystem.services;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import studentmanagementsystem.databases.DatabaseConnection;
import studentmanagementsystem.model.Program;
import studentmanagementsystem.model.Subject;

/**
 *
 * @author rainndev
 */
public class SubjectService {
    private DatabaseConnection connection = new DatabaseConnection();
    private Connection connectDB = connection.getConnection();
    
    public List<Subject> getAllSubject() {
        List<Subject> subjectList = new ArrayList<>();
        String query =  "SELECT * FROM subject";

        try(Statement statement = connectDB.createStatement();){
            try(ResultSet result =  statement.executeQuery(query);){
                while (result.next()) {
                    int id = result.getInt("id");
                    String subjectCode = result.getString("subject_code");
                    String subjectName = result.getString("subject_name");
                    BigDecimal subjectUnits = result.getBigDecimal("units");

                    Subject subject = new Subject(subjectCode, subjectName, subjectUnits);
                    subject.setSubjectId(id);
                    subjectList.add(subject);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
                        
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        return subjectList;
    }
    
    public int addSubject(Subject subject) {
        String insertUserQuery = "INSERT INTO subject (`subject_code`, `subject_name`, `units`) VALUES (? ,? ,?)";
        
        try(PreparedStatement userSubjectStmnt = connectDB.prepareStatement(insertUserQuery);){
            userSubjectStmnt.setString(1, subject.getSubjectCode());
            userSubjectStmnt.setString(2, subject.getSubjectName());
            userSubjectStmnt.setBigDecimal(3, subject.getSubjectUnits());
            
            return userSubjectStmnt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
             return 0;
        } 
    }
    
    
    public int editSubject(Subject subject) {
        String insertUserQuery = "UPDATE subject SET subject_code= ? ,subject_name= ?, units = ?  WHERE id = ?";
        
        try(PreparedStatement userSubjectStmnt = connectDB.prepareStatement(insertUserQuery);){
            userSubjectStmnt.setString(1, subject.getSubjectCode());
            userSubjectStmnt.setString(2, subject.getSubjectName());
            userSubjectStmnt.setBigDecimal(3, subject.getSubjectUnits());
            userSubjectStmnt.setInt(4, subject.getSubjectId());
            
            return userSubjectStmnt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
             return 0;
        } 
    }
    
    public int deleteSubject(int ID){
        String deleteQuery = "DELETE FROM subject WHERE id = ?";
        
        try(PreparedStatement stmt = connectDB.prepareStatement(deleteQuery);){
           stmt.setInt(1, ID);
           return stmt.executeUpdate();         
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }
    
    
    public List<Subject> getSearchedSubject(String searchQuery) {
        List<Subject> subjectList = new ArrayList<>();
        String searchPattern = "%" + searchQuery + "%";
        String query = 
        "SELECT * FROM subject " +
        "WHERE id LIKE ? OR subject_code LIKE ? OR subject_name LIKE ?";
        
        try(PreparedStatement preparedStatement = connectDB.prepareStatement(query);){
            preparedStatement.setString(1, searchPattern);
            preparedStatement.setString(2, searchPattern);
            preparedStatement.setString(3, searchPattern);

            try(ResultSet result =  preparedStatement.executeQuery();){
                while (result.next()) {
                    int id = result.getInt("id");
                    String subjectCode = result.getString("subject_code");
                    String subjectName = result.getString("subject_name");
                    BigDecimal subjectUnits = result.getBigDecimal("units");

                    Subject subject = new Subject(subjectCode, subjectName, subjectUnits);
                    subject.setSubjectId(id);
                    subjectList.add(subject);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
                        
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        return subjectList;
    }
}
 