// src/hms/view/FacilityPanel.java
package hms.view;

import hms.data.FacilityRepository;
import hms.model.Facility;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;

public class FacilityPanel extends JPanel {

    private final FacilityRepository repo;
    private final FacilityTableModel tableModel;
    private final JTable table;

    public FacilityPanel(FacilityRepository repo) {
        this.repo = repo;
        this.tableModel = new FacilityTableModel(repo.getAll());
        this.table = new JTable(tableModel);

        setLayout(new BorderLayout());
        add(new JScrollPane(table), BorderLayout.CENTER);
        add(createButtons(), BorderLayout.SOUTH);
    }

    private JComponent createButtons() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER));

        JButton addBtn = new JButton("Add");
        JButton editBtn = new JButton("Edit");
        JButton delBtn = new JButton("Delete");

        addBtn.addActionListener(e -> onAdd());
        editBtn.addActionListener(e -> onEdit());
        delBtn.addActionListener(e -> onDelete());

        panel.add(addBtn);
        panel.add(editBtn);
        panel.add(delBtn);

        return panel;
    }

    private void onAdd() {
        FacilityForm form = new FacilityForm(null);
        int result = JOptionPane.showConfirmDialog(
                this, form, "Add Facility",
                JOptionPane.OK_CANCEL_OPTION,
                JOptionPane.PLAIN_MESSAGE
        );
        if (result == JOptionPane.OK_OPTION) {
            Facility f = form.toFacility();
            try {
                repo.add(f);
                repo.save();
                tableModel.fireTableDataChanged();
            } catch (IOException ex) {
                showError(ex);
            }
        }
    }

    private void onEdit() {
        int row = table.getSelectedRow();
        if (row < 0) return;

        Facility existing = tableModel.getAt(row);
        FacilityForm form = new FacilityForm(existing);
        int result = JOptionPane.showConfirmDialog(
                this, form, "Edit Facility",
                JOptionPane.OK_CANCEL_OPTION,
                JOptionPane.PLAIN_MESSAGE
        );
        if (result == JOptionPane.OK_OPTION) {
            Facility updated = form.toFacility();
            try {
                // update in list
                repo.getAll().set(row, updated);
                repo.save();
                tableModel.fireTableDataChanged();
            } catch (IOException ex) {
                showError(ex);
            }
        }
    }

    private void onDelete() {
        int row = table.getSelectedRow();
        if (row < 0) return;

        int confirm = JOptionPane.showConfirmDialog(
                this,
                "Delete selected facility?",
                "Confirm",
                JOptionPane.YES_NO_OPTION
        );
        if (confirm == JOptionPane.YES_OPTION) {
            Facility existing = tableModel.getAt(row);
            try {
                repo.remove(existing);
                repo.save();
                tableModel.fireTableDataChanged();
            } catch (IOException ex) {
                showError(ex);
            }
        }
    }

    private void showError(Exception ex) {
        ex.printStackTrace();
        JOptionPane.showMessageDialog(
                this,
                ex.getMessage(),
                "Error",
                JOptionPane.ERROR_MESSAGE
        );
    }

    // Simple inner form panel
    private static class FacilityForm extends JPanel {
        private final JTextField idField = new JTextField(10);
        private final JTextField nameField = new JTextField(20);
        private final JTextField addressField = new JTextField(25);
        private final JTextField phoneField = new JTextField(15);

        public FacilityForm(Facility existing) {
            setLayout(new GridLayout(0, 2, 5, 5));

            add(new JLabel("ID:"));
            add(idField);
            add(new JLabel("Name:"));
            add(nameField);
            add(new JLabel("Address (addr, postcode):"));
            add(addressField);
            add(new JLabel("Phone:"));
            add(phoneField);

            if (existing != null) {
                idField.setText(existing.getFacilityId());
                nameField.setText(existing.getName());
                addressField.setText(existing.getAddress());
                phoneField.setText(existing.getPhone());
            }
        }

        public Facility toFacility() {
            return new Facility(
                    idField.getText().trim(),
                    nameField.getText().trim(),
                    addressField.getText().trim(),
                    phoneField.getText().trim()
            );
        }
    }
}
