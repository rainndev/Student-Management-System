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
    private int gradeId;
    private int studentId; 
    private int teacherSubjectId;
    private BigDecimal grade;
    private String remarks;
    private int semester;
    private String schoolYear; 

    public Grade(int gradeId, int studentId, int teacherSubjectId, BigDecimal grade, String remarks, int semester, String schoolYear) {
        this.gradeId = gradeId;
        this.studentId = studentId;
        this.teacherSubjectId = teacherSubjectId;
        this.grade = grade;
        this.remarks = remarks;
        this.semester = semester;
        this.schoolYear = schoolYear;
    }
    
     public Grade(int studentId, int teacherSubjectId, BigDecimal grade, String remarks, int semester, String schoolYear) {
        this.studentId = studentId;
        this.teacherSubjectId = teacherSubjectId;
        this.grade = grade;
        this.remarks = remarks;
        this.semester = semester;
        this.schoolYear = schoolYear;
    }

    public int getGradeId() {
        return gradeId;
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

    public int getSemester() {
        return semester;
    }

    public int getTeacherSubjectId() {
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

    public void setSemester(int semester) {
        this.semester = semester;
    }

    public void setStudentId(int studentId) {
        this.studentId = studentId;
    }

    public void setTeacherSubjectId(int teacherSubjectId) {
        this.teacherSubjectId = teacherSubjectId;
    }
    
    public void setGradeId(int gradeId) {
        this.gradeId = gradeId;
    }
    
    
        
}
