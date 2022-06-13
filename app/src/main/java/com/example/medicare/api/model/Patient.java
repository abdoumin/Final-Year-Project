package com.example.medicare.api.model;

public class Patient {
        private String firstName,
                lastName,
                age,
                gender,
                username,
                password;
        private Integer id;

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getAge() {
        return age;
    }

    public String getGender() {
        return gender;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public Patient() {
        }

        public Patient(String firstName, String lastName,String username,
                          String password,String age, String gender) {
            this.firstName = firstName;
            this.lastName = lastName;
            this.age = age;
            this.username = username;
            this.password = password;
            this.gender = gender;
        }

    public Integer getId() {
        return id;
    }
}
