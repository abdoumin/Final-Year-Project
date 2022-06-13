package com.example.medicare.model;


import com.example.medicare.api.model.Patient;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.time.LocalDate;
import java.util.List;

public class Prescription {

    @SerializedName("id")
    private int  id;

    //  **@JsonIgnore
    @SerializedName("patient")
    private Patient patient;
    @SerializedName("doctor")
    private Doctor doctor;

    @SerializedName("medications")
    private List<Medication> medications;

    @SerializedName("prescriptionDate")
    private String prescriptionDate;
    @SerializedName("refillData")
    private String refillData;


    public Prescription() {
    }

    public Prescription(
            String prescriptionDate,
            String refillData
    ) {
        this.prescriptionDate = prescriptionDate;
        this.refillData = refillData;

    }

    public Prescription(Patient patient, Doctor doctor, List<Medication> medications, String prescriptionDate, String refillData) {
        this.patient = patient;
        this.doctor = doctor;
        this.medications = medications;
        this.prescriptionDate = prescriptionDate;
        this.refillData = refillData;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Patient getPatient() {
        return patient;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }

    public Doctor getDoctor() {
        return doctor;
    }

    public void setDoctor(Doctor doctor) {
        this.doctor = doctor;
    }

    public String getPrescriptionDate() {
        return prescriptionDate;
    }

    public void setPrescriptionDate(String prescriptionDate) {
        this.prescriptionDate = prescriptionDate;
    }

    public String getRefillData() {
        return refillData;
    }

    public void setRefillData(String refillData) {
        this.refillData = refillData;
    }


    public List<Medication> getMedications() {
        return medications;
    }

    public void setMedications(List<Medication> medications) {
        this.medications = medications;
    }

}

