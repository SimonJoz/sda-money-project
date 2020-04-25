package com.company;

import com.company.model.Currency;
import com.company.model.Money;
import com.company.model.Offer;
import com.company.model.Person;

import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        Person Bob = new Person("Bob", "Smith");
        Person Sue = new Person("Sue", "Doe");

        Sue.receiveMoney(new Money(100, Currency.PLN));

        Sue.addItemToBuy(new Offer("Mouse", new ArrayList<>(List.of(
                new Money(20, Currency.EUR), new Money(1, Currency.PLN)))));

        Sue.addItemToBuy(new Offer("Keyboard", new ArrayList<>(List.of(
                new Money(5, Currency.USD), new Money(5, Currency.PLN)))));


        ArrayList<Money> sellOffers = new ArrayList<>();
        sellOffers.add(new Money(5, Currency.USD));
        sellOffers.add(new Money(3, Currency.PLN));
        sellOffers.add(new Money(2, Currency.EUR));

        Bob.offerItemForSale(new Offer("Mouse", new ArrayList<>(List.of(
                new Money(2, Currency.USD),
                new Money(2, Currency.PLN)))));
        Bob.offerItemForSale(new Offer("Keyboard", new ArrayList<>(sellOffers)));
        Bob.offerItemForSale(new Offer("Headphones", new ArrayList<>(sellOffers)));

        MainControl control = new MainControl();
        control.mainLoop(Sue, Bob);

    }
}