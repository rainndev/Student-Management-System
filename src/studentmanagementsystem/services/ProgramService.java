/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package studentmanagementsystem.services;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import studentmanagementsystem.databases.DatabaseConnection;
import studentmanagementsystem.model.Program;

/**
 *
 * @author rainndev
 */
public class ProgramService {
    private DatabaseConnection connection = new DatabaseConnection();
    private Connection connectDB = connection.getConnection();
    
    public List<Program> getAllPrograms() {
        List<Program> programList = new ArrayList<>();
   
        String query =  "SELECT * FROM program";
        
        try(Statement statement = connectDB.createStatement();){
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
}
