package com.techhealth.service;

import com.techhealth.mongo.MedicationRule;
import com.techhealth.repository.MedicationRuleRepository;
import com.techhealth.web.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MedicationRuleService {

    private final MedicationRuleRepository medicationRuleRepository;

    public MedicationRuleService(MedicationRuleRepository medicationRuleRepository) {
        this.medicationRuleRepository = medicationRuleRepository;
    }

    public List<MedicationRule> getAll() {
        return medicationRuleRepository.findAll();
    }

    public MedicationRule getById(String id) {
        return medicationRuleRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("MedicationRule not found: " + id));
    }

    public MedicationRule create(MedicationRule body) {
        body.setId(null);
        return medicationRuleRepository.save(body);
    }

    public MedicationRule update(String id, MedicationRule body) {
        MedicationRule existing = getById(id);

        existing.setRuleId(body.getRuleId());
        existing.setMedicationId(body.getMedicationId());
        existing.setDescription(body.getDescription());
        existing.setCondition(body.getCondition());
        existing.setActive(body.isActive());

        return medicationRuleRepository.save(existing);
    }

    public void delete(String id) {
        if (!medicationRuleRepository.existsById(id)) {
            throw new ResourceNotFoundException("MedicationRule not found: " + id);
        }
        medicationRuleRepository.deleteById(id);
    }
}

