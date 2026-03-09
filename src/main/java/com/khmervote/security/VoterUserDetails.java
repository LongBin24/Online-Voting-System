package com.khmervote.security;

import com.khmervote.entity.Voter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

public class VoterUserDetails implements UserDetails {

    private final Voter voter;

    public VoterUserDetails(Voter voter) {
        this.voter = voter;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(voter.getRole()));
    }

    @Override
    public String getPassword() {
        return voter.getPassword();
    }

    @Override
    public String getUsername() {
        return voter.getEmail();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return voter.getLocked() ==null || !voter.getLocked();
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return voter.getVerified() != null && voter.getVerified();
    }

    public Long getVoterId() {
        return voter.getId();
    }

    public String getFullName() {
        return voter.getName();
    }
}