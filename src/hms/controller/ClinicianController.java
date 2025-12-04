// src/hms/controller/ClinicianController.java
package hms.controller;

import hms.data.ClinicianRepository;
import hms.model.ClinicianInfo;

import java.io.IOException;
import java.util.List;

public class ClinicianController {

    private final ClinicianRepository repo;

    public ClinicianController(ClinicianRepository repo) {
        this.repo = repo;
    }

    public List<ClinicianInfo> getAllClinicians() {
        return repo.getAll();
    }

    public void addClinician(ClinicianInfo clinician) throws IOException {
        repo.add(clinician);
        repo.save();
    }

    public void updateClinician(int index, ClinicianInfo updatedClinician) throws IOException {
        repo.getAll().set(index, updatedClinician);
        repo.save();
    }

    public void deleteClinician(int index) throws IOException {
        repo.getAll().remove(index);
        repo.save();
    }
}
