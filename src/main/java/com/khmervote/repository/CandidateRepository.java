package com.khmervote.repository;

import com.khmervote.dto.CandidateResult;
import com.khmervote.entity.Candidate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CandidateRepository extends JpaRepository<Candidate, Long> {

    @Query("SELECT new com.khmervote.dto.CandidateResult(c.name, c.party, COUNT(v.id)) " +
            "FROM Candidate c LEFT JOIN Vote v ON c.id = v.candidateId " +
            "GROUP BY c.id, c.name, c.party " +
            "ORDER BY COUNT(v.id) DESC")
    List<CandidateResult> getVotingResults();
}