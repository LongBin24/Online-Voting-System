package com.khmervote.controller;

import com.khmervote.dto.CandidateResult;
import com.khmervote.repository.CandidateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
@RestController
@RequestMapping("/api/dashboard")
public class DashboardController {

    @Autowired
    private CandidateRepository candidateRepository;

    @GetMapping("/results")
    public List<CandidateResult> getResults() {
        return candidateRepository.getVotingResults();
    }
}
