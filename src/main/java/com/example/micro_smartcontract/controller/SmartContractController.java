package com.example.micro_smartcontract.controller;
import java.util.Collections;
import java.util.List;

import com.example.micro_smartcontract.Repository.OrdonnanceRepository;
import com.example.micro_smartcontract.dto.OrdonnanceRequest;
import com.example.micro_smartcontract.entity.OrdonnanceEntity;
import com.example.micro_smartcontract.service.SmartContractService;
import com.example.micro_smartcontract.service.SmartContractService.OrdonnanceDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.web3j.protocol.core.methods.response.TransactionReceipt;

import java.math.BigInteger;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/smartcontract")
@RequiredArgsConstructor
@CrossOrigin("*")
public class SmartContractController {

    private final SmartContractService service;
    private final OrdonnanceRepository ordonnanceRepository;

    @PostMapping("/creer")
    public ResponseEntity<TransactionReceipt> creerOrdonnance(@RequestBody OrdonnanceRequest request) throws Exception {
        TransactionReceipt receipt = service.creerOrdonnance(
                request.getPatientId(),
                request.getNomPatient(),
                request.getMaladie(),
                request.getMedicaments(),
                request.getDocteur()
        );
        return ResponseEntity.status(HttpStatus.CREATED).body(receipt);
    }


    @GetMapping("/verifier")
    public boolean verifier(@RequestParam String maladie,
                            @RequestParam String medicament) throws Exception {
        return service.verifierCompatibilite(maladie, medicament);
    }

    @GetMapping("/get")
    public OrdonnanceDTO getOrdonnance(@RequestParam BigInteger id) throws Exception {
        return service.getOrdonnance(id);
    }

    @PostMapping("/ajouterCompatibilite")
    public void ajouterCompatibilite(@RequestParam String maladie,
                                     @RequestParam String medicament) throws Exception {
        service.ajouterCompatibilite(maladie, medicament);
    }
    @GetMapping("/patient/{patientId}")
    public ResponseEntity<OrdonnanceEntity> getOrdonnanceByPatientId(@PathVariable Long patientId) {
        Optional<OrdonnanceEntity> ordonnance = ordonnanceRepository.findByPatientId(patientId);

        if (ordonnance.isPresent()) {
            return ResponseEntity.ok(ordonnance.get());
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
    @GetMapping("/ordonnances")
    public ResponseEntity<List<Map<String, Object>>> getAllOrdonnances() {
        try {
            List<Map<String, Object>> ordonnances = service.recupererToutesLesOrdonnances();
            return ResponseEntity.ok(ordonnances);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Collections.singletonList(Map.of("erreur", e.getMessage())));
        }
    }

    @PutMapping("/ordonnance/update")
    public ResponseEntity<OrdonnanceEntity> updateOrdonnance(@RequestBody OrdonnanceEntity updatedOrdonnance) {
        if (updatedOrdonnance.getId() == null) {
            return ResponseEntity.badRequest().build();
        }

        Optional<OrdonnanceEntity> existing = ordonnanceRepository.findById(updatedOrdonnance.getId());
        if (existing.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        OrdonnanceEntity ord = existing.get();
        ord.setMaladie(updatedOrdonnance.getMaladie());
        ord.setDocteur(updatedOrdonnance.getDocteur());
        ord.setMedicament(updatedOrdonnance.getMedicament());

        ordonnanceRepository.save(ord);
        return ResponseEntity.ok(ord);
    }



}
