package com.techhealth.web;

import com.techhealth.domain.Visit;
import com.techhealth.service.VisitService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/visits")
public class VisitController {

    private final VisitService service;

    public VisitController(VisitService service) {
        this.service = service;
    }

    @GetMapping
    public List<Visit> getAll() {
        return service.getAll();
    }

    @GetMapping("/{id}")
    public Visit getById(@PathVariable Long id) {
        return service.getById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Visit create(@Valid @RequestBody Visit body) {
        return service.create(body);
    }

    @PutMapping("/{id}")
    public Visit update(@PathVariable Long id, @Valid @RequestBody Visit body) {
        return service.update(id, body);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }

    @GetMapping("/by-patient/{patientId}")
    public List<Visit> getByPatient(@PathVariable Long patientId) {
        return service.getByPatientId(patientId);
    }

    @GetMapping("/by-facility/{facilityId}")
    public List<Visit> getByFacility(@PathVariable Long facilityId) {
        return service.getByFacilityId(facilityId);
    }
}
