package com.techhealth.web;

import com.techhealth.domain.Prescription;
import com.techhealth.service.PrescriptionService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/prescriptions")
public class PrescriptionController {

    private final PrescriptionService service;

    public PrescriptionController(PrescriptionService service) {
        this.service = service;
    }

    @GetMapping
    public List<Prescription> getAll() {
        return service.getAll();
    }

    @GetMapping("/{id}")
    public Prescription getById(@PathVariable Long id) {
        return service.getById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Prescription create(@Valid @RequestBody Prescription body) {
        return service.create(body);
    }

    @PutMapping("/{id}")
    public Prescription update(@PathVariable Long id, @Valid @RequestBody Prescription body) {
        return service.update(id, body);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }

    @GetMapping("/by-patient/{patientId}")
    public List<Prescription> getByPatient(@PathVariable Long patientId) {
        return service.getByPatientId(patientId);
    }

    @GetMapping("/by-doctor/{doctorId}")
    public List<Prescription> getByDoctor(@PathVariable Long doctorId) {
        return service.getByDoctorId(doctorId);
    }

    @GetMapping("/by-medication/{medicationId}")
    public List<Prescription> getByMedication(@PathVariable Long medicationId) {
        return service.getByMedicationId(medicationId);
    }
}
