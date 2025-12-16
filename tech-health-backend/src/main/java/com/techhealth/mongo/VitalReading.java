package com.techhealth.mongo;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.time.Instant;
import java.util.Map;

@Document(collection = "vital_readings")
public class VitalReading {

    @Id
    private String id;

    @Field("patient_id")
    private Long patientId;

    @Field("visit_id")
    private Long visitId;

    @Field("recorded_by")
    private Long recordedBy;

    private Instant timestamp;

    private Map<String, Object> vitals;

    private String notes;

    // getters & setters...

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public Long getPatientId() { return patientId; }
    public void setPatientId(Long patientId) { this.patientId = patientId; }

    public Long getVisitId() { return visitId; }
    public void setVisitId(Long visitId) { this.visitId = visitId; }

    public Long getRecordedBy() { return recordedBy; }
    public void setRecordedBy(Long recordedBy) { this.recordedBy = recordedBy; }

    public Instant getTimestamp() { return timestamp; }
    public void setTimestamp(Instant timestamp) { this.timestamp = timestamp; }

    public Map<String, Object> getVitals() { return vitals; }
    public void setVitals(Map<String, Object> vitals) { this.vitals = vitals; }

    public String getNotes() { return notes; }
    public void setNotes(String notes) { this.notes = notes; }
}


