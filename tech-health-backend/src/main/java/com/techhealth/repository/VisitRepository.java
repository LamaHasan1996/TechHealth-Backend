package com.techhealth.repository;

import com.techhealth.domain.Visit;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface VisitRepository extends JpaRepository<Visit, Long> {
    List<Visit> findByPatient_Id(Long patientId);
    List<Visit> findByFacility_Id(Long facilityId);
}


