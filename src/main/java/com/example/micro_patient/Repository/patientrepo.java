package com.example.micro_patient.Repository;

import com.example.micro_patient.entity.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface patientrepo extends JpaRepository<Patient, Long> {
    Optional<Patient> findByNomAndPrenom(String nom, String prenom);
    Optional<Patient> findByNumeroDossier(String numeroDossier);


}
