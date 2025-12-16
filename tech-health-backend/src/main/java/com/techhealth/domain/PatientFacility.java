package com.techhealth.domain;

import jakarta.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

@Entity
@Table(name = "patient_facilities")
public class PatientFacility {

    @EmbeddedId
    private PatientFacilityId id;

    @MapsId("patientId")
    @ManyToOne(optional = false)
    @JoinColumn(name = "patient_id")
    private Patient patient;

    @MapsId("facilityId")
    @ManyToOne(optional = false)
    @JoinColumn(name = "facility_id")
    private Facility facility;

    @Column(name = "end_date")
    private LocalDate endDate;

    public PatientFacility() {}

    public PatientFacilityId getId() { return id; }
    public void setId(PatientFacilityId id) { this.id = id; }

    public Patient getPatient() { return patient; }
    public void setPatient(Patient patient) { this.patient = patient; }

    public Facility getFacility() { return facility; }
    public void setFacility(Facility facility) { this.facility = facility; }

    public LocalDate getEndDate() { return endDate; }
    public void setEndDate(LocalDate endDate) { this.endDate = endDate; }

    // ----- Embedded ID class -----
    @Embeddable
    public static class PatientFacilityId implements Serializable {
        @Column(name = "patient_id")
        private Long patientId;

        @Column(name = "facility_id")
        private Long facilityId;

        @Column(name = "start_date")
        private LocalDate startDate;

        public PatientFacilityId() {}

        public PatientFacilityId(Long patientId, Long facilityId, LocalDate startDate) {
            this.patientId = patientId;
            this.facilityId = facilityId;
            this.startDate = startDate;
        }

        public Long getPatientId() { return patientId; }
        public void setPatientId(Long patientId) { this.patientId = patientId; }

        public Long getFacilityId() { return facilityId; }
        public void setFacilityId(Long facilityId) { this.facilityId = facilityId; }

        public LocalDate getStartDate() { return startDate; }
        public void setStartDate(LocalDate startDate) { this.startDate = startDate; }

        @Override public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof PatientFacilityId that)) return false;
            return Objects.equals(patientId, that.patientId)
                    && Objects.equals(facilityId, that.facilityId)
                    && Objects.equals(startDate, that.startDate);
        }

        @Override public int hashCode() {
            return Objects.hash(patientId, facilityId, startDate);
        }
    }
}
