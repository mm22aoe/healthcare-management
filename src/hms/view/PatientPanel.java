package hms.view;

import hms.controller.PatientController;
import hms.model.Patient;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import java.awt.*;
import java.io.IOException;
import java.util.List;

public class PatientPanel extends JPanel {

    private final PatientController controller;
    private final List<Patient> patients;
    private final JTable table;
    private final PatientTableModel tableModel;

    public PatientPanel(PatientController controller) {
        this.controller = controller;
        this.patients = controller.getAllPatients();
        this.tableModel = new PatientTableModel();
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
        PatientForm form = new PatientForm(null);
        int result = JOptionPane.showConfirmDialog(this, form,
                "Add Patient", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
        if (result == JOptionPane.OK_OPTION) {
            Patient p = form.toPatient();
            try {
                controller.addPatient(p);
                tableModel.fireTableDataChanged();
            } catch (IOException ex) {
                showError(ex);
            }
        }
    }

    private void onEdit() {
        int row = table.getSelectedRow();
        if (row < 0) return;

        Patient selected = patients.get(row);
        PatientForm form = new PatientForm(selected);
        int result = JOptionPane.showConfirmDialog(this, form,
                "Edit Patient", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
        if (result == JOptionPane.OK_OPTION) {
            form.updatePatient(selected);
            try {
                controller.updatePatient();
                tableModel.fireTableDataChanged();
            } catch (IOException ex) {
                showError(ex);
            }
        }
    }

    private void onDelete() {
        int row = table.getSelectedRow();
        if (row < 0) return;
        Patient selected = patients.get(row);
        int confirm = JOptionPane.showConfirmDialog(this,
                "Delete patient " + selected.getName() + "?",
                "Confirm", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            try {
                controller.deletePatient(selected);
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

    private class PatientTableModel extends AbstractTableModel {

        private final String[] columns = {
                "ID", "Name", "DOB", "Address", "Phone", "NHS No", "GP Surgery"
        };

        @Override
        public int getRowCount() {
            return patients.size();
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
            Patient p = patients.get(rowIndex);
            switch (columnIndex) {
                case 0: return p.getPatientId();
                case 1: return p.getName();
                case 2: return p.getDob();
                case 3: return p.getAddress();
                case 4: return p.getPhone();
                case 5: return p.getNhsNumber();
                case 6: return p.getGpSurgery();
                default: return "";
            }
        }
    }

    /**
     * Simple form panel used inside dialogs
     */
    private static class PatientForm extends JPanel {
        private final JTextField idField = new JTextField(10);
        private final JTextField nameField = new JTextField(15);
        private final JTextField dobField = new JTextField(10);
        private final JTextField addressField = new JTextField(20);
        private final JTextField phoneField = new JTextField(10);
        private final JTextField nhsField = new JTextField(10);
        private final JTextField gpField = new JTextField(15);

        public PatientForm(Patient existing) {
            setLayout(new GridLayout(0, 2, 5, 5));
            add(new JLabel("ID:")); add(idField);
            add(new JLabel("Name:")); add(nameField);
            add(new JLabel("DOB:")); add(dobField);
            add(new JLabel("Address:")); add(addressField);
            add(new JLabel("Phone:")); add(phoneField);
            add(new JLabel("NHS No:")); add(nhsField);
            add(new JLabel("GP Surgery:")); add(gpField);

            if (existing != null) {
                idField.setText(existing.getPatientId());
                idField.setEditable(false);
                nameField.setText(existing.getName());
                dobField.setText(existing.getDob());
                addressField.setText(existing.getAddress());
                phoneField.setText(existing.getPhone());
                nhsField.setText(existing.getNhsNumber());
                gpField.setText(existing.getGpSurgery());
            }
        }

        public Patient toPatient() {
            return new Patient(
                    idField.getText().trim(),
                    nameField.getText().trim(),
                    dobField.getText().trim(),
                    addressField.getText().trim(),
                    phoneField.getText().trim(),
                    nhsField.getText().trim(),
                    gpField.getText().trim()
            );
        }

        public void updatePatient(Patient p) {
            p.setName(nameField.getText().trim());
            p.setDob(dobField.getText().trim());
            p.setAddress(addressField.getText().trim());
            p.setPhone(phoneField.getText().trim());
            p.setNhsNumber(nhsField.getText().trim());
            p.setGpSurgery(gpField.getText().trim());
        }
    }
}
