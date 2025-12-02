package hms.data;

import hms.model.AdminStaff;
import hms.util.CSVUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * staff.csv columns:
 * 0  staff_id
 * 1  first_name
 * 2  last_name
 * 3  role
 * 4  department
 * 5  facility_id
 * 6  phone_number
 * 7  email
 * 8  employment_status
 * 9  start_date
 * 10 line_manager
 * 11 access_level
 */
public class StaffRepository {

    private final String filePath;
    private final List<AdminStaff> staff = new ArrayList<>();

    public StaffRepository(String filePath) throws IOException {
        this.filePath = filePath;
        load();
    }

    private void load() throws IOException {
        staff.clear();

        List<String[]> rows = CSVUtils.read(filePath);

        if (!rows.isEmpty()) {
            String first = rows.get(0)[0];
            if (first != null && first.trim().equalsIgnoreCase("staff_id")) {
                rows.remove(0);
            }
        }

        for (String[] r : rows) {
            if (r == null || r.length < 8) continue;

            String id    = r[0].trim();
            String fName = r[1].trim();
            String lName = r[2].trim();
            String phone = r[6].trim();
            String email = r[7].trim();

            if (id.isEmpty()) continue;

            String name = (fName + " " + lName).trim();
            AdminStaff a = new AdminStaff(id, name, email, phone, id);
            staff.add(a);
        }
    }

    public List<AdminStaff> getAll() {
        return staff;
    }

    public void add(AdminStaff a) {
        staff.add(a);
    }

    public void remove(AdminStaff a) {
        staff.remove(a);
    }

    public void save() throws IOException {
        List<String[]> rows = new ArrayList<>();

        rows.add(new String[]{
                "staff_id",
                "first_name",
                "last_name",
                "role",
                "department",
                "facility_id",
                "phone_number",
                "email",
                "employment_status",
                "start_date",
                "line_manager",
                "access_level"
        });

        for (AdminStaff a : staff) {
            String firstName = "";
            String lastName = "";
            String name = a.getName() == null ? "" : a.getName().trim();
            String[] parts = name.split("\\s+", 2);
            if (parts.length == 1) {
                firstName = parts[0];
            } else if (parts.length == 2) {
                firstName = parts[0];
                lastName = parts[1];
            }

            rows.add(new String[]{
                    a.getEmployeeId(),  // 0 staff_id
                    firstName,          // 1
                    lastName,           // 2
                    "",                 // 3 role
                    "",                 // 4 department
                    "",                 // 5 facility_id
                    a.getPhone(),       // 6
                    a.getEmail(),       // 7
                    "",                 // 8 employment_status
                    "",                 // 9 start_date
                    "",                 // 10 line_manager
                    ""                  // 11 access_level
            });
        }

        CSVUtils.write(filePath, rows);
    }
}
