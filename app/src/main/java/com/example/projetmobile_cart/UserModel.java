package com.example.projetmobile_cart;

import android.net.Uri;

public class UserModel {
    private String firstName, lastName, number, email, password, bio;
    public UserModel() {
    }
    public UserModel(String firstName, String lastName, String number, String email, String password,String bio) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.number = number;
        this.email = email;
        this.password = password;
        this.bio = bio;
    }

    public UserModel(String firstName, String lastName, String number, String email ) {
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

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

}
