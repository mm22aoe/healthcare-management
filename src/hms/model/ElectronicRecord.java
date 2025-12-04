package hms.model;

public class ElectronicRecord {

    private String recordId;
    private String patientId;
    private String medicalHistory;
    private String allergies;

    public ElectronicRecord(String recordId, String patientId,
                            String medicalHistory, String allergies) {
        this.recordId = recordId;
        this.patientId = patientId;
        this.medicalHistory = medicalHistory;
        this.allergies = allergies;
    }

    public String getRecordId() {
        return recordId;
    }

    public String getPatientId() {
        return patientId;
    }

    public String getMedicalHistory() {
        return medicalHistory;
    }

    public String getAllergies() {
        return allergies;
    }

    public void setMedicalHistory(String medicalHistory) {
        this.medicalHistory = medicalHistory;
    }

    public void setAllergies(String allergies) {
        this.allergies = allergies;
    }
}
