/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package studentmanagementsystem.model;

/**
 *
 * @author rainndev
 */
public class Program {
    private int id;
    private String programCode;
    private String programName;
    private String description;

    public Program(int id, String programCode, String programName, String description) {
        this.id = id;
        this.programCode = programCode;
        this.programName = programName;
        this.description = description;
    }
    
    public int getId(){ return id; }
    public String getProgramCode(){ return programCode; }
    public String getProgramName(){ return programName; }
    public String getDescription(){ return description; }
    
    
    @Override
    public String toString() {
        return programName; 
    }

}
