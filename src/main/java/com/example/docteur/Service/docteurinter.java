package com.example.docteur.Service;

import com.example.docteur.entity.Docteur;

import java.util.List;

public interface docteurinter {
    public Docteur Ajouter(Docteur docteur);
    public void Modifier(Docteur docteur);
    public void Supprimer(long id);
    public Docteur getdocteur (long id);
    public List<Docteur> getAlldocteur();
}

