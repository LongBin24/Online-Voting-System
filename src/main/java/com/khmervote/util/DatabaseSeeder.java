package com.khmervote.util;

import com.khmervote.entity.Candidate;
import com.khmervote.repository.CandidateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DatabaseSeeder implements CommandLineRunner {

    @Autowired
    private CandidateRepository candidateRepository;

    @Override
    public void run(String... args) throws Exception {
        if (candidateRepository.count() == 0) {
            candidateRepository.save(new Candidate(null, "លោក រ៉ូមីអូ", "គណបក្ស ក"));
            candidateRepository.save(new Candidate(null, "កញ្ញា ណាណា", "គណបក្ស ខ"));

            System.out.println("✅ Initial candidates have been added to the database!");
        }
    }
}
