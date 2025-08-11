package com.example.docteur.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Docteur {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idDocteur;

    private String nom;

    private String prenom;

    private String specialite;

    private String email;

    private String telephone;

    private String hopital;
}