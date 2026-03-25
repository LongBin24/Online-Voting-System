package com.khmervote.repository;

import com.khmervote.entity.Verification;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface VerificationRepository extends JpaRepository<Verification, Long> {
    Optional<Verification> findByVoterIdAndCode(Long voterId, String code);
}
