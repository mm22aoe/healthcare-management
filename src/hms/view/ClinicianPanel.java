// src/hms/view/ClinicianPanel.java
package hms.view;

import hms.controller.ClinicianController;
import hms.model.ClinicianInfo;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;

public class ClinicianPanel extends JPanel {

    private final ClinicianController controller;
    private final ClinicianTableModel tableModel;
    private final JTable table;

    public ClinicianPanel(ClinicianController controller) {
        this.controller = controller;
        this.tableModel = new ClinicianTableModel(controller.getAllClinicians());
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
        ClinicianFormPanel form = new ClinicianFormPanel();
        int result = JOptionPane.showConfirmDialog(
                this, form, "Add Clinician",
                JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE
        );

        if (result == JOptionPane.OK_OPTION) {
            ClinicianInfo c = form.toClinician();
            try {
                controller.addClinician(c);
                tableModel.fireTableDataChanged();
            } catch (IOException ex) {
                showError(ex);
            }
        }
    }

    private void onEdit() {
        int row = table.getSelectedRow();
        if (row < 0) return;

        ClinicianInfo existing = tableModel.getAt(row);
        ClinicianFormPanel form = new ClinicianFormPanel(existing);

        int result = JOptionPane.showConfirmDialog(
                this, form, "Edit Clinician",
                JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE
        );

        if (result == JOptionPane.OK_OPTION) {
            ClinicianInfo updated = form.toClinician();
            try {
                controller.updateClinician(row, updated);
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
                this, "Delete selected clinician?", "Confirm",
                JOptionPane.YES_NO_OPTION
        );

        if (confirm == JOptionPane.YES_OPTION) {
            try {
                controller.deleteClinician(row);
                tableModel.fireTableDataChanged();
            } catch (IOException ex) {
                showError(ex);
            }
        }
    }

    private void showError(Exception ex) {
        ex.printStackTrace();
        JOptionPane.showMessageDialog(
                this, ex.getMessage(), "Error",
                JOptionPane.ERROR_MESSAGE
        );
    }
}
