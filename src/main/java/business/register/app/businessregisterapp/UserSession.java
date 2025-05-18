package business.register.app.businessregisterapp;

public class UserSession {
    private static UserSession instance;
    private String email;
    private String role;

    private UserSession(String email,  String role){
        this.email = email;
        this.role = role;

    }
    public static void createSession(String email,  String role) {
        instance = new UserSession(email,  role);
    }

    public static UserSession getInstance() {
        return instance;
    }

    public String getEmail() {
        return email;
    }



    public String getRole() {
        return role;
    }

    public static void clearSession() {
        instance = null;
    }
}
