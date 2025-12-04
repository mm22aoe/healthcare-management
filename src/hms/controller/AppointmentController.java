package hms.controller;

import hms.data.AppointmentRepository;
import hms.model.Appointment;

import java.io.IOException;
import java.util.List;

public class AppointmentController {

    private final AppointmentRepository repo;

    public AppointmentController(AppointmentRepository repo) {
        this.repo = repo;
    }

    public List<Appointment> getAllAppointments() {
        return repo.getAll();
    }

    public void addAppointment(Appointment a) throws IOException {
        repo.add(a);
        repo.save();
    }

    public void updateAppointment() throws IOException {
        repo.save();
    }

    public void deleteAppointment(Appointment a) throws IOException {
        repo.remove(a);
        repo.save();
    }
}
