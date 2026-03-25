package com.khmervote.controller;

import com.khmervote.entity.AuditTrail;
import com.khmervote.entity.Candidate;
import com.khmervote.repository.CandidateRepository;
import com.khmervote.repository.AuditTrailRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin/candidates")
public class AdminCandidateController {

    @Autowired
    private CandidateRepository candidateRepository;

    @Autowired
    private AuditTrailRepository auditRepository;

    @PostMapping("/add")
    public ResponseEntity<?> addCandidate(@RequestBody Candidate candidate) {
        candidateRepository.save(candidate);
        return ResponseEntity.ok("បន្ថែមបេក្ខជនជោគជ័យ!");
    }

    @GetMapping("/all")
    public List<Candidate> getAll() {
        return candidateRepository.findAll();
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        candidateRepository.deleteById(id);
        return ResponseEntity.ok("លុបបេក្ខជនរួចរាល់!");
    }

    @GetMapping("/logs")
    public List<AuditTrail> getSystemLogs() {
        return auditRepository.findAllByOrderByActionTimestampDesc();
    }
}