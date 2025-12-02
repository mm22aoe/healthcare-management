package hms.data;

import hms.model.Appointment;
import hms.util.CSVUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * appointments.csv columns:
 * 0  appointment_id
 * 1  patient_id
 * 2  clinician_id
 * 3  facility_id
 * 4  appointment_date
 * 5  appointment_time
 * 6  duration_minutes
 * 7  appointment_type
 * 8  status
 * 9  reason_for_visit
 * 10 notes
 * 11 created_date
 * 12 last_modified
 */
public class AppointmentRepository {

    private final String filePath;
    private final List<Appointment> appointments = new ArrayList<>();

    public AppointmentRepository(String filePath) throws IOException {
        this.filePath = filePath;
        load();
    }

    private void load() throws IOException {
        appointments.clear(); // Clear in-memory list before loading

        List<String[]> rows = CSVUtils.read(filePath);

        // Remove header row if detected
        if (!rows.isEmpty()) {
            String firstCell = rows.get(0)[0];
            if (firstCell != null && firstCell.trim().equalsIgnoreCase("appointment_id")) {
                rows.remove(0);
            }
        }

        for (String[] r : rows) {
            if (r == null || r.length < 10) {
                continue;
            }

            String id          = r[0].trim();
            String patientId   = r[1].trim();
            String clinicianId = r[2].trim();
            String facilityId  = r[3].trim();
            String date        = r[4].trim();
            String time        = r[5].trim();
            String status      = r[8].trim();
            String reason      = r[9].trim();

            if (id.isEmpty()) {
                continue; // Skip blank rows
            }

            Appointment a = new Appointment(
                id,
                patientId,
                clinicianId,
                facilityId,
                date,
                time,
                status,
                reason
            );

            appointments.add(a);
        }
    }

    public List<Appointment> getAll() {
        return appointments;
    }

    public void add(Appointment appointment) {
        appointments.add(appointment);
    }

    public void remove(Appointment appointment) {
        appointments.remove(appointment);
    }

    public void save() throws IOException {
        List<String[]> rows = new ArrayList<>();

        // Header row
        rows.add(new String[] {
                "appointment_id",
                "patient_id",
                "clinician_id",
                "facility_id",
                "appointment_date",
                "appointment_time",
                "duration_minutes",
                "appointment_type",
                "status",
                "reason_for_visit",
                "notes",
                "created_date",
                "last_modified"
        });

        // Write appointment rows
        for (Appointment a : appointments) {
            rows.add(new String[] {
                    a.getAppointmentId(), // 0
                    a.getPatientId(),     // 1
                    a.getClinicianId(),   // 2
                    a.getFacilityId(),    // 3
                    a.getDate(),          // 4
                    a.getTime(),          // 5
                    "",                   // 6 duration_minutes
                    "",                   // 7 appointment_type
                    a.getStatus(),        // 8
                    a.getReason(),        // 9
                    "",                   // 10 notes
                    "",                   // 11 created_date
                    ""                    // 12 last_modified
            });
        }

        CSVUtils.write(filePath, rows);
    }
}
