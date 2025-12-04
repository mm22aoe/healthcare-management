package hms.model;

public class SpecialistDoc extends User {

    private String medNo;
    private String specialty;
    private String employeeId;

    public SpecialistDoc(String userId, String name, String email, String phone,
                         String medNo, String specialty, String employeeId) {
        super(userId, name, email, phone, "SpecialistDoc");
        this.medNo = medNo;
        this.specialty = specialty;
        this.employeeId = employeeId;
    }

    public String getMedNo() {
        return medNo;
    }

    public String getSpecialty() {
        return specialty;
    }

    public String getEmployeeId() {
        return employeeId;
    }
}
