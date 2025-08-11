package com.example.docteur.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Ordonnance {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long patientId; // ID du patient récupéré via API (pas une relation directe)

    private String diagnostic;

    private LocalDate dateCreation;

    private Boolean valideeParSmartContract; // Résultat de validation via Remix IDE

    // Relation avec le médecin prescripteur
    @ManyToOne
    @JoinColumn(name = "docteur_id")
    private Docteur docteur;

    @ElementCollection
    private List<String> medicaments;

}