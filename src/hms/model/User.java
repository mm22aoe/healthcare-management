package hms.model;

public abstract class User {
    protected String userId;
    protected String name;
    protected String email;
    protected String phone;
    protected String userType; // GPDoc, SpecialistDoc, AdminStaff, Nurse

    public User(String userId, String name, String email, String phone, String userType) {
        this.userId = userId;
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.userType = userType;
    }

    public String getUserId() {
        return userId;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getPhone() {
        return phone;
    }

    public String getUserType() {
        return userType;
    }

    public boolean login(String id) {
        return this.userId.equals(id);
    }

    public void logout() {
        // no-op placeholder
    }
}
