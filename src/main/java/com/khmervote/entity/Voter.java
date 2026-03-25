package com.khmervote.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Entity
@Table(name = "VOTER")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Voter {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String name;

    @Column(nullable = false, unique = true, length = 100)
    private String email;

    @Column(nullable = false)
    private String phone;

    @Column(nullable = false)
    private String password;

    @Column(name = "has_voted")
    private Boolean Voted = false;

    @Column(name = "is_verified")
    private Boolean Verified = false;

    @Column(name = "is_locked")
    private Boolean Locked = false;

    @Column(name = "failed_attempts")
    private Integer failedAttempts = 0;

    @Column(name="role",nullable = false)
    private String role = "ROLE_VOTER";
}
