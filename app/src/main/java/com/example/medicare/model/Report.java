package com.example.medicare.model;

public class Report {
    private String date,condition, diagnosis,severity;

    public Report() {
    }

    public Report(String date, String condition, String diagnosis, String severity) {
        this.date = date;
        this.condition = condition;
        this.diagnosis = diagnosis;
        this.severity = severity;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getCondition() {
        return condition;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }

    public String getDiagnosis() {
        return diagnosis;
    }

    public void setDiagnosis(String diagnosis) {
        this.diagnosis = diagnosis;
    }

    public String getSeverity() {
        return severity;
    }

    public void setSeverity(String severity) {
        this.severity = severity;
    }
}
