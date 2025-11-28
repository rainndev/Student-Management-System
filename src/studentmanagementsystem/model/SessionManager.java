package studentmanagementsystem.model;

public class SessionManager {

    private static Integer userId;
    private static String firstName;
    private static String lastName;
    private static User user;


   
    public static void setSession(Integer id, String fName, String lName) {
        userId = id;
        firstName = fName;
        lastName = lName;
    }

    public static void clearSession() {
        userId = null;
        firstName = null;
        lastName = null;
    }

    public static void setUser(User user) {
        SessionManager.user = user;
    }

    public static User getUser() {
        return user;
    }
    
    
    public static Integer getUserId() {
        return userId;
    }

    public static String getFirstName() {
        return firstName;
    }

    public static String getLastName() {
        return lastName;
    }

    public static String getFullName() {
        if (firstName == null || lastName == null) return null;

        String formattedFirst = firstName.substring(0,1).toUpperCase() +
                                firstName.substring(1).toLowerCase();

        String formattedLast = lastName.substring(0,1).toUpperCase() +
                               lastName.substring(1).toLowerCase();

        return formattedFirst + " " + formattedLast;
    }
    
    
    public static boolean isActive() {
        return userId != null || firstName != null || lastName != null;
    }
}
