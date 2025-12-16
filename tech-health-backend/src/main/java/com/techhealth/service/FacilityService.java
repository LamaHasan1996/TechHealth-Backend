package com.techhealth.service;

import com.techhealth.domain.Facility;
import com.techhealth.repository.FacilityRepository;
import com.techhealth.web.ResourceNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class FacilityService {

    private final FacilityRepository facilityRepository;

    public FacilityService(FacilityRepository facilityRepository) {
        this.facilityRepository = facilityRepository;
    }

    @Transactional(readOnly = true)
    public List<Facility> getAll() {
        return facilityRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Facility getById(Long id) {
        return facilityRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Facility not found: " + id));
    }

    public Facility create(Facility body) {
        body.setId(null);
        return facilityRepository.save(body);
    }

    public Facility update(Long id, Facility body) {
        Facility existing = getById(id);

        existing.setName(body.getName());
        existing.setFacilityType(body.getFacilityType());
        existing.setCity(body.getCity());
        existing.setAddressLine(body.getAddressLine());
        existing.setPhone(body.getPhone());
        existing.setIsActive(body.getIsActive());

        return facilityRepository.save(existing);
    }

    public void delete(Long id) {
        if (!facilityRepository.existsById(id)) {
            throw new ResourceNotFoundException("Facility not found: " + id);
        }
        facilityRepository.deleteById(id);
    }
}

