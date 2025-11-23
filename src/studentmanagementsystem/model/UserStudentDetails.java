/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package studentmanagementsystem.model;

/**
 *
 * @author rainndev
 */
public class UserStudentDetails {
    private User user;
    private int yearLevel;
    private String programCode;
    
    public UserStudentDetails() {
    }

    public UserStudentDetails(User user, int yearLevel, String programCode) {
        this.user = user;
        this.yearLevel = yearLevel;
        this.programCode = programCode;
    }

    public User getUser() {
        return user;
    }

    public int getYearLevel() {
        return yearLevel;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setYearLevel(int yearLevel) {
        this.yearLevel = yearLevel;
    }

    public void setProgramCode(String programCode) {
        this.programCode = programCode;
    }

    public String getProgramCode() {
        return programCode;
    }
}
