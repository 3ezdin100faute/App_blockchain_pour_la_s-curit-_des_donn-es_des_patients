package com.example.micro_smartcontract.service;

import com.example.micro_smartcontract.OrdonnanceContract;
import com.example.micro_smartcontract.Repository.OrdonnanceRepository;
import com.example.micro_smartcontract.entity.OrdonnanceEntity;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.web3j.abi.datatypes.Address;
import org.web3j.abi.datatypes.Utf8String;
import org.web3j.abi.datatypes.generated.Uint256;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.tuples.generated.Tuple6;
import org.web3j.tx.gas.DefaultGasProvider;

import java.math.BigInteger;
import java.time.LocalDate;
import java.util.*;

@Service
@RequiredArgsConstructor
public class SmartContractService {

    private final Web3j web3j;
    private final Credentials credentials;
    private final OrdonnanceRepository ordonnanceRepository;
    private final RestTemplate restTemplate;
    private OrdonnanceContract ordonnanceContract; // injecté dans le service

    private final String contractAddress = "0x1B41eEEFaB6680E7E2faa1B720719e8e50b8e0D4";

    private OrdonnanceContract loadContract() {
        return OrdonnanceContract.load(contractAddress, web3j, credentials, new DefaultGasProvider());
    }

    public TransactionReceipt creerOrdonnance(Long patientId, String nomPatient, String maladie, List<String> medicaments, String docteur) throws Exception {
        if (nomPatient == null || nomPatient.isEmpty()) {
            throw new IllegalArgumentException("nomPatient ne peut pas être null ou vide");
        }
        if (maladie == null || maladie.isEmpty()) {
            throw new IllegalArgumentException("maladie ne peut pas être null ou vide");
        }
        if (medicaments == null || medicaments.isEmpty()) {
            throw new IllegalArgumentException("La liste de médicaments ne peut pas être vide");
        }

        maladie = maladie.toLowerCase().trim();

        // Filtrer null et chaînes vides, normaliser en minuscules
        List<String> medicamentsNettoyes = medicaments.stream()
                .filter(Objects::nonNull)
                .map(String::trim)
                .filter(s -> !s.isEmpty())
                .map(String::toLowerCase)
                .toList();

        if (medicamentsNettoyes.isEmpty()) {
            throw new IllegalArgumentException("La liste de médicaments après nettoyage est vide");
        }

        String medicamentPrincipal = medicamentsNettoyes.get(0);

        boolean iaCompatible = verifierCompatibiliteIA(maladie, medicamentPrincipal);
        if (!iaCompatible) {
            throw new IllegalArgumentException("❌ Médicament incompatible avec la maladie selon l'IA !");
        }

        boolean scCompatible = verifierCompatibiliteSC(maladie, medicamentPrincipal);
        if (!scCompatible) {
            ajouterCompatibilite(maladie, medicamentPrincipal);
            scCompatible = verifierCompatibiliteSC(maladie, medicamentPrincipal);
            if (!scCompatible) {
                throw new IllegalStateException("❌ Échec de l'ajout de la compatibilité dans le smart contract !");
            }
        }

        // Envoie uniquement le 1er médicament à la blockchain
        TransactionReceipt receipt = loadContract().creerOrdonnance(
                BigInteger.valueOf(patientId),
                nomPatient,
                maladie,
                Collections.singletonList(medicamentPrincipal)
        ).send();

        // Sauvegarde de tous les médicaments en JSON
        ObjectMapper mapper = new ObjectMapper();
        String medicamentsJson = mapper.writeValueAsString(Map.of("medicaments", medicamentsNettoyes));

        OrdonnanceEntity ordonnance = OrdonnanceEntity.builder()
                .patientId(patientId)
                .maladie(maladie)
                .dateCreation(LocalDate.now())
                .docteur(docteur)
                .medicament(medicamentsJson) // JSON string avec plusieurs médicaments
                .build();

        ordonnanceRepository.save(ordonnance);

        return receipt;
    }

    public boolean verifierCompatibilite(String maladie, String medicament) {
        try {
            System.out.println("[DEBUG] verifierCompatibilite called with maladie='" + maladie + "', medicament='" + medicament + "'");
            OrdonnanceContract contract = loadContract();
            boolean result = contract.verifierCompatibilite(maladie, medicament).send();
            System.out.println("[DEBUG] verifierCompatibilite result: " + result);
            return result;
        } catch (Exception e) {
            System.out.println("[ERROR] verifierCompatibilite exception: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    public OrdonnanceDTO getOrdonnance(BigInteger id) throws Exception {
        OrdonnanceContract contract = loadContract();
        var result = contract.getOrdonnance(id).send();
        return new OrdonnanceDTO(result.component1(), result.component2(), result.component3().toString(), result.component4());
    }

    public void ajouterCompatibilite(String maladie, String medicament) throws Exception {
        OrdonnanceContract contract = loadContract();
        contract.setCompatibilite(maladie, Collections.singletonList(medicament)).send();
    }

    public record OrdonnanceDTO(String nomPatient, String maladie, String medicament, String docteur) {
    }

    public boolean verifierCompatibiliteIA(String maladie, String medicament) {
        String url = "http://localhost:5001/verifier";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        Map<String, String> payload = Map.of(
                "maladie", maladie,
                "medicament", medicament
        );

        HttpEntity<Map<String, String>> request = new HttpEntity<>(payload, headers);

        ResponseEntity<Map> response = restTemplate.postForEntity(url, request, Map.class);

        if (response.getStatusCode().is2xxSuccessful() && response.getBody() != null) {
            return Boolean.TRUE.equals(response.getBody().get("compatible"));
        }

        return false;
    }

    public List<Map<String, Object>> recupererToutesLesOrdonnances() throws Exception {
        Tuple6<
                List<Uint256>, List<Uint256>, List<Utf8String>, List<Utf8String>, List<Utf8String>, List<Address>
                > result = OrdonnanceContract.getAllOrdonnances(ordonnanceContract).send();

        List<Map<String, Object>> ordonnances = new ArrayList<>();
        for (int i = 0; i < result.component1().size(); i++) {
            Map<String, Object> o = new HashMap<>();
            o.put("id", result.component1().get(i).getValue());
            o.put("patientId", result.component2().get(i).getValue());
            o.put("nomPatient", result.component3().get(i).getValue());
            o.put("maladie", result.component4().get(i).getValue());
            o.put("medicament", result.component5().get(i).getValue());
            o.put("docteur", result.component6().get(i).getValue());

            ordonnances.add(o);
        }
        return ordonnances;
    }

    public boolean verifierCompatibiliteSC(String maladie, String medicament) throws Exception {
        maladie = maladie.toLowerCase().trim();
        medicament = medicament.toLowerCase().trim();

        return loadContract().verifierCompatibilite(maladie, medicament).send();
    }
    public OrdonnanceEntity getDerniereOrdonnanceParPatientId(Long patientId) {
        return ordonnanceRepository.findByPatientId(patientId)
                .orElseThrow(() -> new RuntimeException("Aucune ordonnance trouvée pour ce patient"));
    }

}
