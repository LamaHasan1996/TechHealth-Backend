package com.techhealth.web;

import com.techhealth.mongo.VitalReading;
import com.techhealth.service.VitalReadingService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/vital-readings")
public class VitalReadingController {

    private final VitalReadingService service;

    public VitalReadingController(VitalReadingService service) {
        this.service = service;
    }

    @GetMapping
    public List<VitalReading> getAll() {
        return service.getAll();
    }

    @GetMapping("/{id}")
    public VitalReading getById(@PathVariable String id) {
        return service.getById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public VitalReading create(@RequestBody VitalReading body) {
        return service.create(body);
    }

    @PutMapping("/{id}")
    public VitalReading update(@PathVariable String id, @RequestBody VitalReading body) {
        return service.update(id, body);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable String id) {
        service.delete(id);
    }

    @GetMapping("/by-patient/{patientId}")
    public List<VitalReading> getByPatient(@PathVariable Long patientId) {
        return service.getByPatientId(patientId);
    }

    @GetMapping("/latest5/by-patient/{patientId}")
    public List<VitalReading> getLatest5ByPatient(@PathVariable Long patientId) {
        return service.getLatest5ByPatientId(patientId);
    }

    @GetMapping("/latest/by-patient/{patientId}")
    public VitalReading getLatestByPatient(@PathVariable Long patientId) {
        return service.getLatestByPatientId(patientId);
    }
}
