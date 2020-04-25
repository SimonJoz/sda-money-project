package com.company.IO;

import com.company.exceptions.NoSuchCurrencyException;
import com.company.model.Currency;
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
        Currency currency = Currency.getCurrencyByInt(readInt());
        if(currency.equals(Currency.NO_SUCH_CURRENCY)){
            throw new NoSuchCurrencyException();
        }
        return currency;
    }

    public Money readMoney() throws NoSuchCurrencyException {
        Currency currency = readCurrency();
        System.out.println("Please enter amount: ");
        int amount = readInt();
        return new Money(amount, currency);
    }

}
