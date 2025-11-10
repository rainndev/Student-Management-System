/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package studentmanagementsystem.model;

import java.sql.Date;

/**
 *
 * @author rainndev
 */
public class User {
    private String  username;
    private String  password;
    private int role;
    private String  firstName;
    private String  lastName;
    private int isActive;
    private Date createdAt;

    
    public User(){};
    
    public  User(String username, String password, int role, String firstName, String lastName, int isActive, Date createdAt ){
        this.username = username;
        this.password = password;
        this.role = role;
        this.firstName = firstName;
        this.lastName = lastName;
        this.isActive = isActive;
        this.createdAt = createdAt;
    }
    
    
    public  User(String username, String password, int role, String firstName, String lastName, int isActive){
        this(username, password, role, firstName, lastName, isActive, null);
    }
       
    public String getUsername() { return username; } 
    public int getRole() { return role; }
    public String getFirstName() { return firstName; }
    public String getLastName() { return lastName; }
    public int getIsActive() { return isActive; }
    public Date getCreatedAt() { return createdAt; }
    public String getPassword() { return password; }
    public String getFullName() {
        return this.firstName + " " + this.lastName;
    }

}
