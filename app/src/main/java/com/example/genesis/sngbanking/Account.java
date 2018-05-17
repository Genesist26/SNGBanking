package com.example.genesis.sngbanking;

import java.util.ArrayList;

public class Account {

    private ArrayList<BankAccount> list;

    public Account(){
        list = new ArrayList<>();
    }

    public void addAccount(String fName, String lName, int accountNumber,
                           String email, String password, double intialBalance) {

        BankAccount anAcc = new BankAccount(fName, lName, accountNumber,
                email, password, intialBalance);
        list.add(anAcc);
    }

    public BankAccount search(String loginEmail, String loginPass) {
        for(int i=0; i<BankAccount.getLastAccountNumber(); i++) {
            BankAccount anAcc = (BankAccount) list.get(i);
            if(anAcc.getEmail().equals(loginEmail) && anAcc.getPassword().equals(loginPass))
                return anAcc;
        }

        return null;
    }
}
