package com.example.docteur.Repository;

import com.example.docteur.entity.Docteur;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface docteurRepo extends JpaRepository<Docteur,Long> {
}
