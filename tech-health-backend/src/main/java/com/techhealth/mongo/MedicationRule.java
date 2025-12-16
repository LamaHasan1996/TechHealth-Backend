package com.techhealth.mongo;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.Map;

@Document(collection = "medication_rules")
public class MedicationRule {

    @Id
    private String id;

    @Field("rule_id")
    private String ruleId;

    @Field("medication_id")
    private Long medicationId;

    private String description;

    private Map<String, Object> condition;

    private boolean active;

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getRuleId() { return ruleId; }
    public void setRuleId(String ruleId) { this.ruleId = ruleId; }

    public Long getMedicationId() { return medicationId; }
    public void setMedicationId(Long medicationId) { this.medicationId = medicationId; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public Map<String, Object> getCondition() { return condition; }
    public void setCondition(Map<String, Object> condition) { this.condition = condition; }

    public boolean isActive() { return active; }
    public void setActive(boolean active) { this.active = active; }
}

