package com.techhealth.web;

import com.techhealth.domain.MedicationAdministration;
import com.techhealth.service.MedicationAdministrationService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/medication-administrations")
public class MedicationAdministrationController {

    private final MedicationAdministrationService service;

    public MedicationAdministrationController(MedicationAdministrationService service) {
        this.service = service;
    }

    @GetMapping
    public List<MedicationAdministration> getAll() {
        return service.getAll();
    }

    @GetMapping("/{id}")
    public MedicationAdministration getById(@PathVariable Long id) {
        return service.getById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public MedicationAdministration create(@Valid @RequestBody MedicationAdministration body) {
        return service.create(body);
    }

    @PutMapping("/{id}")
    public MedicationAdministration update(@PathVariable Long id, @Valid @RequestBody MedicationAdministration body) {
        return service.update(id, body);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }

    @GetMapping("/by-visit/{visitId}")
    public List<MedicationAdministration> getByVisit(@PathVariable Long visitId) {
        return service.getByVisitId(visitId);
    }

    @GetMapping("/by-prescription/{prescriptionId}")
    public List<MedicationAdministration> getByPrescription(@PathVariable Long prescriptionId) {
        return service.getByPrescriptionId(prescriptionId);
    }

    @GetMapping("/by-administered-by/{userId}")
    public List<MedicationAdministration> getByAdministeredBy(@PathVariable Long userId) {
        return service.getByAdministeredById(userId);
    }
}
