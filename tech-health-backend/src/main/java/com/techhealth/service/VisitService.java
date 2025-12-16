package com.techhealth.service;

import com.techhealth.domain.Visit;
import com.techhealth.repository.VisitRepository;
import com.techhealth.web.ResourceNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class VisitService {

    private final VisitRepository visitRepository;

    public VisitService(VisitRepository visitRepository) {
        this.visitRepository = visitRepository;
    }

    @Transactional(readOnly = true)
    public List<Visit> getAll() {
        return visitRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Visit getById(Long id) {
        return visitRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Visit not found: " + id));
    }

    public Visit create(Visit body) {
        body.setId(null);
        return visitRepository.save(body);
    }

    public Visit update(Long id, Visit body) {
        Visit existing = getById(id);

        existing.setPatient(body.getPatient());
        existing.setFacility(body.getFacility());
        existing.setScheduledBy(body.getScheduledBy());
        existing.setAssignedTo(body.getAssignedTo());
        existing.setVisitDatetime(body.getVisitDatetime());
        existing.setStatus(body.getStatus());
        existing.setNotes(body.getNotes());

        return visitRepository.save(existing);
    }

    public void delete(Long id) {
        if (!visitRepository.existsById(id)) {
            throw new ResourceNotFoundException("Visit not found: " + id);
        }
        visitRepository.deleteById(id);
    }

    @Transactional(readOnly = true)
    public List<Visit> getByPatientId(Long patientId) {
        return visitRepository.findByPatient_Id(patientId);
    }

    @Transactional(readOnly = true)
    public List<Visit> getByFacilityId(Long facilityId) {
        return visitRepository.findByFacility_Id(facilityId);
    }
}

