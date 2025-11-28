/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package studentmanagementsystem.model;

import java.lang.classfile.instruction.SwitchCase;
import java.sql.Date;
import studentmanagementsystem.model.Role;

/**
 *
 * @author rainndev
 */
public class User {
    private String  username;
    private int UserID;
    private String  password;
    private Role role;
    private String  firstName;
    private String  lastName;
    private int isActive;
    private Date createdAt;

    
    public User(){};
    
    public  User(String username, String password, Role role, String firstName, String lastName, int isActive, Date createdAt ){
        this.username = username;
        this.password = password;
        this.role = role;
        this.firstName = firstName;
        this.lastName = lastName;
        this.isActive = isActive;
        this.createdAt = createdAt;
    }
    
    
    public  User(String username, String password, Role role, String firstName, String lastName, int isActive){
        this(username, password, role, firstName, lastName, isActive, null);
    }
    
    public  User(String username, Role role, String firstName, String lastName, int isActive){
        this.username = username;
        this.role = role;
        this.firstName = firstName;
        this.lastName = lastName;
        this.isActive = isActive;
    }
    
    
    public  User(String username, String password, Role role, String firstName, String lastName) {
        this.username = username;
        this.password = password;
        this.role = role;
        this.firstName = firstName;
        this.lastName = lastName;
    }
       
    public String getUsername() { return username; } 
    public Role getRole() { return role; }
    public String getFirstName() { return firstName; }
    public String getLastName() { return lastName; }
    public int getIsActive() { return isActive; }
    public Date getCreatedAt() { return createdAt; }
    public String getPassword() { return password; }
    public String getFullName() {
        return this.firstName + " " + this.lastName;
    }
    public int getUserID() { return this.UserID;}
    
    public String getActiveState(int activeId) {
      return activeId == 1 ? "Active" : "Inactive";
    }

    public void setCreatedAt(Date date) { this.createdAt = date; }
    public void setUserId(int UserID) { this.UserID = UserID; }

    public void setPassword(String password) {
        this.password = password;
    }
}
