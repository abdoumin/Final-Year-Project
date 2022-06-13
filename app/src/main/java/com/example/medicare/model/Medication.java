package com.example.medicare.model;

import androidx.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Medication {
    int id;

    public int getId() {
        return id;
    }

    @SerializedName("date")
    private String Date;
    @SerializedName("name")
    private String Name;
    @SerializedName("frequency")
    private String Frequency;
    @SerializedName("dosage")
    private String Dosage;
    @SerializedName("meal")
    private String Meal;
    @SerializedName("days")
    private String Days;
    @SerializedName("endDate")
    private String EndDate;
    private Prescription prescription;

    private List<HeureDePrise> heuresDePrise;

    public void setId(int id) {
        this.id = id;
    }

    public List<HeureDePrise> getHeuresDePrise() {
        return heuresDePrise;
    }

    public void setHeuresDePrise(List<HeureDePrise> heuresDePrise) {
        this.heuresDePrise = heuresDePrise;
    }

    public Medication() {
    }

    public Medication(String date, String name, String frequency, String dosage, String meal, String days, String endDate) {
        Date = date;
        Name = name;
        Frequency = frequency;
        Dosage = dosage;
        Meal = meal;
        Days = days;
        EndDate = endDate;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getFrequency() {
        return Frequency;
    }

    public void setFrequency(String frequency) {
        Frequency = frequency;
    }

    public String getDosage() {
        return Dosage;
    }

    public void setDosage(String dosage) {
        Dosage = dosage;
    }

    public String getMeal() {
        return Meal;
    }

    public void setMeal(String meal) {
        Meal = meal;
    }

    public String getDays() {
        return Days;
    }

    public void setDays(String days) {
        Days = days;
    }

    public String getEndDate() {
        return EndDate;
    }

    public void setEndDate(String endDate) {
        EndDate = endDate;
    }

    public Prescription getPrescription() {
        return prescription;
    }

    public void setPrescription(Prescription prescription) {
        this.prescription = prescription;
    }

    @NonNull
    @Override
    public String toString() {
        return Name+" "+Date+" "+Dosage+" "+Frequency   ;
    }
}
