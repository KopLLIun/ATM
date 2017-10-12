package org.koplllun.atm;

import javax.xml.bind.annotation.*;

@XmlType(propOrder = {"id", "pass", "balance"})
@XmlRootElement(name = "CashCard")
class CashCard {

    private String id;
    private String pass;
    private int balance;

    public CashCard(String id, String pass, int balance) {
        super();
        this.id = id;
        this.pass = pass;
        this.balance = balance;
    }

    public CashCard() {
        super();
    }

    @XmlElement
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @XmlElement
    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    @XmlElement
    public int getBalance() {
        return balance;
    }

    public void setBalance(int balance) {
        this.balance = balance;
    }

    public void deposit(int money) {
        balance += money;
    }

    public void withdraw(int money) {
        balance -= money;
    }

    public String toString() {
        return "ID = " + id + " Password: " + pass + " Balance: " + balance;
    }
}
