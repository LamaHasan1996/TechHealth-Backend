package com.techhealth.web;

import com.techhealth.domain.PatientFacility;
import com.techhealth.service.PatientFacilityService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/patient-facilities")
public class PatientFacilityController {

    private final PatientFacilityService service;

    public PatientFacilityController(PatientFacilityService service) {
        this.service = service;
    }

    @GetMapping
    public List<PatientFacility> getAll() {
        return service.getAll();
    }

    @GetMapping("/{patientId}/{facilityId}/{startDate}")
    public PatientFacility getById(@PathVariable Long patientId,
                                   @PathVariable Long facilityId,
                                   @PathVariable String startDate) {
        return service.getById(patientId, facilityId, LocalDate.parse(startDate));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public PatientFacility create(@RequestParam Long patientId,
                                  @RequestParam Long facilityId,
                                  @RequestParam String startDate,
                                  @RequestParam(required = false) String endDate) {
        LocalDate sd = LocalDate.parse(startDate);
        LocalDate ed = (endDate == null || endDate.isBlank()) ? null : LocalDate.parse(endDate);
        return service.create(patientId, facilityId, sd, ed);
    }

    @PutMapping("/{patientId}/{facilityId}/{startDate}")
    public PatientFacility update(@PathVariable Long patientId,
                                  @PathVariable Long facilityId,
                                  @PathVariable String startDate,
                                  @RequestParam(required = false) String endDate) {
        LocalDate sd = LocalDate.parse(startDate);
        LocalDate ed = (endDate == null || endDate.isBlank()) ? null : LocalDate.parse(endDate);
        return service.update(patientId, facilityId, sd, ed);
    }

    @DeleteMapping("/{patientId}/{facilityId}/{startDate}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long patientId,
                       @PathVariable Long facilityId,
                       @PathVariable String startDate) {
        service.delete(patientId, facilityId, LocalDate.parse(startDate));
    }

    @GetMapping("/by-patient/{patientId}")
    public List<PatientFacility> getByPatient(@PathVariable Long patientId) {
        return service.getByPatientId(patientId);
    }

    @GetMapping("/by-facility/{facilityId}")
    public List<PatientFacility> getByFacility(@PathVariable Long facilityId) {
        return service.getByFacilityId(facilityId);
    }
}
