package com.techhealth.domain;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "medications")
public class Medication {

    public enum Form { TABLET, CAPSULE, SYRUP, INJECTION }
    public enum Route { ORAL, IV, IM, SC }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "medication_id")
    private Long id;

    @Column(nullable = false)
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Form form;

    @Column(nullable = false)
    private String strength;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Route route;

    @Column(name = "is_controlled", nullable = false)
    private boolean isControlled;

    @Column(name = "stock_quantity", nullable = false)
    private int stockQuantity;

    @Column(name = "created_at")
    private LocalDateTime createdAt = LocalDateTime.now();

    public Medication() {}

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public Form getForm() { return form; }
    public void setForm(Form form) { this.form = form; }

    public String getStrength() { return strength; }
    public void setStrength(String strength) { this.strength = strength; }

    public Route getRoute() { return route; }
    public void setRoute(Route route) { this.route = route; }

    public boolean isControlled() { return isControlled; }
    public void setControlled(boolean controlled) { isControlled = controlled; }

    public int getStockQuantity() { return stockQuantity; }
    public void setStockQuantity(int stockQuantity) { this.stockQuantity = stockQuantity; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
}

