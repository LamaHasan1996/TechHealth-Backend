package com.techhealth.repository;

import com.techhealth.mongo.VitalReading;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface VitalReadingRepository extends MongoRepository<VitalReading, String> {

    List<VitalReading> findTop5ByPatientIdOrderByTimestampDesc(Long patientId);
    List<VitalReading> findByPatientIdOrderByTimestampDesc(Long patientId);
}

