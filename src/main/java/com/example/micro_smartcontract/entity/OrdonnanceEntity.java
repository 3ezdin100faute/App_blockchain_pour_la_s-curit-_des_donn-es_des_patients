package com.example.micro_smartcontract.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrdonnanceEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long patientId; // ID du patient

    private String maladie;

    private LocalDate dateCreation;

    private String docteur;

    @Lob
    private String medicament;
}
