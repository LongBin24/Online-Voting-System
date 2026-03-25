package com.khmervote.repository;

import com.khmervote.entity.AuditTrail;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AuditTrailRepository extends JpaRepository<AuditTrail, Long> {
    List<AuditTrail> findAllByOrderByActionTimestampDesc();
}