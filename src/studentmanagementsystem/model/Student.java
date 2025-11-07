package studentmanagementsystem.model;

import java.sql.Date; 

public class Student {

    private int userId; 	
    private String program; 
    private int yearLevel;
    private String gender;
    private Date birthDate; 	 	
    private String address;
    private String contactNumber;
    private String profilePhotoPath; 	 	
    private int isActive;
    	
    private String firstName;	
    private String lastName;	
    private String userName;	


    public Student() {
    }

    public Student(int userId, String program, int yearLevel, String gender,	
                        Date birthDate, String address, String contactNumber,	
                        String profilePhotoPath, int isActive,
                        String firstName, String lastName, String userName) {
        this.userId = userId;
        this.program = program;
        this.yearLevel = yearLevel;
        this.gender = gender;
        this.birthDate = birthDate;
        this.address = address;
        this.contactNumber = contactNumber;
        this.profilePhotoPath = profilePhotoPath;
        this.isActive = isActive;
        
     
        this.firstName = firstName;
        this.lastName = lastName;
        this.userName = userName;
    }

    

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }


    public String getProgram() { 
        return program;
    }

    public void setProgram(String program) { 
        this.program = program;
    }

    public int getYearLevel() {
        return yearLevel;
    }

    public void setYearLevel(int yearLevel) {
        this.yearLevel = yearLevel;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getContactNumber() {
        return contactNumber;
    }

    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }

    public String getProfilePhotoPath() {
        return profilePhotoPath;
    }

    public void setProfilePhotoPath(String profilePhotoPath) {
        this.profilePhotoPath = profilePhotoPath;
    }

    public int getIsActive() { 
        return isActive;
    }

    public void setIsActive(int isActive) { 
        this.isActive = isActive;
    }
    

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
    
    public String getFullName() {
        return this.firstName + " " + this.lastName;
    }
}