package com.techhealth.repository;

import com.techhealth.domain.MedicationAdministration;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MedicationAdministrationRepository extends JpaRepository<MedicationAdministration, Long> {
    List<MedicationAdministration> findByVisit_Id(Long visitId);
    List<MedicationAdministration> findByPrescription_Id(Long prescriptionId);
    List<MedicationAdministration> findByAdministeredBy_Id(Long userId);
}

