package hms.data;

import hms.model.Facility;
import hms.util.CSVUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * facilities.csv columns:
 * 0  facility_id
 * 1  facility_name
 * 2  facility_type
 * 3  address
 * 4  postcode
 * 5  phone_number
 * 6  email
 * 7  opening_hours
 * 8  manager_name
 * 9  capacity
 * 10 specialities_offered
 */
public class FacilityRepository {

    private final String filePath;
    private final List<Facility> facilities = new ArrayList<>();

    public FacilityRepository(String filePath) throws IOException {
        this.filePath = filePath;
        load();
    }

    private void load() throws IOException {
        facilities.clear();

        List<String[]> rows = CSVUtils.read(filePath);

        // Robust header skip
        if (!rows.isEmpty()) {
            String first = rows.get(0)[0];
            if (first != null && first.trim().equalsIgnoreCase("facility_id")) {
                rows.remove(0);
            }
        }

        for (String[] r : rows) {
            if (r == null || r.length < 6) continue;

            String id      = r[0].trim();
            String name    = r[1].trim();
            String address = r[3].trim();
            String postcode= r[4].trim();
            String phone   = r[5].trim();

            if (id.isEmpty()) continue;

            String fullAddress = address.isEmpty() ? postcode : address + ", " + postcode;
            facilities.add(new Facility(id, name, fullAddress, phone));
        }
    }

    public List<Facility> getAll() {
        return facilities;
    }

    public void add(Facility f) {
        facilities.add(f);
    }

    public void remove(Facility f) {
        facilities.remove(f);
    }

    public void save() throws IOException {
        List<String[]> rows = new ArrayList<>();

        rows.add(new String[]{
                "facility_id",
                "facility_name",
                "facility_type",
                "address",
                "postcode",
                "phone_number",
                "email",
                "opening_hours",
                "manager_name",
                "capacity",
                "specialities_offered"
        });

        for (Facility f : facilities) {
            // split "address, postcode" back into two fields
            String addr = f.getAddress() == null ? "" : f.getAddress().trim();
            String address = addr;
            String postcode = "";
            int idx = addr.lastIndexOf(',');
            if (idx >= 0) {
                address = addr.substring(0, idx).trim();
                postcode = addr.substring(idx + 1).trim();
            }

            rows.add(new String[]{
                    f.getFacilityId(),   // 0
                    f.getName(),         // 1
                    "",                  // 2 facility_type
                    address,             // 3
                    postcode,            // 4
                    f.getPhone(),        // 5
                    "",                  // 6 email
                    "",                  // 7 opening_hours
                    "",                  // 8 manager_name
                    "",                  // 9 capacity
                    ""                   // 10 specialities_offered
            });
        }

        CSVUtils.write(filePath, rows);
    }
}
