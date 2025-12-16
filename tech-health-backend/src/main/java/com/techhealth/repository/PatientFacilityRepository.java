package com.techhealth.repository;

import com.techhealth.domain.PatientFacility;
import com.techhealth.domain.PatientFacility.PatientFacilityId;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PatientFacilityRepository extends JpaRepository<PatientFacility, PatientFacilityId> {
    List<PatientFacility> findByPatient_Id(Long patientId);
    List<PatientFacility> findByFacility_Id(Long facilityId);
    boolean existsByPatient_IdAndFacility_IdAndId_StartDate(Long patientId, Long facilityId, java.time.LocalDate startDate);
}
