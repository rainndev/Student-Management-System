package studentmanagementsystem.model;

import java.sql.Date; 

public class Student extends User{

    private int userId; 	
    private String program; 
    private int yearLevel;
    private String gender;
    private Date birthDate; 	 	
    private String address;
    private String contactNumber;
    private String profilePhotoPath; 
    private int programId;
    private int isActive;
    private String profilePhoto;
    
    public Student(int roleId, String program, int yearLevel, String gender,	
                        Date birthDate, String address, String contactNumber,	
                        String profilePhotoPath, int isActive,
                        String firstName, String lastName, String userName, 
                        int programId, String profilePhoto) {
        
        super(userName, "123", roleId , firstName, lastName, isActive);
        
        this.program = program;
        this.yearLevel = yearLevel;
        this.gender = gender;
        this.address = address;
        this.birthDate = birthDate;
        this.contactNumber = contactNumber;
        this.profilePhotoPath = profilePhotoPath;
        this.programId = programId;
        this.profilePhoto = profilePhoto;
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
    
    public int getProgramId() {
        return this.programId;
    }
    
    public void setProgramId(int programID) {
       this.programId = programID;
    }
    
    public String getProfilePhoto() {
        return profilePhoto;
    }
    
    public void setProfilePhoto(String profilePhoto) {
        this.profilePhoto = profilePhoto;
    }
}