package com.example.medicare.model;

public class MedicalExpense {
    private String consultationCost,testCost,pharmacyCost,reportCost,otherCost,tax,totalCost,PaymentPayed,PaymentLeft,modeOfPayment;

    public MedicalExpense() {
    }

    public MedicalExpense(String consultationCost, String testCost, String pharmacyCost, String reportCost, String otherCost, String tax, String totalCost, String paymentPayed, String paymentLeft, String modeOfPayment) {
        this.consultationCost = consultationCost;
        this.testCost = testCost;
        this.pharmacyCost = pharmacyCost;
        this.reportCost = reportCost;
        this.otherCost = otherCost;
        this.tax = tax;
        this.totalCost = totalCost;
        PaymentPayed = paymentPayed;
        PaymentLeft = paymentLeft;
        this.modeOfPayment = modeOfPayment;

    }

    public String getConsultationCost() {
        return consultationCost;
    }

    public void setConsultationCost(String consultationCost) {
        this.consultationCost = consultationCost;
    }

    public String getTestCost() {
        return testCost;
    }

    public void setTestCost(String testCost) {
        this.testCost = testCost;
    }

    public String getPharmacyCost() {
        return pharmacyCost;
    }

    public void setPharmacyCost(String pharmacyCost) {
        this.pharmacyCost = pharmacyCost;
    }

    public String getReportCost() {
        return reportCost;
    }

    public void setReportCost(String reportCost) {
        this.reportCost = reportCost;
    }

    public String getOtherCost() {
        return otherCost;
    }

    public void setOtherCost(String otherCost) {
        this.otherCost = otherCost;
    }

    public String getTax() {
        return tax;
    }

    public void setTax(String tax) {
        this.tax = tax;
    }

    public String getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(String totalCost) {
        this.totalCost = totalCost;
    }

    public String getPaymentPayed() {
        return PaymentPayed;
    }

    public void setPaymentPayed(String paymentPayed) {
        PaymentPayed = paymentPayed;
    }

    public String getPaymentLeft() {
        return PaymentLeft;
    }

    public void setPaymentLeft(String paymentLeft) {
        PaymentLeft = paymentLeft;
    }

    public String getModeOfPayment() {
        return modeOfPayment;
    }

    public void setModeOfPayment(String modeOfPayment) {
        this.modeOfPayment = modeOfPayment;
    }
}
