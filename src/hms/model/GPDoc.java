package hms.model;

public class GPDoc extends User {

    private String medNo;
    private String employeeId;

    public GPDoc(String userId, String name, String email, String phone,
                 String medNo, String employeeId) {
        super(userId, name, email, phone, "GPDoc");
        this.medNo = medNo;
        this.employeeId = employeeId;
    }

    public String getMedNo() {
        return medNo;
    }

    public String getEmployeeId() {
        return employeeId;
    }
}
