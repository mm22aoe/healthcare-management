package hms.controller;

import hms.data.PatientRepository;
import hms.model.Patient;

import java.io.IOException;
import java.util.List;

public class PatientController {

    private final PatientRepository repo;

    public PatientController(PatientRepository repo) {
        this.repo = repo;
    }

    public List<Patient> getAllPatients() {
        return repo.getAll();
    }

    public void addPatient(Patient p) throws IOException {
        repo.add(p);
        repo.save();
    }

    public void updatePatient() throws IOException {
        // changes are already in object references
        repo.save();
    }

    public void deletePatient(Patient p) throws IOException {
        repo.remove(p);
        repo.save();
    }
}
