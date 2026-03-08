package com.khmervote.controller;

import com.khmervote.entity.AuditTrail;
import com.khmervote.repository.AuditTrailRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import com.khmervote.security.VoterUserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private AuditTrailRepository auditRepository;

    @GetMapping("/logs")
    public List<AuditTrail> getSystemLogs(Authentication auth) {

        if (auth != null) {
            VoterUserDetails user = (VoterUserDetails) auth.getPrincipal();
            System.out.println("Admin access by: " + user.getUsername());
        }

        return auditRepository.findAllByOrderByActionTimestampDesc();
    }
}
