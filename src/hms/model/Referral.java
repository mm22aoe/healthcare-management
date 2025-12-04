package hms.model;

public class Referral {

    private String referralId;
    private String patientId;
    private String fromClinicianId;
    private String toSpecialty;
    private String urgency;
    private String status;
    private String summary;

    public Referral(String referralId, String patientId, String fromClinicianId,
                    String toSpecialty, String urgency, String status,
                    String summary) {
        this.referralId = referralId;
        this.patientId = patientId;
        this.fromClinicianId = fromClinicianId;
        this.toSpecialty = toSpecialty;
        this.urgency = urgency;
        this.status = status;
        this.summary = summary;
    }

    public String getReferralId() {
        return referralId;
    }

    public String getPatientId() {
        return patientId;
    }

    public String getFromClinicianId() {
        return fromClinicianId;
    }

    public String getToSpecialty() {
        return toSpecialty;
    }

    public String getUrgency() {
        return urgency;
    }

    public String getStatus() {
        return status;
    }

    public String getSummary() {
        return summary;
    }

    public void setUrgency(String urgency) {
        this.urgency = urgency;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }
}
