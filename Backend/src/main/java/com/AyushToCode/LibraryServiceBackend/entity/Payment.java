package com.AyushToCode.LibraryServiceBackend.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "payment")
@Data
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String userEmail;

    private double amount;
}
