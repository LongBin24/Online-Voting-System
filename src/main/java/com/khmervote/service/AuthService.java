package com.khmervote.service;


import com.khmervote.entity.Verification;
import com.khmervote.entity.Voter;
import com.khmervote.repository.VerificationRepository;
import com.khmervote.repository.VoterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
public class AuthService {

    @Autowired private VoterRepository voterRepository;
    @Autowired private VerificationRepository verificationRepository;
    @Autowired private EmailService emailService;
    @Autowired private PasswordEncoder passwordEncoder;

    @Transactional
    public String registerVoter(Voter voter) {

        if(voterRepository.findByEmail(voter.getEmail()).isPresent()) {
            throw new RuntimeException("This email is already registered!");
        }

        voter.setRole("ROLE_VOTER");
        voter.setPassword(passwordEncoder.encode(voter.getPassword()));
        voter.setVerified(false);
        Voter savedVoter = voterRepository.save(voter);

        String otpCode = String.valueOf((int)((Math.random() * 900000) + 100000));

        Verification v = new Verification();
        v.setVoterId(savedVoter.getId());
        v.setCode(otpCode);
        v.setType("REGISTER");
        v.setExpiry(LocalDateTime.now().plusMinutes(15));
        verificationRepository.save(v);

        emailService.sendVerificationEmail(voter.getEmail(), otpCode);

        return "Registration successful! Please check your email for the verification code.";
    }

    @Transactional
    public String verifyAccount(String email, String code) {
        Voter voter = voterRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Voter not found!"));

        Verification v = verificationRepository.findByVoterIdAndCode(voter.getId(), code)
                .orElseThrow(() -> new RuntimeException("Invalid verification code!"));

        if (v.getExpiry().isBefore(LocalDateTime.now())) {
            throw new RuntimeException("Verification code has expired!");
        }

        voter.setVerified(true);
        voterRepository.save(voter);

        verificationRepository.delete(v);

        return "Your account has been successfully verified! You can now log in and vote.";
    }
}
