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
public class Grade {
    private int studentId; 
    private int teacherSubjectId;
    private BigDecimal grade;
    private String remarks;
    private String semester;
    private String schoolYear; 

    public Grade(int studentId, int teacherSubjectId, BigDecimal grade, String remarks, String semester, String schoolYear) {
        this.studentId = studentId;
        this.teacherSubjectId = teacherSubjectId;
        this.grade = grade;
        this.remarks = remarks;
        this.semester = semester;
        this.schoolYear = schoolYear;
    }

    public BigDecimal getGrade() {
        return grade;
    }

    public int getStudentId() {
        return studentId;
    }

    public String getRemarks() {
        return remarks;
    }

    public String getSchoolYear() {
        return schoolYear;
    }

    public String getSemester() {
        return semester;
    }

    public int getTecaherSubjectId() {
        return teacherSubjectId;
    }

    public void setGrade(BigDecimal grade) {
        this.grade = grade;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public void setSchoolYear(String schoolYear) {
        this.schoolYear = schoolYear;
    }

    public void setSemester(String semester) {
        this.semester = semester;
    }

    public void setStudentId(int studentId) {
        this.studentId = studentId;
    }

    public void setTecaherSubjectId(int tecaherSubjectId) {
        this.teacherSubjectId = tecaherSubjectId;
    }
    
        
}
