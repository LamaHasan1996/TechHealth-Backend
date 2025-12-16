package com.techhealth.domain;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "medication_administration")
public class MedicationAdministration {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "administration_id")
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "visit_id")
    private Visit visit;

    @ManyToOne(optional = false)
    @JoinColumn(name = "prescription_id")
    private Prescription prescription;

    @ManyToOne(optional = false)
    @JoinColumn(name = "administered_by")
    private User administeredBy;

    @Column(name = "administered_at", nullable = false)
    private LocalDateTime administeredAt;

    @Column(name = "dose_given")
    private String doseGiven;

    @Column(name = "was_given", nullable = false)
    private boolean wasGiven;

    @Column(columnDefinition = "TEXT")
    private String comments;

    public MedicationAdministration() {}

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Visit getVisit() { return visit; }
    public void setVisit(Visit visit) { this.visit = visit; }

    public Prescription getPrescription() { return prescription; }
    public void setPrescription(Prescription prescription) { this.prescription = prescription; }

    public User getAdministeredBy() { return administeredBy; }
    public void setAdministeredBy(User administeredBy) { this.administeredBy = administeredBy; }

    public LocalDateTime getAdministeredAt() { return administeredAt; }
    public void setAdministeredAt(LocalDateTime administeredAt) { this.administeredAt = administeredAt; }

    public String getDoseGiven() { return doseGiven; }
    public void setDoseGiven(String doseGiven) { this.doseGiven = doseGiven; }

    public boolean isWasGiven() { return wasGiven; }
    public void setWasGiven(boolean wasGiven) { this.wasGiven = wasGiven; }

    public String getComments() { return comments; }
    public void setComments(String comments) { this.comments = comments; }
}
