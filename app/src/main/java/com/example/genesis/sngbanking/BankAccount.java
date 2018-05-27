package com.example.genesis.sngbanking;

import android.widget.Toast;

import java.io.Serializable;

public class BankAccount implements Serializable {

    private String fName;
    private String lName;
    private String email;
    private String password;
    private long accountNumber;
    private double balance;


    private static long lastAccountNumber = 4823521350L;

    public BankAccount(String fName, String lName,
                       String email, String password,
                       double intialBalance) {
        this.fName = fName;
        this.lName = lName;
        this.email = email;
        this.password = password;
        this.balance = intialBalance;
        this.accountNumber = ++lastAccountNumber;
    }

    public BankAccount(String fName, String lName,
                       String email, String password,
                       String accountNumber, double intialBalance) {
        this.fName = fName;
        this.lName = lName;
        this.email = email;
        this.password = password;
        this.accountNumber = Long.parseLong(accountNumber);
        this.balance = intialBalance;

    }


    public void deposit(double depositAmount) {
        balance += depositAmount;
    }

    public boolean withdraw(double withdrawAmount) {
        if (withdrawAmount > balance) {
            System.out.println("Insufficient Funds!!!");
            return false;
        } else {
            balance -= withdrawAmount;
            return true;
        }
    }

    public long getAccNumber() {
        return accountNumber;
    }

    public double getBalance() {
        return balance;
    }

    public String transfer(BankAccount a, BankAccount b, double amount) {
        if (a.getBalance() < amount)
            return "Insufficient Funds!!!";
        if (b == null)
            return "Wrong Account Number!";

        // check b is exists
        // add code here

        a.withdraw(amount);
        b.deposit(amount);

        return "Completed";
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;

    }

    public String getFullName() {
        return fName + " " + lName;
    }

    public String getAccoutNumber() {
        return Long.toString(accountNumber);
    }

    public String getfName() {
        return fName;
    }


    public String getlName() {
        return lName;
    }
}
