/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package studentmanagementsystem.model;

/**
 *
 * @author rainndev
 */
public class TeacherSubjectComboBox {
    private int teacherSubjectId;
    private String teacherFirstName;
    private String teacherLastName;
    private String subjectCode;
    private String subjectName;

    public TeacherSubjectComboBox(int teacherSubjectId, String teacherFirstName, String teacherLastName, String subjectCode, String subjectName) {
        this.teacherSubjectId = teacherSubjectId;
        this.teacherFirstName = teacherFirstName;
        this.teacherLastName = teacherLastName;
        this.subjectCode = subjectCode;
        this.subjectName = subjectName;
    }

    public String getSubjectCode() {
        return subjectCode;
    }

    public String getSubjectName() {
        return subjectName;
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

    public void setSubjectCode(String subjectCode) {
        this.subjectCode = subjectCode;
    }

    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName;
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

    @Override
    public String toString() {
        return teacherFirstName + " " + teacherLastName + " - " + subjectCode;
    }    
}
