package com.techhealth.repository;

import com.techhealth.mongo.MedicationRule;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface MedicationRuleRepository extends MongoRepository<MedicationRule, String> {}

