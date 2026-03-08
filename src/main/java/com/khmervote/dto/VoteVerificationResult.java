package com.khmervote.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class VoteVerificationResult {
    private boolean exists;
    private String voteHash;
    private String previousHash;
    private String timestamp;
    private String message;
}
