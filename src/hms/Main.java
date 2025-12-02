package hms;

import javax.swing.SwingUtilities;
import hms.view.MainFrame;
import hms.controller.ClinicianController;
import hms.data.*;
import hms.singleton.ReferralManager;
import hms.model.Referral;

import java.io.IOException;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        // Adjust to actual CSV locations or currently in project root
        final String PATIENTS_FILE = "patients.csv";
        final String CLINICIANS_FILE = "clinicians.csv";
        final String FACILITIES_FILE = "facilities.csv";
        final String APPOINTMENTS_FILE = "appointments.csv";
        final String PRESCRIPTIONS_FILE = "prescriptions.csv";
        final String REFERRALS_FILE = "referrals.csv";
        final String STAFF_FILE = "staff.csv";

        try {
            PatientRepository patientRepo = new PatientRepository(PATIENTS_FILE);
            ClinicianRepository clinicianRepo = new ClinicianRepository(CLINICIANS_FILE);
            ClinicianController clinicianController = new ClinicianController(clinicianRepo);
            FacilityRepository facilityRepo = new FacilityRepository(FACILITIES_FILE);
            AppointmentRepository appointmentRepo = new AppointmentRepository(APPOINTMENTS_FILE);
            PrescriptionRepository prescriptionRepo = new PrescriptionRepository(PRESCRIPTIONS_FILE);
            ReferralRepository referralRepo = new ReferralRepository(REFERRALS_FILE);
            StaffRepository staffRepo = new StaffRepository(STAFF_FILE);

            // Initialize Singleton with existing referrals
            List<Referral> referrals = referralRepo.getAll();
            ReferralManager.init(referrals);

            SwingUtilities.invokeLater(() -> {
                MainFrame frame = new MainFrame(
                        patientRepo,
                        clinicianRepo,
                        facilityRepo,
                        appointmentRepo,
                        prescriptionRepo,
                        referralRepo,
                        staffRepo
                );
                frame.setVisible(true);
            });

        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Failed to load CSV files: " + e.getMessage());
        }
    }
}
