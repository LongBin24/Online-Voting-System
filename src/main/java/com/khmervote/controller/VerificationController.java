package com.khmervote.controller;

import com.khmervote.dto.VoteVerificationResult;
import com.khmervote.service.VotingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/verify")
public class VerificationController {

    @Autowired
    private VotingService votingService;

    @GetMapping("/{token}")
    public ResponseEntity<VoteVerificationResult> verify(@PathVariable String token) {
        VoteVerificationResult result = votingService.verifyVoteByToken(token);
        if (result.isExists()) {
            return ResponseEntity.ok(result);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(result);
        }
    }
}
