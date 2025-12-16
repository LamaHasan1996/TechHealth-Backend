package com.techhealth.service;

import com.techhealth.domain.Patient;
import com.techhealth.repository.PatientRepository;
import com.techhealth.web.ResourceNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class PatientService {

    private final PatientRepository patientRepository;

    public PatientService(PatientRepository patientRepository) {
        this.patientRepository = patientRepository;
    }

    @Transactional(readOnly = true)
    public List<Patient> getAll() {
        return patientRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Patient getById(Long id) {
        return patientRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Patient not found: " + id));
    }

    public Patient create(Patient body) {
        body.setId(null);
        return patientRepository.save(body);
    }

    public Patient update(Long id, Patient body) {
        Patient existing = getById(id);

        existing.setFirstName(body.getFirstName());
        existing.setLastName(body.getLastName());
        existing.setDateOfBirth(body.getDateOfBirth());
        existing.setGender(body.getGender());

        return patientRepository.save(existing);
    }


    public void delete(Long id) {
        if (!patientRepository.existsById(id)) {
            throw new ResourceNotFoundException("Patient not found: " + id);
        }
        patientRepository.deleteById(id);
    }
}
