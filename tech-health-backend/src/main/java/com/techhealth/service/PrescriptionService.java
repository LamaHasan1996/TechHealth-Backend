package com.techhealth.service;

import com.techhealth.domain.Prescription;
import com.techhealth.repository.PrescriptionRepository;
import com.techhealth.web.ResourceNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class PrescriptionService {

    private final PrescriptionRepository prescriptionRepository;

    public PrescriptionService(PrescriptionRepository prescriptionRepository) {
        this.prescriptionRepository = prescriptionRepository;
    }

    @Transactional(readOnly = true)
    public List<Prescription> getAll() {
        return prescriptionRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Prescription getById(Long id) {
        return prescriptionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Prescription not found: " + id));
    }

    public Prescription create(Prescription body) {
        body.setId(null);
        return prescriptionRepository.save(body);
    }

    public Prescription update(Long id, Prescription body) {
        Prescription existing = getById(id);

        existing.setPatient(body.getPatient());
        existing.setDoctor(body.getDoctor());
        existing.setMedication(body.getMedication());
        existing.setPrescriptionType(body.getPrescriptionType());
        existing.setDosageInstruction(body.getDosageInstruction());
        existing.setFrequencyPerDay(body.getFrequencyPerDay());
        existing.setStartDate(body.getStartDate());
        existing.setEndDate(body.getEndDate());
        existing.setConditionDescription(body.getConditionDescription());

        return prescriptionRepository.save(existing);
    }

    public void delete(Long id) {
        if (!prescriptionRepository.existsById(id)) {
            throw new ResourceNotFoundException("Prescription not found: " + id);
        }
        prescriptionRepository.deleteById(id);
    }

    @Transactional(readOnly = true)
    public List<Prescription> getByPatientId(Long patientId) {
        return prescriptionRepository.findByPatient_Id(patientId);
    }

    @Transactional(readOnly = true)
    public List<Prescription> getByDoctorId(Long doctorId) {
        return prescriptionRepository.findByDoctor_Id(doctorId);
    }

    @Transactional(readOnly = true)
    public List<Prescription> getByMedicationId(Long medicationId) {
        return prescriptionRepository.findByMedication_Id(medicationId);
    }
}

