// src/hms/view/FacilityTableModel.java
package hms.view;

import hms.model.Facility;

import javax.swing.table.AbstractTableModel;
import java.util.List;

public class FacilityTableModel extends AbstractTableModel {

    private final String[] columns = {"ID", "Name", "Address", "Phone"};
    private final List<Facility> data;

    public FacilityTableModel(List<Facility> data) {
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
        Facility f = data.get(rowIndex);
        return switch (columnIndex) {
            case 0 -> f.getFacilityId();
            case 1 -> f.getName();
            case 2 -> f.getAddress();
            case 3 -> f.getPhone();
            default -> "";
        };
    }

    public Facility getAt(int row) {
        return data.get(row);
    }
}
