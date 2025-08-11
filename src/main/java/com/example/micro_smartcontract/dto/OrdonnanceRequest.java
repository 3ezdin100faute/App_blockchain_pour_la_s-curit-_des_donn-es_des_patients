package com.example.micro_smartcontract.dto;


import lombok.Data;
import java.util.List;

@Data
public class OrdonnanceRequest {
    private Long patientId;
    private String nomPatient;
    private String maladie;
    private List<String> medicaments;
    private String docteur;
}