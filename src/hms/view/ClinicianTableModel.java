// src/hms/view/ClinicianTableModel.java
package hms.view;

import hms.model.ClinicianInfo;

import javax.swing.table.AbstractTableModel;
import java.util.List;

public class ClinicianTableModel extends AbstractTableModel {

    private final String[] columns = {
            "ID", "Name", "Title", "Speciality", "Phone", "Email"
    };

    private final List<ClinicianInfo> data;

    public ClinicianTableModel(List<ClinicianInfo> data) {
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
    public String getColumnName(int col) {
        return columns[col];
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        ClinicianInfo c = data.get(rowIndex);

        return switch (columnIndex) {
            case 0 -> c.getId();
            case 1 -> c.getName();
            case 2 -> c.getTitle();
            case 3 -> c.getSpeciality();
            case 4 -> c.getPhone();
            case 5 -> c.getEmail();
            default -> "";
        };
    }

    public ClinicianInfo getAt(int row) {
        return data.get(row);
    }
}
