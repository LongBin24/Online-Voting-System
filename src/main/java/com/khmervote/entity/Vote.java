package com.khmervote.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;


@Entity
@Table(name = "VOTE")
@Data
public class Vote {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "candidate_id", nullable = false)
    private Long candidateId;

    @Column(name = "vote_hash", nullable = false, unique = true)
    private String voteHash;

    @Column(name = "previous_vote_hash", nullable = false)
    private String previousVoteHash;

    @Column(name = "vote_timestamp")
    private LocalDateTime voteTimestamp = LocalDateTime.now();
<<<<<<< HEAD
=======


>>>>>>> 37ad4674341e5aa8f3d45a1fb0f09801471bd402
}
