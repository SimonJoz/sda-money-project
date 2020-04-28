package com.company.IO;

import com.company.enums.MatcherType;
import com.company.exceptions.NoSuchCurrencyException;
import com.company.enums.Currency;
import com.company.exceptions.NoSuchMatcherException;
import com.company.model.Money;

import java.util.Scanner;

public class DataReader {
    private Scanner scanner = new Scanner(System.in);

    public String readString() {
        return scanner.nextLine();
    }

    public int readInt() {
        try {
            return scanner.nextInt();
        } finally {
            scanner.nextLine();
        }
    }

    public void exit() {
        System.out.println("Bye !");
        scanner.close();
    }
    public Currency readCurrency() throws NoSuchCurrencyException {
        System.out.println("Please choose currency: ");
        Currency.printCurrencies();
        return Currency.getCurrencyByName(readString());
    }

    public Money readMoney() throws NoSuchCurrencyException {
        Currency currency = readCurrency();
        System.out.println("Please enter amount: ");
        int amount = readInt();
        return new Money(amount, currency);
    }
    public MatcherType readMatcherType() throws NoSuchMatcherException {
        System.out.println("Please choose matcher type: ");
        MatcherType.printMatchers();
        int input = readInt();
        return MatcherType.getMatcherTypeById(input);

    }

}
