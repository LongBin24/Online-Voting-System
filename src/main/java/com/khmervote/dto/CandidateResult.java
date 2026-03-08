package com.khmervote.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CandidateResult {
    private String candidateName;
    private String party;
    private Long voteCount;
}
