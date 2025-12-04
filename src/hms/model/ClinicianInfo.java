// src/hms/model/ClinicianInfo.java
package hms.model;

public class ClinicianInfo {

    private String id;
    private String name;
    private String title;
    private String speciality;
    private String phone;
    private String email;

    public ClinicianInfo(String id, String name, String title,
                         String speciality, String phone, String email) {
        this.id = id;
        this.name = name;
        this.title = title;
        this.speciality = speciality;
        this.phone = phone;
        this.email = email;
    }

    // Getters and Setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSpeciality() {
        return speciality;
    }

    public void setSpeciality(String speciality) {
        this.speciality = speciality;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
