package hms.model;

public class Prescription {

    private String prescriptionId;
    private String appointmentId;
    private String patientId;
    private String clinicianId;
    private String medication;
    private String dosage;
    private String condition;
    private String pharmacy;

    public Prescription(String prescriptionId, String appointmentId, String patientId,
                        String clinicianId, String medication, String dosage,
                        String condition, String pharmacy) {
        this.prescriptionId = prescriptionId;
        this.appointmentId = appointmentId;
        this.patientId = patientId;
        this.clinicianId = clinicianId;
        this.medication = medication;
        this.dosage = dosage;
        this.condition = condition;
        this.pharmacy = pharmacy;
    }

    public String getPrescriptionId() {
        return prescriptionId;
    }

    public String getAppointmentId() {
        return appointmentId;
    }

    public String getPatientId() {
        return patientId;
    }

    public String getClinicianId() {
        return clinicianId;
    }

    public String getMedication() {
        return medication;
    }

    public String getDosage() {
        return dosage;
    }

    public String getCondition() {
        return condition;
    }

    public String getPharmacy() {
        return pharmacy;
    }

    public void setMedication(String medication) {
        this.medication = medication;
    }

    public void setDosage(String dosage) {
        this.dosage = dosage;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }

    public void setPharmacy(String pharmacy) {
        this.pharmacy = pharmacy;
    }
}
