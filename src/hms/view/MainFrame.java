// src/hms/view/MainFrame.java
package hms.view;

import hms.controller.*;
import hms.data.*;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;

public class MainFrame extends JFrame {

    private final PatientController patientController;
    private final ClinicianController clinicianController;
    private final AppointmentController appointmentController;
    private final PrescriptionController prescriptionController;
    private final ReferralController referralController;

    // Repos for read-only tabs (facilities/staff) and exports if needed
    private final FacilityRepository facilityRepo;
    private final StaffRepository staffRepo;

    public MainFrame(PatientRepository patientRepo,
                     ClinicianRepository clinicianRepo,
                     FacilityRepository facilityRepo,
                     AppointmentRepository appointmentRepo,
                     PrescriptionRepository prescriptionRepo,
                     ReferralRepository referralRepo,
                     StaffRepository staffRepo) {

        super("Healthcare Management System");

        this.patientController = new PatientController(patientRepo);
        this.clinicianController = new ClinicianController(clinicianRepo);
        this.appointmentController = new AppointmentController(appointmentRepo);
        this.prescriptionController = new PrescriptionController(prescriptionRepo);
        this.referralController = new ReferralController(referralRepo);
        this.facilityRepo = facilityRepo;
        this.staffRepo = staffRepo;

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1000, 600);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        setJMenuBar(buildMenuBar());

        JTabbedPane tabs = new JTabbedPane();
        tabs.addTab("Patients", new PatientPanel(patientController));
        tabs.addTab("Clinicians", new ClinicianPanel(clinicianController));
        tabs.addTab("Appointments", new AppointmentPanel(appointmentController));
        tabs.addTab("Prescriptions", new PrescriptionPanel(prescriptionController));
        tabs.addTab("Referrals", new ReferralPanel(referralController));
        tabs.addTab("Facilities", new FacilityPanel(facilityRepo));
        tabs.addTab("Staff", new StaffPanel(staffRepo));

        add(tabs, BorderLayout.CENTER);
    }

    private JMenuBar buildMenuBar() {
        JMenuBar menuBar = new JMenuBar();

        JMenu fileMenu = new JMenu("Export Reports");

        JMenuItem exportPrescriptions = new JMenuItem("Export Prescriptions");
        JMenuItem exportReferrals = new JMenuItem("Export Referrals");
        JMenuItem exitItem = new JMenuItem("Exit");

        exportPrescriptions.addActionListener(e -> onExportPrescriptions());
        exportReferrals.addActionListener(e -> onExportReferrals());
        exitItem.addActionListener(e -> dispose());

        fileMenu.add(exportPrescriptions);
        fileMenu.add(exportReferrals);
        fileMenu.addSeparator();
        fileMenu.add(exitItem);

        menuBar.add(fileMenu);
        return menuBar;
    }

    private void onExportPrescriptions() {
        try {
            // export to a simple text file in project root
            prescriptionController.exportToTextFile("prescriptions_report.txt");
            JOptionPane.showMessageDialog(this,
                    "Prescriptions exported to prescriptions_report.txt",
                    "Export Successful",
                    JOptionPane.INFORMATION_MESSAGE);
        } catch (IOException ex) {
            showError(ex);
        }
    }

    private void onExportReferrals() {
        try {
            referralController.exportToTextFile("referrals_report.txt");
            JOptionPane.showMessageDialog(this,
                    "Referrals exported to referrals_report.txt",
                    "Export Successful",
                    JOptionPane.INFORMATION_MESSAGE);
        } catch (IOException ex) {
            showError(ex);
        }
    }

    private void showError(Exception ex) {
        ex.printStackTrace();
        JOptionPane.showMessageDialog(this,
                ex.getMessage(),
                "Error",
                JOptionPane.ERROR_MESSAGE);
    }
}
