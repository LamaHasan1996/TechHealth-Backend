package com.techhealth.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import java.time.LocalDateTime;

@Entity
@Table(name = "facilities")
public class Facility {

    public enum FacilityType { ELDERLY_HOME, CLINIC, HOSPITAL, PHARMACY }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "facility_id")
    private Long id;

    @NotBlank(message = "Facility name is required")
    @Size(min = 2, max = 120, message = "Facility name must be between 2 and 120 characters")
    @Column(nullable = false)
    private String name;

    @NotNull(message = "Facility type is required")
    @Enumerated(EnumType.STRING)
    @Column(name = "facility_type", nullable = false)
    private FacilityType facilityType;

    @NotBlank(message = "City is required")
    @Size(min = 2, max = 80, message = "City must be between 2 and 80 characters")
    @Column(nullable = false)
    private String city;

    @NotBlank(message = "Address line is required")
    @Size(min = 5, max = 200, message = "Address must be between 5 and 200 characters")
    @Column(name = "address_line", nullable = false)
    private String addressLine;

    @NotBlank(message = "Phone is required")
    @Size(min = 7, max = 25, message = "Phone must be between 7 and 25 characters")
    @Pattern(regexp = "^[0-9+()\\-\\s]{7,25}$", message = "Phone contains invalid characters")
    @Column(nullable = false)
    private String phone;

    @Column(name = "is_active")
    private Boolean isActive = true;

    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    public Facility() {}

    @PrePersist
    public void onCreate() {
        if (createdAt == null) {
            createdAt = LocalDateTime.now();
        }
        if (isActive == null) {
            isActive = true;
        }
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public FacilityType getFacilityType() { return facilityType; }
    public void setFacilityType(FacilityType facilityType) { this.facilityType = facilityType; }

    public String getCity() { return city; }
    public void setCity(String city) { this.city = city; }

    public String getAddressLine() { return addressLine; }
    public void setAddressLine(String addressLine) { this.addressLine = addressLine; }

    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }

    public Boolean getIsActive() { return isActive; }
    public void setIsActive(Boolean active) { isActive = active; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
}

