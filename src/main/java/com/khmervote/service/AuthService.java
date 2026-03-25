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
            throw new RuntimeException("Email មួយនេះធ្លាប់បានចុះឈ្មោះរួចហើយ!!");
        }

        voter.setRole("ROLE_VOTER");
        voter.setPassword(passwordEncoder.encode(voter.getPassword()));
        voter.setVerified(false);
        voter.setVoted(false);
        voter.setLocked(false);
        voter.setFailedAttempts(0);
        Voter savedVoter = voterRepository.save(voter);

        String otpCode = String.valueOf((int)((Math.random() * 900000) + 100000));

        Verification v = new Verification();
        v.setVoterId(savedVoter.getId());
        v.setCode(otpCode);
        v.setType("REGISTER");
        v.setExpiry(LocalDateTime.now().plusMinutes(15));
        verificationRepository.save(v);

        emailService.sendVerificationEmail(voter.getEmail(), otpCode);

        return "ចុះឈ្មោះជោគជ័យ! សូមពិនិត្យមើលកូដ OTP ក្នុង Email របស់អ្នក";
    }

    @Transactional
    public String verifyAccount(String email, String code) {
        Voter voter = voterRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("រកមិនឃើញអ្នកបោះឆ្នោតទេ!"));

        Verification v = verificationRepository.findByVoterIdAndCode(voter.getId(), code)
                .orElseThrow(() -> new RuntimeException("កូដផ្ទៀងផ្ទាត់មិនត្រឹមត្រូវទេសូមពិនិត្យម្តងទៀត!"));

        if (v.getExpiry().isBefore(LocalDateTime.now())) {
            throw new RuntimeException("កូដផ្ទៀងផ្ទាត់មួយនេះបានផុតកំណត់ហើយ!");
        }

        voter.setVerified(true);
        voterRepository.save(voter);

        verificationRepository.delete(v);

        return "គណនីរបស់អ្នកត្រូវបានផ្ទៀងផ្ទាត់ដោយជោគជ័យ! ឥឡូវនេះអ្នកអាចចូលប្រើប្រាស់ និងបោះឆ្នោតបានហើយ!!";
    }
}