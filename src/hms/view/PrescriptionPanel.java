package hms.view;

import hms.controller.PrescriptionController;
import hms.model.Prescription;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import java.awt.*;
import java.io.IOException;
import java.util.List;

public class PrescriptionPanel extends JPanel {

    private final PrescriptionController controller;
    private final List<Prescription> prescriptions;
    private final JTable table;
    private final PrescriptionTableModel tableModel;

    public PrescriptionPanel(PrescriptionController controller) {
        this.controller = controller;
        this.prescriptions = controller.getAllPrescriptions();
        this.tableModel = new PrescriptionTableModel();
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
        PrescriptionForm form = new PrescriptionForm(null);
        int result = JOptionPane.showConfirmDialog(this, form,
                "Add Prescription", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
        if (result == JOptionPane.OK_OPTION) {
            Prescription p = form.toPrescription();
            try {
                controller.addPrescription(p);
                tableModel.fireTableDataChanged();
            } catch (IOException ex) {
                showError(ex);
            }
        }
    }

    private void onEdit() {
        int row = table.getSelectedRow();
        if (row < 0) return;
        Prescription selected = prescriptions.get(row);
        PrescriptionForm form = new PrescriptionForm(selected);
        int result = JOptionPane.showConfirmDialog(this, form,
                "Edit Prescription", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
        if (result == JOptionPane.OK_OPTION) {
            form.updatePrescription(selected);
            try {
                controller.updatePrescription();
                tableModel.fireTableDataChanged();
            } catch (IOException ex) {
                showError(ex);
            }
        }
    }

    private void onDelete() {
        int row = table.getSelectedRow();
        if (row < 0) return;
        Prescription selected = prescriptions.get(row);
        int confirm = JOptionPane.showConfirmDialog(this,
                "Delete prescription " + selected.getPrescriptionId() + "?",
                "Confirm", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            try {
                controller.deletePrescription(selected);
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

    private class PrescriptionTableModel extends AbstractTableModel {

        private final String[] columns = {
                "ID", "Appointment ID", "Patient ID", "Clinician ID",
                "Medication", "Dosage", "Condition", "Pharmacy"
        };

        @Override
        public int getRowCount() {
            return prescriptions.size();
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
            Prescription p = prescriptions.get(rowIndex);
            switch (columnIndex) {
                case 0: return p.getPrescriptionId();
                case 1: return p.getAppointmentId();
                case 2: return p.getPatientId();
                case 3: return p.getClinicianId();
                case 4: return p.getMedication();
                case 5: return p.getDosage();
                case 6: return p.getCondition();
                case 7: return p.getPharmacy();
                default: return "";
            }
        }
    }

    private static class PrescriptionForm extends JPanel {

        private final JTextField idField = new JTextField(8);
        private final JTextField appointmentIdField = new JTextField(8);
        private final JTextField patientIdField = new JTextField(8);
        private final JTextField clinicianIdField = new JTextField(8);
        private final JTextField medicationField = new JTextField(15);
        private final JTextField dosageField = new JTextField(10);
        private final JTextField conditionField = new JTextField(15);
        private final JTextField pharmacyField = new JTextField(10);

        public PrescriptionForm(Prescription existing) {
            setLayout(new GridLayout(0, 2, 5, 5));
            add(new JLabel("ID:")); add(idField);
            add(new JLabel("Appointment ID:")); add(appointmentIdField);
            add(new JLabel("Patient ID:")); add(patientIdField);
            add(new JLabel("Clinician ID:")); add(clinicianIdField);
            add(new JLabel("Medication:")); add(medicationField);
            add(new JLabel("Dosage:")); add(dosageField);
            add(new JLabel("Condition:")); add(conditionField);
            add(new JLabel("Pharmacy:")); add(pharmacyField);

            if (existing != null) {
                idField.setText(existing.getPrescriptionId());
                idField.setEditable(false);
                appointmentIdField.setText(existing.getAppointmentId());
                patientIdField.setText(existing.getPatientId());
                clinicianIdField.setText(existing.getClinicianId());
                medicationField.setText(existing.getMedication());
                dosageField.setText(existing.getDosage());
                conditionField.setText(existing.getCondition());
                pharmacyField.setText(existing.getPharmacy());
            }
        }

        public Prescription toPrescription() {
            return new Prescription(
                    idField.getText().trim(),
                    appointmentIdField.getText().trim(),
                    patientIdField.getText().trim(),
                    clinicianIdField.getText().trim(),
                    medicationField.getText().trim(),
                    dosageField.getText().trim(),
                    conditionField.getText().trim(),
                    pharmacyField.getText().trim()
            );
        }

        public void updatePrescription(Prescription p) {
            p.setMedication(medicationField.getText().trim());
            p.setDosage(dosageField.getText().trim());
            p.setCondition(conditionField.getText().trim());
            p.setPharmacy(pharmacyField.getText().trim());
        }
    }
}
