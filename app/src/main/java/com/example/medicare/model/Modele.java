package com.example.medicare.model;

import com.google.gson.annotations.SerializedName;

import java.time.LocalDate;
import java.util.List;

import javax.xml.namespace.QName;


public class Modele {


    private Integer id;


    private Prescription prescription;

    @SerializedName("tag")
    private String TAG;
    private String description;

    public Modele() {
    }

    public Modele(String TAG, String description) {
        this.TAG = TAG;
        this.description = description;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Prescription getPrescription() {
        return prescription;
    }

    public void setPrescription(Prescription prescription) {
        this.prescription = prescription;
    }

    public String getTAG() {
        return TAG;
    }

    public void setTAG(String TAG) {
        this.TAG = TAG;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
