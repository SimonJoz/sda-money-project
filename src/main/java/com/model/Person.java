package com.model;

import com.exeptions.IncorrectAmountException;
import com.exeptions.NoSuchCurrencyException;
import com.exeptions.NotEnoughMoneyException;

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
            person.takeIn(money);
        } catch (NoSuchCurrencyException e) {
            System.out.printf("\nYour wallet doesn't contains %s currency.\n",
                    money.getCurrency());
        } catch (NotEnoughMoneyException ex) {
            System.out.println("\nYou haven't got enough money to complete transaction.");
        } catch (IncorrectAmountException e) {
            System.out.println("Amount cannot be negative value.");
        }
    }

    public void takeIn(Money money) {
        try {
            wallet.putIn(money);
        } catch (IncorrectAmountException e) {
            System.out.println("Amount cannot be negative value.");
        }
    }

    @Override
    public String toString() {
        return String.format("%s %s\nWallet: ", name, surname) + wallet.printWallet();
    }
}
