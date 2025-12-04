package hms.controller;

import hms.data.ReferralRepository;
import hms.model.Referral;
import hms.singleton.ReferralManager;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class ReferralController {

    private final ReferralRepository repo;
    private final ReferralManager manager;

    public ReferralController(ReferralRepository repo) {
        this.repo = repo;
        this.manager = ReferralManager.getInstance();
    }

    public List<Referral> getAllReferrals() {
        return manager.getReferrals();
    }

    public void addReferral(Referral r) throws IOException {
        manager.addReferral(r);
        repo.getAll().add(r);
        repo.save();
    }

    public void updateReferral(Referral r) throws IOException {
        manager.updateReferral(r);
        repo.save();
    }

    public void deleteReferral(Referral r) throws IOException {
        manager.removeReferral(r.getReferralId());
        repo.getAll().remove(r);
        repo.save();
    }

    public void exportToTextFile(String filename) throws IOException {
        List<String> referrals = new ArrayList<>();
        List<Referral> allReferrals = manager.getReferrals(); // get the actual list

        List<String> lines = new ArrayList<>();

        // Add a report header
        lines.add("REFERRALS REPORT");
        lines.add("================");

        if (allReferrals.isEmpty()) {
            lines.add("No referrals available.");
        } else {
            for (Referral r : allReferrals) {
                lines.add("Referral ID     : " + r.getReferralId());
                lines.add("Patient ID      : " + r.getPatientId());
                lines.add("From Clinician  : " + r.getFromClinicianId());
                lines.add("To Specialty    : " + r.getToSpecialty());
                lines.add("Urgency         : " + r.getUrgency());
                lines.add("Status          : " + r.getStatus());
                lines.add("Summary         : " + r.getSummary());
                lines.add("--------------------------------------------------");
            }
        }

        Files.write(Path.of(filename), lines);
    }
}
