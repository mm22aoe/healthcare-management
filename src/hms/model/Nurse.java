package hms.model;

public class Nurse extends User {

    private String employeeId;

    public Nurse(String userId, String name, String email, String phone, String employeeId) {
        super(userId, name, email, phone, "Nurse");
        this.employeeId = employeeId;
    }

    public String getEmployeeId() {
        return employeeId;
    }

    public void giveClinicalSupport() {
        // business logic placeholder
    }
}
