package com.example.genesis.sngbanking;

import java.io.Serializable;

public class Authen implements Serializable {
    private String accountNumber;
    private String email;
    private String password;

    public Authen(String accountNumber, String email, String password) {
        this.accountNumber = accountNumber;
        this.email = email;
        this.password = password;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }
}
