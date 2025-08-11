package com.example.docteur.Service;
import com.example.docteur.Repository.ordonnanceRepo;
import com.example.docteur.entity.Ordonnance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class ordonnanceser implements ordonnanceinter {
    private final ordonnanceRepo ordonnanceRepo;


    public ordonnanceser(ordonnanceRepo ordonnanceRepo) {
        this.ordonnanceRepo = ordonnanceRepo;
    }

    @Override
    public Ordonnance Ajouter(Ordonnance ordonnance) {
        return ordonnanceRepo.save(ordonnance);

    }

    @Override
    public void Modifier(Ordonnance ordonnance) {
        if (ordonnanceRepo.existsById(ordonnance.getId())) {
            ordonnanceRepo.save(ordonnance);
        }

    }

    @Override
    public void Supprimer(long id) {
        if (ordonnanceRepo.existsById(id)) {
            ordonnanceRepo.deleteById(id);
        }

    }

    @Override
    public Ordonnance getordonnance(long id) {
        return ordonnanceRepo.findById(id).orElse(null);
    }

    @Override
    public List<Ordonnance> getAllordonnance() {
        return ordonnanceRepo.findAll();
    }

    @Override
    public List<Ordonnance> getOrdonnancesByPatientId(Long patientId) {
        return ordonnanceRepo.findByPatientId(patientId);
    }
}
