package com.khmervote.controller;

import com.khmervote.dto.CandidateResult;
import com.khmervote.repository.CandidateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

public class DashboardController {

    @Autowired
    private CandidateRepository candidateRepository;

    @GetMapping("/results")
    public List<CandidateResult> getResults() {
        return candidateRepository.getVotingResults();
    }
}
