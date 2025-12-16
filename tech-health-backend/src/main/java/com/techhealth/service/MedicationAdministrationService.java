package com.techhealth.service;

import com.techhealth.domain.MedicationAdministration;
import com.techhealth.repository.MedicationAdministrationRepository;
import com.techhealth.web.ResourceNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class MedicationAdministrationService {

    private final MedicationAdministrationRepository medicationAdministrationRepository;

    public MedicationAdministrationService(MedicationAdministrationRepository medicationAdministrationRepository) {
        this.medicationAdministrationRepository = medicationAdministrationRepository;
    }

    @Transactional(readOnly = true)
    public List<MedicationAdministration> getAll() {
        return medicationAdministrationRepository.findAll();
    }

    @Transactional(readOnly = true)
    public MedicationAdministration getById(Long id) {
        return medicationAdministrationRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("MedicationAdministration not found: " + id));
    }

    public MedicationAdministration create(MedicationAdministration body) {
        body.setId(null);
        return medicationAdministrationRepository.save(body);
    }

    public MedicationAdministration update(Long id, MedicationAdministration body) {
        MedicationAdministration existing = getById(id);

        existing.setVisit(body.getVisit());
        existing.setPrescription(body.getPrescription());
        existing.setAdministeredBy(body.getAdministeredBy());
        existing.setAdministeredAt(body.getAdministeredAt());
        existing.setDoseGiven(body.getDoseGiven());
        existing.setWasGiven(body.isWasGiven());
        existing.setComments(body.getComments());

        return medicationAdministrationRepository.save(existing);
    }

    public void delete(Long id) {
        if (!medicationAdministrationRepository.existsById(id)) {
            throw new ResourceNotFoundException("MedicationAdministration not found: " + id);
        }
        medicationAdministrationRepository.deleteById(id);
    }

    @Transactional(readOnly = true)
    public List<MedicationAdministration> getByVisitId(Long visitId) {
        return medicationAdministrationRepository.findByVisit_Id(visitId);
    }

    @Transactional(readOnly = true)
    public List<MedicationAdministration> getByPrescriptionId(Long prescriptionId) {
        return medicationAdministrationRepository.findByPrescription_Id(prescriptionId);
    }

    @Transactional(readOnly = true)
    public List<MedicationAdministration> getByAdministeredById(Long userId) {
        return medicationAdministrationRepository.findByAdministeredBy_Id(userId);
    }
}
