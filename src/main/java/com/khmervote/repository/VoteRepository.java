package com.khmervote.repository;

import com.khmervote.entity.Vote;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface VoteRepository extends JpaRepository<Vote, Long> {
    Optional<Vote> findTopByOrderByIdDesc();

    Optional<Vote> findByVoteHash(String voteHash);
}
