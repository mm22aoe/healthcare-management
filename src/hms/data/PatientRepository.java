package hms.data;

import hms.model.Patient;
import hms.util.CSVUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * patients.csv columns:
 * 0  patient_id
 * 1  first_name
 * 2  last_name
 * 3  date_of_birth
 * 4  nhs_number
 * 5  gender
 * 6  phone_number
 * 7  email
 * 8  address
 * 9  postcode
 * 10 emergency_contact_name
 * 11 emergency_contact_phone
 * 12 registration_date
 * 13 gp_surgery_id
 */
public class PatientRepository {

    private final String filePath;
    private final List<Patient> patients = new ArrayList<>();

    public PatientRepository(String filePath) throws IOException {
        this.filePath = filePath;
        load();
    }

    private void load() throws IOException {
        List<String[]> rows = CSVUtils.read(filePath);

        // Skip header row if present
        if (!rows.isEmpty() && "patient_id".equalsIgnoreCase(rows.get(0)[0])) {
            rows.remove(0);
        }

        for (String[] r : rows) {
            if (r.length < 14) continue;

            String id        = r[0];
            String fullName  = r[1] + " " + r[2];
            String dob       = r[3];
            String address   = r[8] + ", " + r[9];
            String phone     = r[6];
            String nhsNumber = r[4];
            String gpSurgery = r[13];

            Patient p = new Patient(id, fullName, dob, address, phone, nhsNumber, gpSurgery);
            patients.add(p);
        }
    }

    public List<Patient> getAll() {
        return patients;
    }

    public void add(Patient p) {
        patients.add(p);
    }

    public void remove(Patient p) {
        patients.remove(p);
    }

    public void save() throws IOException {
        List<String[]> rows = new ArrayList<>();

        // Header
        rows.add(new String[]{
                "patient_id",
                "first_name",
                "last_name",
                "date_of_birth",
                "nhs_number",
                "gender",
                "phone_number",
                "email",
                "address",
                "postcode",
                "emergency_contact_name",
                "emergency_contact_phone",
                "registration_date",
                "gp_surgery_id"
        });

        for (Patient p : patients) {
            // Split name
            String firstName = "";
            String lastName  = "";
            String[] nameParts = p.getName().trim().split("\\s+", 2);
            if (nameParts.length == 1) {
                firstName = nameParts[0];
            } else if (nameParts.length == 2) {
                firstName = nameParts[0];
                lastName  = nameParts[1];
            }

            // Split address
            String address = p.getAddress();
            String postcode = "";
            int idx = address.lastIndexOf(',');
            if (idx >= 0) {
                postcode = address.substring(idx + 1).trim();
                address  = address.substring(0, idx).trim();
            }

            rows.add(new String[]{
                    p.getPatientId(),  // 0 patient_id
                    firstName,         // 1 first_name
                    lastName,          // 2 last_name
                    p.getDob(),        // 3 date_of_birth
                    p.getNhsNumber(),  // 4 nhs_number
                    "",                // 5 gender
                    p.getPhone(),      // 6 phone_number
                    "",                // 7 email
                    address,           // 8 address
                    postcode,          // 9 postcode
                    "",                // 10 emergency_contact_name
                    "",                // 11 emergency_contact_phone
                    "",                // 12 registration_date
                    p.getGpSurgery()   // 13 gp_surgery_id
            });
        }

        CSVUtils.write(filePath, rows);
    }
}
