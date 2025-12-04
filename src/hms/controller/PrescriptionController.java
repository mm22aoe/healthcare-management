package hms.controller;

import hms.data.PrescriptionRepository;
import hms.model.Prescription;

import java.io.IOException;
import java.util.List;

public class PrescriptionController {

    private final PrescriptionRepository repo;

    public PrescriptionController(PrescriptionRepository repo) {
        this.repo = repo;
    }

    public List<Prescription> getAllPrescriptions() {
        return repo.getAll();
    }

    public void addPrescription(Prescription p) throws IOException {
        repo.add(p);
        repo.save();
    }

    public void updatePrescription() throws IOException {
        repo.save();
    }

    public void deletePrescription(Prescription p) throws IOException {
        repo.remove(p);
        repo.save();
    }

    public void exportToTextFile(String filename) throws IOException {
    repo.exportToTextFile(filename);
}
}
