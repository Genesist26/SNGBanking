package com.example.genesis.sngbanking;

public class BankAccount {

    private String fName;
    private String lName;
    private String email;
    private String password;
    private double balance;
    private int accountNumber;

    private static int lastAccountNumber = 0;

    public BankAccount(String fName, String lName, int accountNumber,
                       String email, String password, double intialBalance)
    {
        this.fName = fName;
        this.lName = lName;
        this.accountNumber = accountNumber;
        this.email = email;
        this.password = password;

        balance = intialBalance;

        accountNumber = lastAccountNumber + 1;
        lastAccountNumber = accountNumber;
    }


    public void deposit(double depositAmount)
    {
        balance += depositAmount;
    }

    public boolean withdraw(double withdrawAmount)
    {
        if (withdrawAmount > balance){
            System.out.println("Insufficient Funds!!!");
            return false;
        } else {
            balance -= withdrawAmount;
            return true;
        }
    }

    public int getNumber()
    {
        return accountNumber;
    }

    public double getBalance()
    {
        return balance;
    }

    public boolean transfer(BankAccount a, BankAccount b, double amount)
    {
        if(a.getBalance() < amount)
            return false;

        // check b is exists

        a.withdraw(amount);
        b.deposit(amount);

        return true;
    }

}