package com.example.micro_patient.Service;

import com.example.micro_patient.entity.Patient;

import java.util.List;

public interface patientinter {
    public Patient savePatient(Patient patient);
    public void updatePatient(Patient patient);
    public void deletePatient(long id);
    public Patient getPatientById(long id);
    public List<Patient> getAllPatient();
}
