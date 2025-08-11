package com.example.docteur.Repository;

import com.example.docteur.entity.Ordonnance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ordonnanceRepo extends JpaRepository<Ordonnance, Long> {
    List<Ordonnance> findByPatientId(Long patientId);
}
