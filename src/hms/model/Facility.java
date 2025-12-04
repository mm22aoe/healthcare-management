package hms.model;

public class Facility {
    private String facilityId;
    private String name;
    private String address;
    private String phone;

    public Facility(String facilityId, String name, String address, String phone) {
        this.facilityId = facilityId;
        this.name = name;
        this.address = address;
        this.phone = phone;
    }

    public String getFacilityId() {
        return facilityId;
    }

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    public String getPhone() {
        return phone;
    }

    @Override
    public String toString() {
        return name + " (" + facilityId + ")";
    }
}
