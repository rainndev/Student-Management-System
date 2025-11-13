/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package studentmanagementsystem.model;

/**
 *
 * @author rainndev
 */
public class Teacher extends User{
     private String department;
     private String contactNumber;
     private Subject subject;
     private int teacherSubjectId ;
     
     
     public Teacher(String username, String password, Role role, String firstName, String lastName, String department, String contactNumber, int isActive){
         super(username, password, role, firstName, lastName, isActive);
         this.department = department;
         this.contactNumber = contactNumber;
     }
     
     public String getDepartment(){ return department; }
     public String getContactNumber(){ return contactNumber; }
     public int getTeacherSubjectId(){ return teacherSubjectId; }
     public Subject getSubject() { return subject; }
     
     public void setDepartment(String department) { this.department = department;}
     public void setContactNumber(String contactNumber) { this.contactNumber = contactNumber;}
     public void setSubject(Subject subject) { this.subject = subject;}
     public void setTeacherSubjectId(int teacherSubjectId) { this.teacherSubjectId = teacherSubjectId;}
}
