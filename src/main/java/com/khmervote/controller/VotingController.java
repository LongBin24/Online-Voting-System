package com.khmervote.controller;

import com.khmervote.entity.Candidate;
import com.khmervote.repository.CandidateRepository;
import com.khmervote.security.VoterUserDetails;
import com.khmervote.service.VotingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/votes")
public class VotingController {

    @Autowired
    private CandidateRepository candidateRepository;

    @GetMapping("/candidates")
    public List<Candidate> getCandidatesForVoter() {
        return candidateRepository.findAll();
    }

    @Autowired
    private VotingService votingService;


    @PostMapping("/cast")
    public ResponseEntity<?> castVote(@RequestBody Map<String, Long> request, Authentication auth) {

        if (auth == null || !auth.isAuthenticated()) {
            return ResponseEntity.status(401).body("សូមចូលប្រើប្រាស់ប្រព័ន្ធជាមុនសិន!");
        }

        try {
            VoterUserDetails userDetails = (VoterUserDetails) auth.getPrincipal();
            Long voterId = userDetails.getVoterId();

            Long candidateId = request.get("candidateId");

            String receiptToken = votingService.castVote(voterId, candidateId);

            return ResponseEntity.ok(Map.of(
                    "status", "success",
                    "message", "ការបោះឆ្នោតជោគជ័យ!",
                    "receiptToken", receiptToken
            ));

        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of(
                    "status", "error",
                    "message", e.getMessage()
            ));
        }
    }
}