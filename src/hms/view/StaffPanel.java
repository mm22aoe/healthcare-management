// src/hms/view/StaffPanel.java
package hms.view;

import hms.data.StaffRepository;
import hms.model.AdminStaff;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;

public class StaffPanel extends JPanel {

    private final StaffRepository repo;
    private final StaffTableModel tableModel;
    private final JTable table;

    public StaffPanel(StaffRepository repo) {
        this.repo = repo;
        this.tableModel = new StaffTableModel(repo.getAll());
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
        StaffForm form = new StaffForm(null);
        int result = JOptionPane.showConfirmDialog(
                this, form, "Add Staff",
                JOptionPane.OK_CANCEL_OPTION,
                JOptionPane.PLAIN_MESSAGE
        );
        if (result == JOptionPane.OK_OPTION) {
            AdminStaff a = form.toStaff();
            try {
                repo.add(a);
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

        AdminStaff existing = tableModel.getAt(row);
        StaffForm form = new StaffForm(existing);
        int result = JOptionPane.showConfirmDialog(
                this, form, "Edit Staff",
                JOptionPane.OK_CANCEL_OPTION,
                JOptionPane.PLAIN_MESSAGE
        );
        if (result == JOptionPane.OK_OPTION) {
            AdminStaff updated = form.toStaff();
            try {
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
                "Delete selected staff?",
                "Confirm",
                JOptionPane.YES_NO_OPTION
        );
        if (confirm == JOptionPane.YES_OPTION) {
            AdminStaff existing = tableModel.getAt(row);
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

    // simple inner form
    private static class StaffForm extends JPanel {
        private final JTextField idField = new JTextField(10);
        private final JTextField nameField = new JTextField(20);
        private final JTextField emailField = new JTextField(20);
        private final JTextField phoneField = new JTextField(15);

        public StaffForm(AdminStaff existing) {
            setLayout(new GridLayout(0, 2, 5, 5));

            add(new JLabel("ID:"));
            add(idField);
            add(new JLabel("Name:"));
            add(nameField);
            add(new JLabel("Email:"));
            add(emailField);
            add(new JLabel("Phone:"));
            add(phoneField);

            if (existing != null) {
                idField.setText(existing.getEmployeeId());
                nameField.setText(existing.getName());
                emailField.setText(existing.getEmail());
                phoneField.setText(existing.getPhone());
            }
        }

        public AdminStaff toStaff() {
            String id = idField.getText().trim();
            String name = nameField.getText().trim();
            String email = emailField.getText().trim();
            String phone = phoneField.getText().trim();
            return new AdminStaff(id, name, email, phone, id);
        }
    }
}
