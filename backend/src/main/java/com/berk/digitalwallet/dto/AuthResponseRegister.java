package com.berk.digitalwallet.dto;

public class AuthResponseRegister {

    private String email;

    public AuthResponseRegister(String email) {
        this.email = email;
    }

    public String getEmail() {
        return email;
    }
}
