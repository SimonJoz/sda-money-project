package com.company;

import com.model.Currency;
import com.model.Money;
import com.model.Person;

import java.math.BigDecimal;

public class Main {

    public static void main(String[] args) {
        Person person1 = new Person("Bob", "Smith");
        Person person2 = new Person("Sue", "Doe");
        person1.receiveMoney(new Money(new BigDecimal(50), Currency.PLN));
        person2.receiveMoney(new Money(new BigDecimal(50), Currency.PLN));

        System.out.println("=======================");
        System.out.println(person1);
        System.out.println();
        System.out.println(person2);
        person1.pay(person2, new Money(BigDecimal.TEN, Currency.USD));
        System.out.println("=======================");

        System.out.println(person1);
        System.out.println();
        System.out.println(person2);
        person1.pay(person2, new Money(new BigDecimal(60), Currency.PLN));
        System.out.println("=======================");

        System.out.println(person1);
        System.out.println();
        System.out.println(person2);
        person1.pay(person2, new Money(BigDecimal.TEN, Currency.PLN));
        System.out.println("\n" + person1);
        System.out.println();
        System.out.println(person2);
        System.out.println("=======================");


        person1.receiveMoney(new Money(new BigDecimal(20), Currency.USD));
        System.out.println("\n" + person1);
        System.out.println();
        System.out.println(person2);
        System.out.println("=======================");

        person1.pay(person2, new Money(new BigDecimal(10), Currency.USD));
        System.out.println("\n" + person1);
        System.out.println();
        System.out.println(person2);
        System.out.println("=======================");

        person1.pay(person2, new Money(new BigDecimal(-20), Currency.PLN));
//        System.out.println("\n" + person1);
//        System.out.println();
//        System.out.println(person2);
//        System.out.println("=======================");
    }
}