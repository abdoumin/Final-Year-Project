package com.example.medicare.model;

import java.util.List;

public class HeureDePrise {

    private Integer id;
    private String heuredeprise;

    private Medication medication;

    public HeureDePrise(String heuredeprise) {
        this.heuredeprise = heuredeprise;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getHeuredeprise() {
        return heuredeprise;
    }

    public void setHeuredeprise(String heuredeprise) {
        this.heuredeprise = heuredeprise;
    }

    public Medication getMedication() {
        return medication;
    }

    public void setMedication(Medication medication) {
        this.medication = medication;
    }
}
