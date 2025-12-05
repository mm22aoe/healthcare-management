// src/hms/view/StaffTableModel.java
package hms.view;

import hms.model.AdminStaff;

import javax.swing.table.AbstractTableModel;
import java.util.List;

public class StaffTableModel extends AbstractTableModel {

    private final String[] columns = {"ID", "Name", "Email", "Phone"};
    private final List<AdminStaff> data;

    public StaffTableModel(List<AdminStaff> data) {
        this.data = data;
    }

    @Override
    public int getRowCount() {
        return data.size();
    }

    @Override
    public int getColumnCount() {
        return columns.length;
    }

    @Override
    public String getColumnName(int column) {
        return columns[column];
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        AdminStaff s = data.get(rowIndex);
        return switch (columnIndex) {
            case 0 -> s.getEmployeeId();
            case 1 -> s.getName();
            case 2 -> s.getEmail();
            case 3 -> s.getPhone();
            default -> "";
        };
    }

    public AdminStaff getAt(int row) {
        return data.get(row);
    }
}
