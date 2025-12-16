package com.techhealth.domain;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "prescriptions")
public class Prescription {

    public enum PrescriptionType { DAILY, CONDITIONAL, WEEKLY, MONTHLY }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "prescription_id")
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "patient_id")
    private Patient patient;

    @ManyToOne(optional = false)
    @JoinColumn(name = "doctor_id")
    private User doctor;

    @ManyToOne(optional = false)
    @JoinColumn(name = "medication_id")
    private Medication medication;

    @Enumerated(EnumType.STRING)
    @Column(name = "prescription_type", nullable = false)
    private PrescriptionType prescriptionType;

    @Column(name = "dosage_instruction", nullable = false)
    private String dosageInstruction;

    @Column(name = "frequency_per_day")
    private Integer frequencyPerDay; // nullable for CONDITIONAL

    @Column(name = "start_date", nullable = false)
    private LocalDate startDate;

    @Column(name = "end_date")
    private LocalDate endDate;

    @Column(name = "condition_description")
    private String conditionDescription;

    @Column(name = "created_at")
    private LocalDateTime createdAt = LocalDateTime.now();

    public Prescription() {}

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Patient getPatient() { return patient; }
    public void setPatient(Patient patient) { this.patient = patient; }

    public User getDoctor() { return doctor; }
    public void setDoctor(User doctor) { this.doctor = doctor; }

    public Medication getMedication() { return medication; }
    public void setMedication(Medication medication) { this.medication = medication; }

    public PrescriptionType getPrescriptionType() { return prescriptionType; }
    public void setPrescriptionType(PrescriptionType prescriptionType) { this.prescriptionType = prescriptionType; }

    public String getDosageInstruction() { return dosageInstruction; }
    public void setDosageInstruction(String dosageInstruction) { this.dosageInstruction = dosageInstruction; }

    public Integer getFrequencyPerDay() { return frequencyPerDay; }
    public void setFrequencyPerDay(Integer frequencyPerDay) { this.frequencyPerDay = frequencyPerDay; }

    public LocalDate getStartDate() { return startDate; }
    public void setStartDate(LocalDate startDate) { this.startDate = startDate; }

    public LocalDate getEndDate() { return endDate; }
    public void setEndDate(LocalDate endDate) { this.endDate = endDate; }

    public String getConditionDescription() { return conditionDescription; }
    public void setConditionDescription(String conditionDescription) { this.conditionDescription = conditionDescription; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
}
