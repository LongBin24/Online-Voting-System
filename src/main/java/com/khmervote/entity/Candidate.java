package com.khmervote.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "CANDIDATE")
@Data
public class Candidate {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String party;
}
