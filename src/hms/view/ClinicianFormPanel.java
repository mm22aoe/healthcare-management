// src/hms/view/ClinicianFormPanel.java
package hms.view;

import hms.model.ClinicianInfo;

import javax.swing.*;
import java.awt.*;

public class ClinicianFormPanel extends JPanel {

    private final JTextField idField    = new JTextField(10);
    private final JTextField nameField  = new JTextField(20);
    private final JTextField titleField = new JTextField(10);
    private final JTextField specField  = new JTextField(15);
    private final JTextField phoneField = new JTextField(15);
    private final JTextField emailField = new JTextField(20);

    public ClinicianFormPanel() {
        this(null);
    }

    public ClinicianFormPanel(ClinicianInfo existing) {
        setLayout(new GridLayout(0, 2, 5, 5));

        add(new JLabel("ID:"));
        add(idField);

        add(new JLabel("Name:"));
        add(nameField);

        add(new JLabel("Title:"));
        add(titleField);

        add(new JLabel("Speciality:"));
        add(specField);

        add(new JLabel("Phone:"));
        add(phoneField);

        add(new JLabel("Email:"));
        add(emailField);

        if (existing != null) {
            idField.setText(existing.getId());
            nameField.setText(existing.getName());
            titleField.setText(existing.getTitle());
            specField.setText(existing.getSpeciality());
            phoneField.setText(existing.getPhone());
            emailField.setText(existing.getEmail());
        }
    }

    public ClinicianInfo toClinician() {
        return new ClinicianInfo(
                idField.getText().trim(),
                nameField.getText().trim(),
                titleField.getText().trim(),
                specField.getText().trim(),
                phoneField.getText().trim(),
                emailField.getText().trim()
        );
    }
}
