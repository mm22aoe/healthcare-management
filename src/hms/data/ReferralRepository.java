package hms.data;

import hms.model.Referral;
import hms.util.CSVUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * referrals.csv columns:
 * 0  referral_id
 * 1  patient_id
 * 2  referring_clinician_id
 * 3  referred_to_clinician_id
 * 4  referring_facility_id
 * 5  referred_to_facility_id
 * 6  referral_date
 * 7  urgency_level
 * 8  referral_reason
 * 9  clinical_summary
 * 10 requested_investigations
 * 11 status
 * 12 appointment_id
 * 13 notes
 * 14 created_date
 * 15 last_updated
 */
public class ReferralRepository {

    private final String filePath;
    private final List<Referral> referrals = new ArrayList<>();

    public ReferralRepository(String filePath) throws IOException {
        this.filePath = filePath;
        load();
    }

    /**
     * Loads referral data from CSV safely.
     */
    private void load() throws IOException {
        referrals.clear();

        List<String[]> rows = CSVUtils.read(filePath);

        // Skip header if present
        if (!rows.isEmpty()) {
            String first = rows.get(0)[0];
            if (first != null && first.trim().equalsIgnoreCase("referral_id")) {
                rows.remove(0);
            }
        }

        for (String[] r : rows) {
            if (r == null || r.length == 0) continue;

            String id        = cell(r, 0);
            String patientId = cell(r, 1);
            String fromClin  = cell(r, 2);
            String toClin    = cell(r, 3);

            String urgency   = cell(r, 7);
            String reason    = cell(r, 8);
            String clinical  = cell(r, 9);
            String status    = cell(r, 11);
            String notes     = cell(r, 13);

            if (id.isEmpty()) continue;

            // Combined summary text
            String summary = (reason + " | " + clinical + " | " + notes).trim();

            Referral ref = new Referral(
                    id,
                    patientId,
                    fromClin,
                    toClin,
                    urgency,
                    status,
                    summary
            );

            referrals.add(ref);
        }
    }

    /**
     * Safe accessor: avoids IndexOutOfBounds and null.
     */
    private String cell(String[] row, int index) {
        if (row == null) return "";
        if (index < 0 || index >= row.length) return "";
        return row[index] == null ? "" : row[index].trim();
    }

    public List<Referral> getAll() {
        return referrals;
    }

    /**
     * Saves referral list back to CSV in original 16-column format.
     */
    public void save() throws IOException {
        List<String[]> rows = new ArrayList<>();

        rows.add(new String[]{
                "referral_id",
                "patient_id",
                "referring_clinician_id",
                "referred_to_clinician_id",
                "referring_facility_id",
                "referred_to_facility_id",
                "referral_date",
                "urgency_level",
                "referral_reason",
                "clinical_summary",
                "requested_investigations",
                "status",
                "appointment_id",
                "notes",
                "created_date",
                "last_updated"
        });

        for (Referral r : referrals) {
            rows.add(new String[]{
                    r.getReferralId(),       // 0
                    r.getPatientId(),        // 1
                    r.getFromClinicianId(),  // 2
                    r.getToSpecialty(),      // 3
                    "",                      // 4 referring_facility_id
                    "",                      // 5 referred_to_facility_id
                    "",                      // 6 referral_date
                    r.getUrgency(),          // 7
                    r.getSummary(),          // 8 merged reason+clinical+notes
                    "",                      // 9 clinical_summary
                    "",                      // 10 requested_investigations
                    r.getStatus(),           // 11
                    "",                      // 12 appointment_id
                    "",                      // 13 notes
                    "",                      // 14 created_date
                    ""                       // 15 last_updated
            });
        }

        CSVUtils.write(filePath, rows);
    }

    public void exportToTextFile(String filename) throws IOException {
        List<String> lines = new ArrayList<>();

        for (Referral r : referrals) {
            lines.add("Referral ID     : " + r.getReferralId());
            lines.add("Patient ID      : " + r.getPatientId());
            lines.add("From Clinician  : " + r.getFromClinicianId());
            lines.add("To Specialty    : " + r.getToSpecialty());
            lines.add("Urgency         : " + r.getUrgency());
            lines.add("Status          : " + r.getStatus());
            lines.add("Summary         : " + r.getSummary());
            lines.add("--------------------------------------------------");
        }

        java.nio.file.Files.write(java.nio.file.Path.of(filename), lines);
    }
}
