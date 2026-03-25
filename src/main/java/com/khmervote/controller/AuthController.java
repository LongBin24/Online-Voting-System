package com.khmervote.controller;

import com.khmervote.entity.Voter;
import com.khmervote.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    @Autowired
    private AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody Voter voter){
        try{
            String message = authService.registerVoter(voter);
            return  ResponseEntity.ok(Map.of("message",message));
        }catch(Exception e){
            return  ResponseEntity.badRequest().body(Map.of("error",e.getMessage()));
        }
    }

    @PostMapping("/verify")
    public ResponseEntity<?> verify(@RequestBody Map<String,String>request){
        try{
            String email = request.get("email");
            String code =  request.get("code");
            String message = authService.verifyAccount(email,code);
            return ResponseEntity.ok(Map.of("message",message));
        }catch (Exception e){
            return ResponseEntity.badRequest().body(Map.of("error",e.getMessage()));
        }
    }
}
