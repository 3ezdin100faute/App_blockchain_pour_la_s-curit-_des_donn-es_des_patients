package com.example.docteur.Service;

import com.example.docteur.entity.Docteur;
import com.example.docteur.entity.Ordonnance;

import java.util.List;

public interface ordonnanceinter {
    public Ordonnance Ajouter(Ordonnance ordonnance);
    public void Modifier(Ordonnance ordonnance);
    public void Supprimer(long id);
    public Ordonnance getordonnance (long id);
    public List<Ordonnance> getAllordonnance();
    public List<Ordonnance> getOrdonnancesByPatientId(Long patientId);
}
