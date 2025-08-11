package com.example.micro_smartcontract.Repository;

import com.example.micro_smartcontract.entity.OrdonnanceEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface OrdonnanceRepository extends JpaRepository<OrdonnanceEntity, Long> {
    Optional<OrdonnanceEntity> findByPatientId(Long patientId);



}
