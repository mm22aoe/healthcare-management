package hms.model;

public class AdminStaff extends User {

    private String employeeId;

    public AdminStaff(String userId, String name, String email, String phone, String employeeId) {
        super(userId, name, email, phone, "AdminStaff");
        this.employeeId = employeeId;
    }

    public String getEmployeeId() {
        return employeeId;
    }
}
