package com.example.genesis.sngbanking;

import android.widget.Toast;

import java.io.Serializable;

public class BankAccount implements Serializable {

    private String fName;
    private String lName;
    private long accountNumber;
    private double balance;


    private static long lastAccountNumber = 4823521350L;

    public BankAccount(String fName, String lName,
                       double intialBalance) {
        this.fName = fName;
        this.lName = lName;
        this.balance = intialBalance;
        this.accountNumber = ++lastAccountNumber;
    }

    public BankAccount(String fName, String lName,
                       String accountNumber, double intialBalance) {
        this.fName = fName;
        this.lName = lName;
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

    public boolean transfer(BankAccount destAcc, double amount) {
        if (this.getBalance() < amount)
            return false;

        this.withdraw(amount);
        destAcc.deposit(amount);

        return true;
    }

    public String getFullName() {
        return fName + " " + lName;
    }

    public String getAccountNumber() {
        return Long.toString(accountNumber);
    }

    public String getfName() {
        return fName;
    }


    public String getlName() {
        return lName;
    }
}
