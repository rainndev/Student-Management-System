/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package studentmanagementsystem.model;

import java.math.BigDecimal;

/**
 *
 * @author rainndev
 */
public class Subject {
    private int subjectID;
    private String subjectCode;
    private String subjectName;
    private BigDecimal subjectUnits;

    public Subject(String subjectCode, String subjectName, BigDecimal subjectUnits) {
        this.subjectCode = subjectCode;
        this.subjectName = subjectName;
        this.subjectUnits = subjectUnits;
    }
    
    public int getSubjectId() { return this.subjectID; }
    public String getSubjectCode() { return this.subjectCode; }
    public String getSubjectName() { return this.subjectName; }
    public BigDecimal getSubjectUnits() { return this.subjectUnits; }
    
    public void setSubjectId(int subjectId) {
        this.subjectID = subjectId;
    }
    
    public void setSubjectCode(int subjectCode) {
        this.subjectCode = this.subjectCode;
    }
    
    public void setSubjectName(String subjectName){
        this.subjectName = subjectName;
    }
    
    public void setSubjectUnit(BigDecimal subjectUnits){
        this.subjectUnits = subjectUnits;
    }
    
}
