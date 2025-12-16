package com.techhealth.service;

import com.techhealth.domain.Facility;
import com.techhealth.domain.Patient;
import com.techhealth.domain.PatientFacility;
import com.techhealth.domain.PatientFacility.PatientFacilityId;
import com.techhealth.repository.FacilityRepository;
import com.techhealth.repository.PatientFacilityRepository;
import com.techhealth.repository.PatientRepository;
import com.techhealth.web.ResourceNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
@Transactional
public class PatientFacilityService {

    private final PatientFacilityRepository patientFacilityRepository;
    private final PatientRepository patientRepository;
    private final FacilityRepository facilityRepository;

    public PatientFacilityService(PatientFacilityRepository patientFacilityRepository,
                                  PatientRepository patientRepository,
                                  FacilityRepository facilityRepository) {
        this.patientFacilityRepository = patientFacilityRepository;
        this.patientRepository = patientRepository;
        this.facilityRepository = facilityRepository;
    }

    @Transactional(readOnly = true)
    public List<PatientFacility> getAll() {
        return patientFacilityRepository.findAll();
    }

    @Transactional(readOnly = true)
    public PatientFacility getById(Long patientId, Long facilityId, LocalDate startDate) {
        PatientFacilityId id = new PatientFacilityId(patientId, facilityId, startDate);
        return patientFacilityRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("PatientFacility not found: patientId=" + patientId + ", facilityId=" + facilityId + ", startDate=" + startDate));
    }

    public PatientFacility create(Long patientId, Long facilityId, LocalDate startDate, LocalDate endDate) {
        if (patientFacilityRepository.existsByPatient_IdAndFacility_IdAndId_StartDate(patientId, facilityId, startDate)) {
            return getById(patientId, facilityId, startDate);
        }

        Patient patient = patientRepository.findById(patientId)
                .orElseThrow(() -> new ResourceNotFoundException("Patient not found: " + patientId));

        Facility facility = facilityRepository.findById(facilityId)
                .orElseThrow(() -> new ResourceNotFoundException("Facility not found: " + facilityId));

        PatientFacility pf = new PatientFacility();
        pf.setId(new PatientFacilityId(patientId, facilityId, startDate));
        pf.setPatient(patient);
        pf.setFacility(facility);
        pf.setEndDate(endDate);

        return patientFacilityRepository.save(pf);
    }

    public PatientFacility update(Long patientId, Long facilityId, LocalDate startDate, LocalDate endDate) {
        PatientFacility existing = getById(patientId, facilityId, startDate);
        existing.setEndDate(endDate);
        return patientFacilityRepository.save(existing);
    }

    public void delete(Long patientId, Long facilityId, LocalDate startDate) {
        PatientFacilityId id = new PatientFacilityId(patientId, facilityId, startDate);
        if (!patientFacilityRepository.existsById(id)) {
            throw new ResourceNotFoundException("PatientFacility not found: patientId=" + patientId + ", facilityId=" + facilityId + ", startDate=" + startDate);
        }
        patientFacilityRepository.deleteById(id);
    }

    @Transactional(readOnly = true)
    public List<PatientFacility> getByPatientId(Long patientId) {
        return patientFacilityRepository.findByPatient_Id(patientId);
    }

    @Transactional(readOnly = true)
    public List<PatientFacility> getByFacilityId(Long facilityId) {
        return patientFacilityRepository.findByFacility_Id(facilityId);
    }
}

