package hms.view;

import hms.controller.AppointmentController;
import hms.model.Appointment;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import java.awt.*;
import java.io.IOException;
import java.util.List;

public class AppointmentPanel extends JPanel {

    private final AppointmentController controller;
    private final List<Appointment> appointments;
    private final JTable table;
    private final AppointmentTableModel tableModel;

    public AppointmentPanel(AppointmentController controller) {
        this.controller = controller;
        this.appointments = controller.getAllAppointments();
        this.tableModel = new AppointmentTableModel();
        this.table = new JTable(tableModel);

        setLayout(new BorderLayout());
        add(new JScrollPane(table), BorderLayout.CENTER);
        add(createButtonPanel(), BorderLayout.SOUTH);
    }

    private JPanel createButtonPanel() {
        JPanel panel = new JPanel();
        JButton addBtn = new JButton("Add");
        JButton editBtn = new JButton("Edit");
        JButton deleteBtn = new JButton("Delete");

        addBtn.addActionListener(e -> onAdd());
        editBtn.addActionListener(e -> onEdit());
        deleteBtn.addActionListener(e -> onDelete());

        panel.add(addBtn);
        panel.add(editBtn);
        panel.add(deleteBtn);
        return panel;
    }

    private void onAdd() {
        AppointmentForm form = new AppointmentForm(null);
        int result = JOptionPane.showConfirmDialog(this, form,
                "Add Appointment", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
        if (result == JOptionPane.OK_OPTION) {
            Appointment a = form.toAppointment();
            try {
                controller.addAppointment(a);
                tableModel.fireTableDataChanged();
            } catch (IOException ex) {
                showError(ex);
            }
        }
    }

    private void onEdit() {
        int row = table.getSelectedRow();
        if (row < 0) return;
        Appointment selected = appointments.get(row);
        AppointmentForm form = new AppointmentForm(selected);
        int result = JOptionPane.showConfirmDialog(this, form,
                "Edit Appointment", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
        if (result == JOptionPane.OK_OPTION) {
            form.updateAppointment(selected);
            try {
                controller.updateAppointment();
                tableModel.fireTableDataChanged();
            } catch (IOException ex) {
                showError(ex);
            }
        }
    }

    private void onDelete() {
        int row = table.getSelectedRow();
        if (row < 0) return;
        Appointment selected = appointments.get(row);
        int confirm = JOptionPane.showConfirmDialog(this,
                "Delete appointment " + selected.getAppointmentId() + "?",
                "Confirm", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            try {
                controller.deleteAppointment(selected);
                tableModel.fireTableDataChanged();
            } catch (IOException ex) {
                showError(ex);
            }
        }
    }

    private void showError(Exception e) {
        e.printStackTrace();
        JOptionPane.showMessageDialog(this, e.getMessage(),
                "Error", JOptionPane.ERROR_MESSAGE);
    }

    private class AppointmentTableModel extends AbstractTableModel {

        private final String[] columns = {
                "ID", "Patient ID", "Clinician ID", "Facility ID",
                "Date", "Time", "Status", "Reason"
        };

        @Override
        public int getRowCount() {
            return appointments.size();
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
            Appointment a = appointments.get(rowIndex);
            switch (columnIndex) {
                case 0: return a.getAppointmentId();
                case 1: return a.getPatientId();
                case 2: return a.getClinicianId();
                case 3: return a.getFacilityId();
                case 4: return a.getDate();
                case 5: return a.getTime();
                case 6: return a.getStatus();
                case 7: return a.getReason();
                default: return "";
            }
        }
    }

    private static class AppointmentForm extends JPanel {
        private final JTextField idField = new JTextField(8);
        private final JTextField patientIdField = new JTextField(8);
        private final JTextField clinicianIdField = new JTextField(8);
        private final JTextField facilityIdField = new JTextField(8);
        private final JTextField dateField = new JTextField(10);
        private final JTextField timeField = new JTextField(5);
        private final JTextField statusField = new JTextField(10);
        private final JTextField reasonField = new JTextField(15);

        public AppointmentForm(Appointment existing) {
            setLayout(new GridLayout(0, 2, 5, 5));
            add(new JLabel("ID:")); add(idField);
            add(new JLabel("Patient ID:")); add(patientIdField);
            add(new JLabel("Clinician ID:")); add(clinicianIdField);
            add(new JLabel("Facility ID:")); add(facilityIdField);
            add(new JLabel("Date:")); add(dateField);
            add(new JLabel("Time:")); add(timeField);
            add(new JLabel("Status:")); add(statusField);
            add(new JLabel("Reason:")); add(reasonField);

            if (existing != null) {
                idField.setText(existing.getAppointmentId());
                idField.setEditable(false);
                patientIdField.setText(existing.getPatientId());
                clinicianIdField.setText(existing.getClinicianId());
                facilityIdField.setText(existing.getFacilityId());
                dateField.setText(existing.getDate());
                timeField.setText(existing.getTime());
                statusField.setText(existing.getStatus());
                reasonField.setText(existing.getReason());
            }
        }

        public Appointment toAppointment() {
            return new Appointment(
                    idField.getText().trim(),
                    patientIdField.getText().trim(),
                    clinicianIdField.getText().trim(),
                    facilityIdField.getText().trim(),
                    dateField.getText().trim(),
                    timeField.getText().trim(),
                    statusField.getText().trim(),
                    reasonField.getText().trim()
            );
        }

        public void updateAppointment(Appointment a) {
            a.setDate(dateField.getText().trim());
            a.setTime(timeField.getText().trim());
            a.setStatus(statusField.getText().trim());
            a.setReason(reasonField.getText().trim());
        }
    }
}
