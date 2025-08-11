package com.example.docteur.controller;

import com.example.docteur.entity.Ordonnance;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.docteur.Service.ordonnanceser;

import java.util.List;

@RestController
@RequestMapping("/ordonnance")
@AllArgsConstructor
@CrossOrigin("*")
public class ordonnancecontroller {
    @Autowired
    private ordonnanceser ordonnanceser;
    @PostMapping("/add")
    public Ordonnance addordonnance(@RequestBody Ordonnance ordonnance){
        return ordonnanceser.Ajouter(ordonnance);
    }
    @PutMapping("/update")
    public void updateordonnance(@RequestBody Ordonnance ordonnance){
         ordonnanceser.Modifier(ordonnance);
    }
    @DeleteMapping("/delete/{id}")
    public void deleteordonnance(@PathVariable Long id){
        ordonnanceser.Supprimer(id);
    }
    @GetMapping("/get/{id}")
    public Ordonnance getordonnance(@PathVariable Long id){
        return ordonnanceser.getordonnance(id);
    }
    @GetMapping("/getAll")
    public List<Ordonnance> getAllordonnance(){
        return ordonnanceser.getAllordonnance();
    }
    @GetMapping("/patient/{patientId}")
    public ResponseEntity<List<Ordonnance>> getOrdonnancesByPatientId(@PathVariable Long patientId) {
        List<Ordonnance> ordonnances = ordonnanceser.getOrdonnancesByPatientId(patientId);
        return ResponseEntity.ok(ordonnances);
    }

}
