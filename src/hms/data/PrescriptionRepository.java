package hms.data;

import hms.model.Prescription;
import hms.util.CSVUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * prescriptions.csv columns:
 * 0  prescription_id
 * 1  patient_id
 * 2  clinician_id
 * 3  appointment_id
 * 4  prescription_date
 * 5  medication_name
 * 6  dosage
 * 7  frequency
 * 8  duration_days
 * 9  quantity
 * 10 instructions
 * 11 pharmacy_name
 * 12 status
 * 13 issue_date
 * 14 collection_date
 */
public class PrescriptionRepository {

    private final String filePath;
    private final List<Prescription> prescriptions = new ArrayList<>();

    public PrescriptionRepository(String filePath) throws IOException {
        this.filePath = filePath;
        load();
    }

    private void load() throws IOException {
        prescriptions.clear(); // Reset in-memory list

        List<String[]> rows = CSVUtils.read(filePath);

        // Skip header row if present
        if (!rows.isEmpty()) {
            String firstCell = rows.get(0)[0];
            if (firstCell != null && firstCell.trim().equalsIgnoreCase("prescription_id")) {
                rows.remove(0);
            }
        }

        for (String[] r : rows) {
            if (r == null || r.length < 12) {
                continue;
            }

            String id            = r[0].trim();
            String patientId     = r[1].trim();
            String clinicianId   = r[2].trim();
            String appointmentId = r[3].trim();
            String medication    = r[5].trim();
            String dosage        = r[6].trim();
            String instructions  = r[10].trim();
            String pharmacy      = r[11].trim();

            if (id.isEmpty()) {
                continue; // skip blank entries
            }

            Prescription p = new Prescription(
                    id,
                    appointmentId,
                    patientId,
                    clinicianId,
                    medication,
                    dosage,
                    instructions,
                    pharmacy
            );

            prescriptions.add(p);
        }
    }

    public List<Prescription> getAll() {
        return prescriptions;
    }

    public void add(Prescription p) {
        prescriptions.add(p);
    }

    public void remove(Prescription p) {
        prescriptions.remove(p);
    }

    public void save() throws IOException {
        List<String[]> rows = new ArrayList<>();

        // Header
        rows.add(new String[]{
                "prescription_id",
                "patient_id",
                "clinician_id",
                "appointment_id",
                "prescription_date",
                "medication_name",
                "dosage",
                "frequency",
                "duration_days",
                "quantity",
                "instructions",
                "pharmacy_name",
                "status",
                "issue_date",
                "collection_date"
        });

        // Data rows
        for (Prescription p : prescriptions) {
            rows.add(new String[]{
                    p.getPrescriptionId(),  // 0
                    p.getPatientId(),       // 1
                    p.getClinicianId(),     // 2
                    p.getAppointmentId(),   // 3
                    "",                     // 4 prescription_date
                    p.getMedication(),      // 5
                    p.getDosage(),          // 6
                    "",                     // 7 frequency
                    "",                     // 8 duration_days
                    "",                     // 9 quantity
                    p.getCondition(),       // 10 instructions
                    p.getPharmacy(),        // 11 pharmacy_name
                    "",                     // 12 status
                    "",                     // 13 issue_date
                    ""                      // 14 collection_date
            });
        }

        CSVUtils.write(filePath, rows);
    }

    public void exportToTextFile(String filename) throws IOException {
        List<String> lines = new ArrayList<>();

        for (Prescription p : prescriptions) {
            lines.add("Prescription ID: " + p.getPrescriptionId());
            lines.add("Patient ID      : " + p.getPatientId());
            lines.add("Clinician ID    : " + p.getClinicianId());
            lines.add("Appointment ID  : " + p.getAppointmentId());
            lines.add("Medication      : " + p.getMedication());
            lines.add("Dosage          : " + p.getDosage());
            lines.add("Pharmacy        : " + p.getPharmacy());
            lines.add("Instructions    : " + p.getCondition());
            lines.add("--------------------------------------------------");
        }

        java.nio.file.Files.write(java.nio.file.Path.of(filename), lines);
    }
}
