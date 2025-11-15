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
public class StudentGradeData {
    private int gradeId;
    private int studentId;
    private int teacherSubjectId;
    private BigDecimal grade;
    private String remarks;
    private int semester;
    private String schoolYear;
    
    private int subjectId;
    private String subjectCode;
    private String subjectName;
    private BigDecimal subjectUnits;
    private String teacherFirstName;
    private String teacherLastName;

    public StudentGradeData(int gradeId, int studentId, int teacherSubjectId, BigDecimal grade, String remarks, int semester, String schoolYear, int subjectId, String subjectCode, String subjectName, BigDecimal subjectUnits, String teacherFirstName, String teacherLastName) {
        this.gradeId = gradeId;
        this.studentId = studentId;
        this.teacherSubjectId = teacherSubjectId;
        this.grade = grade;
        this.remarks = remarks;
        this.semester = semester;
        this.schoolYear = schoolYear;
        this.subjectId = subjectId;
        this.subjectCode = subjectCode;
        this.subjectName = subjectName;
        this.subjectUnits = subjectUnits;
        this.teacherFirstName = teacherFirstName;
        this.teacherLastName = teacherLastName;
    }

    public BigDecimal getGrade() {
        return grade;
    }

    public int getGradeId() {
        return gradeId;
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

    public int getStudentId() {
        return studentId;
    }

    public String getSubjectCode() {
        return subjectCode;
    }

    public int getSubjectId() {
        return subjectId;
    }

    public String getSubjectName() {
        return subjectName;
    }

    public BigDecimal getSubjectUnits() {
        return subjectUnits;
    }

    public String getTeacherFirstName() {
        return teacherFirstName;
    }

    public String getTeacherLastName() {
        return teacherLastName;
    }

    public int getTeacherSubjectId() {
        return teacherSubjectId;
    }
    
    public String getTeacherFullName() {
        return teacherFirstName + " " + teacherLastName;
    }

    public void setGrade(BigDecimal grade) {
        this.grade = grade;
    }

    public void setGradeId(int gradeId) {
        this.gradeId = gradeId;
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

    public void setSubjectCode(String subjectCode) {
        this.subjectCode = subjectCode;
    }

    public void setSubjectId(int subjectId) {
        this.subjectId = subjectId;
    }

    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName;
    }

    public void setSubjectUnits(BigDecimal subjectUnits) {
        this.subjectUnits = subjectUnits;
    }

    public void setTeacherFirstName(String teacherFirstName) {
        this.teacherFirstName = teacherFirstName;
    }

    public void setTeacherLastName(String teacherLastName) {
        this.teacherLastName = teacherLastName;
    }

    public void setTeacherSubjectId(int teacherSubjectId) {
        this.teacherSubjectId = teacherSubjectId;
    }
}
