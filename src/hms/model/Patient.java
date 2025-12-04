package hms.model;

public class Patient {
    private String patientId;
    private String name;
    private String dob;     // for simplicity, keep as string
    private String address;
    private String phone;
    private String nhsNumber;
    private String gpSurgery;

    public Patient(String patientId, String name, String dob, String address,
                   String phone, String nhsNumber, String gpSurgery) {
        this.patientId = patientId;
        this.name = name;
        this.dob = dob;
        this.address = address;
        this.phone = phone;
        this.nhsNumber = nhsNumber;
        this.gpSurgery = gpSurgery;
    }

    public String getPatientId() {
        return patientId;
    }

    public String getName() {
        return name;
    }

    public String getDob() {
        return dob;
    }

    public String getAddress() {
        return address;
    }

    public String getPhone() {
        return phone;
    }

    public String getNhsNumber() {
        return nhsNumber;
    }

    public String getGpSurgery() {
        return gpSurgery;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setNhsNumber(String nhsNumber) {
        this.nhsNumber = nhsNumber;
    }

    public void setGpSurgery(String gpSurgery) {
        this.gpSurgery = gpSurgery;
    }

    @Override
    public String toString() {
        return patientId + " - " + name;
    }
}
