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
     private int userId;
     private String department;
     private String contactNumber;
     private int isActive;
     
     public Teacher(String username, String password, int role, String firstName, String lastName, String department, String contactNumber, int isActive){
         super(username, password, role, firstName, lastName, isActive);
         this.department = department;
         this.contactNumber = contactNumber;
         this.isActive = isActive;
     }
     
    
     public int getUserId(){ return userId; }
     public String getDepartment(){ return department; }
     public String getContactNumber(){ return contactNumber; }
     public int getIsActive(){ return isActive; }
     
     public void setUserID(int userID) { this.userId = userID;}
     public void setDepartment(String department) { this.department = department;}
     public void setContactNumber(String contactNumber) { this.contactNumber = contactNumber;}
     public void setIsActive(int isActive) { this.isActive = isActive;}
}
