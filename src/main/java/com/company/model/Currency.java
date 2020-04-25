package com.company.model;

import com.company.exceptions.NoSuchCurrencyException;

import java.util.Arrays;

public enum Currency {
    PLN("PLN"),
    USD("USD"),
    EUR("EUR"),
    GBP("GBP");

    private final String desc;

    Currency(String desc) {
        this.desc = desc;
    }

    public static Currency getCurrencyByName(String name) throws NoSuchCurrencyException {
        return Arrays.stream(Currency.values())
                .filter(currency -> currency.desc.equals(name.toUpperCase()))
                .findFirst()
                .orElseThrow(NoSuchCurrencyException::new);
    }

    public static void printCurrencies() {
        Arrays.stream(Currency.values())
                .forEach(currency -> System.out.printf("- %s\n", currency));
    }
}
