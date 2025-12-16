package com.techhealth.service;

import com.techhealth.domain.Medication;
import com.techhealth.repository.MedicationRepository;
import com.techhealth.web.ResourceNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class MedicationService {

    private final MedicationRepository medicationRepository;

    public MedicationService(MedicationRepository medicationRepository) {
        this.medicationRepository = medicationRepository;
    }

    @Transactional(readOnly = true)
    public List<Medication> getAll() {
        return medicationRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Medication getById(Long id) {
        return medicationRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Medication not found: " + id));
    }

    public Medication create(Medication body) {
        body.setId(null);
        return medicationRepository.save(body);
    }

    public Medication update(Long id, Medication body) {
        Medication existing = getById(id);

        existing.setName(body.getName());
        existing.setForm(body.getForm());
        existing.setStrength(body.getStrength());
        existing.setRoute(body.getRoute());
        existing.setControlled(body.isControlled());
        existing.setStockQuantity(body.getStockQuantity());

        return medicationRepository.save(existing);
    }

    public void delete(Long id) {
        if (!medicationRepository.existsById(id)) {
            throw new ResourceNotFoundException("Medication not found: " + id);
        }
        medicationRepository.deleteById(id);
    }
}
