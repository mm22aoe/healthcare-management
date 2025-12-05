package hms.view;

import hms.controller.ReferralController;
import hms.model.Referral;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import java.awt.*;
import java.io.IOException;
import java.util.List;

public class ReferralPanel extends JPanel {

    private final ReferralController controller;
    private final List<Referral> referrals;
    private final JTable table;
    private final ReferralTableModel tableModel;

    public ReferralPanel(ReferralController controller) {
        this.controller = controller;
        this.referrals = controller.getAllReferrals();
        this.tableModel = new ReferralTableModel();
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
        ReferralForm form = new ReferralForm(null);
        int result = JOptionPane.showConfirmDialog(this, form,
                "Add Referral", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
        if (result == JOptionPane.OK_OPTION) {
            Referral r = form.toReferral();
            try {
                controller.addReferral(r);
                tableModel.fireTableDataChanged();
            } catch (IOException ex) {
                showError(ex);
            }
        }
    }

    private void onEdit() {
        int row = table.getSelectedRow();
        if (row < 0) return;
        Referral selected = referrals.get(row);
        ReferralForm form = new ReferralForm(selected);
        int result = JOptionPane.showConfirmDialog(this, form,
                "Edit Referral", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
        if (result == JOptionPane.OK_OPTION) {
            form.updateReferral(selected);
            try {
                controller.updateReferral(selected);
                tableModel.fireTableDataChanged();
            } catch (IOException ex) {
                showError(ex);
            }
        }
    }

    private void onDelete() {
        int row = table.getSelectedRow();
        if (row < 0) return;
        Referral selected = referrals.get(row);
        int confirm = JOptionPane.showConfirmDialog(this,
                "Delete referral " + selected.getReferralId() + "?",
                "Confirm", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            try {
                controller.deleteReferral(selected);
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

    private class ReferralTableModel extends AbstractTableModel {

        private final String[] columns = {
                "ID", "Patient ID", "From Clinician", "Specialty",
                "Urgency", "Status", "Summary"
        };

        @Override
        public int getRowCount() {
            return referrals.size();
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
            Referral r = referrals.get(rowIndex);
            switch (columnIndex) {
                case 0: return r.getReferralId();
                case 1: return r.getPatientId();
                case 2: return r.getFromClinicianId();
                case 3: return r.getToSpecialty();
                case 4: return r.getUrgency();
                case 5: return r.getStatus();
                case 6: return r.getSummary();
                default: return "";
            }
        }
    }

    private static class ReferralForm extends JPanel {

        private final JTextField idField = new JTextField(8);
        private final JTextField patientIdField = new JTextField(8);
        private final JTextField fromClinicianField = new JTextField(8);
        private final JTextField specialtyField = new JTextField(10);
        private final JTextField urgencyField = new JTextField(10);
        private final JTextField statusField = new JTextField(10);
        private final JTextArea summaryArea = new JTextArea(4, 20);

        public ReferralForm(Referral existing) {
            setLayout(new BorderLayout());

            JPanel fields = new JPanel(new GridLayout(0, 2, 5, 5));
            fields.add(new JLabel("ID:")); fields.add(idField);
            fields.add(new JLabel("Patient ID:")); fields.add(patientIdField);
            fields.add(new JLabel("From Clinician ID:")); fields.add(fromClinicianField);
            fields.add(new JLabel("Specialty:")); fields.add(specialtyField);
            fields.add(new JLabel("Urgency:")); fields.add(urgencyField);
            fields.add(new JLabel("Status:")); fields.add(statusField);

            add(fields, BorderLayout.NORTH);
            add(new JScrollPane(summaryArea), BorderLayout.CENTER);
            summaryArea.setBorder(BorderFactory.createTitledBorder("Summary"));

            if (existing != null) {
                idField.setText(existing.getReferralId());
                idField.setEditable(false);
                patientIdField.setText(existing.getPatientId());
                fromClinicianField.setText(existing.getFromClinicianId());
                specialtyField.setText(existing.getToSpecialty());
                urgencyField.setText(existing.getUrgency());
                statusField.setText(existing.getStatus());
                summaryArea.setText(existing.getSummary());
            }
        }

        public Referral toReferral() {
            return new Referral(
                    idField.getText().trim(),
                    patientIdField.getText().trim(),
                    fromClinicianField.getText().trim(),
                    specialtyField.getText().trim(),
                    urgencyField.getText().trim(),
                    statusField.getText().trim(),
                    summaryArea.getText().trim()
            );
        }

        public void updateReferral(Referral r) {
            r.setUrgency(urgencyField.getText().trim());
            r.setStatus(statusField.getText().trim());
            r.setSummary(summaryArea.getText().trim());
        }
    }
}
