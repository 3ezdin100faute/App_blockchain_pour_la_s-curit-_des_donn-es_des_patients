package com.example.micro_patient.controller;

import com.example.micro_patient.Repository.patientrepo;
import com.example.micro_patient.Service.patientservice;
import com.example.micro_patient.entity.Patient;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/patient")
@AllArgsConstructor
@CrossOrigin("*")
public class patientcontroller {

    @Autowired
    patientservice patientService;
    private final patientrepo patientRepository;

    @PostMapping("/save")
    public Patient savePatient(@RequestBody Patient patient) {
        return patientService.savePatient(patient);
    }

    @PutMapping("/update")
    public void updatePatient(@RequestBody Patient patient) {
        patientService.updatePatient(patient);
    }

    @DeleteMapping("/delete/{id}")
    public void deletePatient(@PathVariable("id") long id) {
        patientService.deletePatient(id);
    }

    @GetMapping("/get/{id}")
    public Patient getPatientById(@PathVariable("id") long id) {
        return patientService.getPatientById(id);
    }

    @GetMapping("/getall")
    public List<Patient> getAllPatient() {
        return patientService.getAllPatient();
    }
    @GetMapping("/search")
    public ResponseEntity<Patient> getByNomAndPrenom(@RequestParam String nom, @RequestParam String prenom) {
        return patientRepository.findByNomAndPrenom(nom, prenom)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    @GetMapping("/byNumeroDossier/{numeroDossier}")
    public ResponseEntity<Patient> getPatientByNumeroDossier(@PathVariable String numeroDossier) {
        Patient patient = patientService.getPatientByNumeroDossier(numeroDossier);
        return ResponseEntity.ok(patient);
    }

}
