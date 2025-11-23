/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package studentmanagementsystem.model;

/**
 *
 * @author rainndev
 */
public class UserTeacherDetails {
    private User user;
    private int subjectCounts;
    private String department;
    
    public UserTeacherDetails() {
    }

    public UserTeacherDetails(User user, int subjectCounts, String department) {
        this.user = user;
        this.subjectCounts = subjectCounts;
        this.department = department;
    }
    
    public User getUser() {
        return user;
    }
    
    public void setUser(User user) {
        this.user = user;
    }

    public String getDepartment() {
        return department;
    }

    public int getSubjectCounts() {
        return subjectCounts;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public void setSubjectCounts(int subjectCounts) {
        this.subjectCounts = subjectCounts;
    }
}
