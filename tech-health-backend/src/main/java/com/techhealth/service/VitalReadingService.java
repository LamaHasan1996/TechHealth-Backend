package com.techhealth.service;

import com.techhealth.mongo.VitalReading;
import com.techhealth.repository.VitalReadingRepository;
import com.techhealth.web.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;

@Service
public class VitalReadingService {

    private final VitalReadingRepository vitalReadingRepository;

    public VitalReadingService(VitalReadingRepository vitalReadingRepository) {
        this.vitalReadingRepository = vitalReadingRepository;
    }

    public List<VitalReading> getAll() {
        return vitalReadingRepository.findAll();
    }

    public VitalReading getById(String id) {
        return vitalReadingRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("VitalReading not found: " + id));
    }

    public VitalReading create(VitalReading body) {
        body.setId(null);
        if (body.getTimestamp() == null) {
            body.setTimestamp(Instant.now());
        }
        return vitalReadingRepository.save(body);
    }

    public VitalReading update(String id, VitalReading body) {
        VitalReading existing = getById(id);

        existing.setPatientId(body.getPatientId());
        existing.setVisitId(body.getVisitId());
        existing.setRecordedBy(body.getRecordedBy());
        existing.setTimestamp(body.getTimestamp());
        existing.setVitals(body.getVitals());
        existing.setNotes(body.getNotes());

        return vitalReadingRepository.save(existing);
    }

    public void delete(String id) {
        if (!vitalReadingRepository.existsById(id)) {
            throw new ResourceNotFoundException("VitalReading not found: " + id);
        }
        vitalReadingRepository.deleteById(id);
    }

    public List<VitalReading> getByPatientId(Long patientId) {
        return vitalReadingRepository.findByPatientIdOrderByTimestampDesc(patientId);
    }

    public List<VitalReading> getLatest5ByPatientId(Long patientId) {
        return vitalReadingRepository.findTop5ByPatientIdOrderByTimestampDesc(patientId);
    }

    public VitalReading getLatestByPatientId(Long patientId) {
        List<VitalReading> list = vitalReadingRepository.findTop5ByPatientIdOrderByTimestampDesc(patientId);
        if (list.isEmpty()) {
            throw new ResourceNotFoundException("No VitalReading found for patientId: " + patientId);
        }
        return list.get(0);
    }
}

