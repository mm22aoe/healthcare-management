// src/hms/data/ClinicianRepository.java
package hms.data;

import hms.model.ClinicianInfo;
import hms.util.CSVUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ClinicianRepository {

    private final String filePath;
    private final List<ClinicianInfo> clinicians = new ArrayList<>();

    public ClinicianRepository(String filePath) throws IOException {
        this.filePath = filePath;
        load();
    }

    private void load() throws IOException {
        clinicians.clear();
        List<String[]> rows = CSVUtils.read(filePath);

        // Robust header skip: trim + case-insensitive
        if (!rows.isEmpty()) {
            String firstCell = rows.get(0)[0];
            if (firstCell != null && firstCell.trim().equalsIgnoreCase("clinician_id")) {
                rows.remove(0);
            }
        }

        for (String[] r : rows) {
            if (r == null || r.length < 7) continue;

            String id    = r[0].trim();
            String fName = r[1].trim();
            String lName = r[2].trim();
            String title = r[3].trim();
            String spec  = r[4].trim();
            String phone = r[5].trim();
            String email = r[6].trim();

            if (id.isEmpty()) continue;

            String name = (fName + " " + lName).trim();
            clinicians.add(new ClinicianInfo(id, name, title, spec, phone, email));
        }
    }

    public List<ClinicianInfo> getAll() {
        return clinicians;
    }

    public void add(ClinicianInfo c) {
        clinicians.add(c);
    }

    public void remove(ClinicianInfo c) {
        clinicians.remove(c);
    }

    public void save() throws IOException {
        List<String[]> rows = new ArrayList<>();

        // Header exactly as in CSV
        rows.add(new String[]{
            "clinician_id",
            "first_name",
            "last_name",
            "title",
            "speciality",
            "phone_number",
            "email",
            "facility_id",
            "workplace_id",
            "workplace_type",
            "employment_status",
            "start_date"
        });

        for (ClinicianInfo c : clinicians) {
            String firstName = "";
            String lastName  = "";

            String[] parts = c.getName().trim().split("\\s+", 2);
            if (parts.length == 1) {
                firstName = parts[0];
            } else if (parts.length == 2) {
                firstName = parts[0];
                lastName  = parts[1];
            }

            rows.add(new String[]{
                c.getId(),         // clinician_id
                firstName,         // first_name
                lastName,          // last_name
                c.getTitle(),      // title
                c.getSpeciality(), // speciality
                c.getPhone(),      // phone_number
                c.getEmail(),      // email
                "",                // facility_id
                "",                // workplace_id
                "",                // workplace_type
                "",                // employment_status
                ""                 // start_date
            });
        }

        CSVUtils.write(filePath, rows);
    }
}
