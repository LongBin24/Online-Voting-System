package com.khmervote.service;

import com.khmervote.entity.Voter;
import com.khmervote.repository.VoterRepository;
import com.khmervote.security.VoterUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class VoterUserDetailsService implements UserDetailsService {
    @Autowired
    private VoterRepository voterRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Voter voter = voterRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("រកមិនឃើញ Email នេះទេ!"));
        return new VoterUserDetails(voter);
    }
}
