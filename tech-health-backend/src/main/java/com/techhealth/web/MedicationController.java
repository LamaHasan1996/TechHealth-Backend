package com.techhealth.web;

import com.techhealth.domain.Medication;
import com.techhealth.service.MedicationService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/medications")
public class MedicationController {

    private final MedicationService service;

    public MedicationController(MedicationService service) {
        this.service = service;
    }

    @GetMapping
    public List<Medication> getAll() {
        return service.getAll();
    }

    @GetMapping("/{id}")
    public Medication getById(@PathVariable Long id) {
        return service.getById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Medication create(@Valid @RequestBody Medication body) {
        return service.create(body);
    }

    @PutMapping("/{id}")
    public Medication update(@PathVariable Long id, @Valid @RequestBody Medication body) {
        return service.update(id, body);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }
}
