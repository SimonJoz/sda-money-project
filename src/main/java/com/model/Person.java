package com.model;

import com.exceptions.NotEnoughMoneyException;

public class Person {
    private String name;
    private String surname;
    private Wallet wallet;

    public Person(String name, String surname) {
        wallet = new Wallet();
        this.name = name;
        this.surname = surname;
    }

    public void pay(Person person, Money money) {
        try {
            wallet.takeOut(money);
            person.receiveMoney(money);
        } catch (NotEnoughMoneyException ex) {
            System.out.println("\nYou haven't got enough money to complete transaction.");
        }
    }

    public void receiveMoney(Money money) {
            wallet.putIn(money);
    }

    public Wallet getWallet() {
        return wallet;
    }

    @Override
    public String toString() {
        return String.format("Person: %s %s.\nWallet:\n", name, surname) + wallet.printWallet();
    }
}
