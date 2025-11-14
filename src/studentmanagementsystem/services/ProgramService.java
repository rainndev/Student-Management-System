/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package studentmanagementsystem.services;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.List;
import studentmanagementsystem.databases.DatabaseConnection;
import studentmanagementsystem.model.Program;
import studentmanagementsystem.model.Subject;

/**
 *
 * @author rainndev
 */
public class ProgramService {
    private DatabaseConnection connection = new DatabaseConnection();

    
    public List<Program> getAllPrograms() {
        List<Program> programList = new ArrayList<>();
   
        String query =  "SELECT * FROM program";
        
        try(Connection connectDB = connection.getConnection();
            Statement statement = connectDB.createStatement();){
            try(ResultSet result =  statement.executeQuery(query);){
                while (result.next()) {
                    int id = result.getInt("id");
                    String programCode = result.getString("program_code");
                    String programName = result.getString("program_name");
                    String description = result.getString("description");

                    Program program = new Program(id, programCode, programName, description);
                    programList.add(program);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
                        
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        return programList;
    }
    
    
    public boolean addProgram(Program program) {
        String query =  "INSERT INTO program(program_code, program_name, description) VALUES(? , ? , ?)";
        
        try(Connection connectDB = connection.getConnection();
            PreparedStatement programStmnt = connectDB.prepareStatement(query);){
               programStmnt.setString(1, program.getProgramCode());
               programStmnt.setString(2, program.getProgramName());
               programStmnt.setString(3, program.getDescription());
               
               int rowsAffected = programStmnt.executeUpdate();
               return rowsAffected > 0;  
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
      
       
    public boolean editProgram(Program program) {
        String query = "UPDATE program SET program_code = ?, program_name = ?, description = ?  WHERE id = ?";
        
        try(Connection connectDB = connection.getConnection();
            PreparedStatement stmnt = connectDB.prepareStatement(query);){
            stmnt.setString(1, program.getProgramCode());
            stmnt.setString(2, program.getProgramName());
            stmnt.setString(3, program.getDescription());
            stmnt.setInt(4, program.getId());
            int rows = stmnt.executeUpdate();
            
            return rows > 0;
        } catch (Exception e) {
            e.printStackTrace();
             return false;
        } 
    }
    
    public boolean deleteProgram(int ID){
        String query = "DELETE FROM program WHERE id = ?";
        
        try(Connection connectDB = connection.getConnection();
            PreparedStatement stmt = connectDB.prepareStatement(query);){
           stmt.setInt(1, ID);
           
           int rows = stmt.executeUpdate(); 
           return rows > 0;         
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    
    
    public List<Program> getSearchedPrograms(String searchQuery) {
        List<Program> programList = new ArrayList<>();
        String searchPattern = "%" + searchQuery + "%";
        
        String query = 
        "SELECT * FROM program  " +
        "WHERE id LIKE ? OR program_code LIKE ? OR program_name LIKE ?";
        
        
        try(Connection connectDB = connection.getConnection();
            PreparedStatement prepareStatement = connectDB.prepareStatement(query);){
            prepareStatement.setString(1, searchPattern);
            prepareStatement.setString(2, searchPattern);
            prepareStatement.setString(3, searchPattern);
            
            try(ResultSet result =  prepareStatement.executeQuery();){
                while (result.next()) {
                    int id = result.getInt("id");
                    String programCode = result.getString("program_code");
                    String programName = result.getString("program_name");
                    String description = result.getString("description");

                    Program program = new Program(id, programCode, programName, description);
                    programList.add(program);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
                        
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        return programList;
    }
    
}
