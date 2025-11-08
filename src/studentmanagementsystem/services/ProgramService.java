/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package studentmanagementsystem.services;

import java.sql.Connection;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import studentmanagementsystem.databases.DatabaseConnection;
import studentmanagementsystem.model.Program;
import studentmanagementsystem.model.Student;

/**
 *
 * @author rainndev
 */
public class ProgramService {
    public List<Program> getAllPrograms() {
        List<Program> programList = new ArrayList<>();
        
        DatabaseConnection connection = new DatabaseConnection();
        Connection connectDB = connection.getConnection();
        String query =  "SELECT * FROM program";

        Statement statement = null;
        ResultSet result = null;
        
        try {
            statement = connectDB.createStatement();
            result = statement.executeQuery(query);
            System.out.println(result);
            
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
        } finally {
            try {
                if (result != null) result.close();
                if (statement != null) statement.close();
                if (connectDB != null) connectDB.close();
            } catch (Exception closeEx) {
                closeEx.printStackTrace();
            }
        }
        
        return programList;
    }
}
