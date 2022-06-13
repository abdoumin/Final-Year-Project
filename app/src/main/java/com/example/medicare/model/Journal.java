package com.example.medicare.model;


import com.google.gson.annotations.SerializedName;

import java.time.LocalDate;
import java.time.LocalDateTime;


public class Journal {


    private Integer id;


    private Medication medication;

    String status;


    String date ;

    String time;

    public Journal() {
    }

    public Journal(String status, String date, String time) {
        this.status = status;
        this.date = date;
        this.time = time;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Medication getMedication() {
        return medication;
    }

    public void setMedication(Medication medication) {
        this.medication = medication;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
