package com.khmervote.service;

import com.khmervote.dto.VoteVerificationResult;
import com.khmervote.entity.Receipt;
import com.khmervote.entity.Vote;
import com.khmervote.entity.Voter;
import com.khmervote.repository.ReceiptRepository;
import com.khmervote.repository.VoteRepository;
import com.khmervote.repository.VoterRepository;
import com.khmervote.util.HashUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class VotingService {

    @Autowired
    private VoterRepository voterRepository;

    @Autowired
    private VoteRepository voteRepository;

    @Autowired
    private ReceiptRepository receiptRepository;

    @Transactional
    public String castVote(Long voterId, Long candidateId) {

        Voter voter = voterRepository.findById(voterId)
                .orElseThrow(() -> new RuntimeException("Voter not found!"));

        if (Boolean.TRUE.equals(voter.getVoted())) {
            throw new RuntimeException("You have already voted!");
        }
        voterRepository.save(voter);
        voter.setVoted(true);

        voter.getVoted();
        voterRepository.save(voter);

        String previousHash = voteRepository.findTopByOrderByIdDesc()
                .map(Vote::getVoteHash)
                .orElse("0000000000000000000000000000000000000000000000000000000000000000");

        LocalDateTime now = LocalDateTime.now();
        String dataToHash = candidateId + "|" + previousHash + "|" + now.toString();
        String newVoteHash = HashUtil.generateSHA256(dataToHash);


        Vote vote = new Vote();
        vote.setCandidateId(candidateId);
        vote.setVoteHash(newVoteHash);
        vote.setPreviousVoteHash(previousHash);
        vote.setVoteTimestamp(now);
        voteRepository.save(vote);

        String receiptToken = "KHMER-VOTE-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();

        Receipt receipt = new Receipt();
        receipt.setReceiptToken(receiptToken);
        receipt.setVoteHash(newVoteHash);
        receipt.setIssuedTimestamp(now);
        receiptRepository.save(receipt);


        return receiptToken;
    }
    public VoteVerificationResult verifyVoteByToken(String token) {

        return receiptRepository.findByReceiptToken(token)
                .map(receipt -> {

                    return voteRepository.findByVoteHash(receipt.getVoteHash())
                            .map(vote -> new VoteVerificationResult(
                                    true,
                                    vote.getVoteHash(),
                                    vote.getPreviousVoteHash(),
                                    vote.getVoteTimestamp().toString(),
                                    "សន្លឹកឆ្នោតរបស់អ្នកមានវត្តមានក្នុងប្រព័ន្ធ និងមានសុពលភាព!"
                            ))
                            .orElse(new VoteVerificationResult(false, null, null, null, "រកមិនឃើញទិន្នន័យសន្លឹកឆ្នោត!"));
                })
                .orElse(new VoteVerificationResult(false, null, null, null, "លេខបង្កាន់ដៃ (Token) មិនត្រឹមត្រូវ!"));
    }
}
