package studentmanagementsystem.model;

import java.sql.Date; 

public class Student extends User{
    private Program program; 
    private int yearLevel;
    private String gender;
    private Date birthDate; 	 	
    private String address;
    private String contactNumber;
    private int programId;
    private String profilePhoto;

    public Student() {
    }
    
    public Student(Role role, Program program, int yearLevel, String gender,	
                        Date birthDate, String address, String contactNumber, int isActive,
                        String firstName, String lastName, String userName, String profilePhoto) {
        
        super(userName, "123", role , firstName, lastName, isActive);
        
        this.program = program;
        this.yearLevel = yearLevel;
        this.gender = gender;
        this.address = address;
        this.birthDate = birthDate;
        this.contactNumber = contactNumber;
        this.profilePhoto = profilePhoto;
    }
    

    public Program getProgram() { 
        return program;
    }

    public void setProgram(Program program) { 
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