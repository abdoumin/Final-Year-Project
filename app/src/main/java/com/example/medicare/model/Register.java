package com.example.medicare.model;

import androidx.annotation.NonNull;

public class Register {
    String firstName,lastName,email,Register;

    public Register() {
    }

    public Register(String firstName, String lastName, String email, String register) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        Register = register;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getRegister() {
        return Register;
    }

    public void setRegister(String register) {
        Register = register;
    }

    @NonNull
    @Override
    public String toString() {
        return firstName +" " + lastName;
    }
}
