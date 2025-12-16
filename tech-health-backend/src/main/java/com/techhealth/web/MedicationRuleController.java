package com.techhealth.web;

import com.techhealth.mongo.MedicationRule;
import com.techhealth.service.MedicationRuleService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/medication-rules")
public class MedicationRuleController {

    private final MedicationRuleService service;

    public MedicationRuleController(MedicationRuleService service) {
        this.service = service;
    }

    @GetMapping
    public List<MedicationRule> getAll() {
        return service.getAll();
    }

    @GetMapping("/{id}")
    public MedicationRule getById(@PathVariable String id) {
        return service.getById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public MedicationRule create(@RequestBody MedicationRule body) {
        return service.create(body);
    }

    @PutMapping("/{id}")
    public MedicationRule update(@PathVariable String id, @RequestBody MedicationRule body) {
        return service.update(id, body);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable String id) {
        service.delete(id);
    }
}
