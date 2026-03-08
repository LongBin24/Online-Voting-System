package com.khmervote.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;
import java.time.LocalDateTime;

@Entity
@Data
public class AuditTrail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long voterId;
    private String action;
    private LocalDateTime actionTimestamp = LocalDateTime.now();

    public AuditTrail(Long voterId, String action) {
        this.voterId = voterId;
        this.action = action;
    }
    public AuditTrail() {}
}