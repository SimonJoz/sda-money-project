package com.company.enums;

import com.company.exceptions.NoSuchCurrencyException;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;

@RequiredArgsConstructor
public enum Currency {
    PLN("PLN"),
    USD("USD"),
    EUR("EUR"),
    GBP("GBP");

    private final String desc;

    public static Currency getCurrencyByName(String name) throws NoSuchCurrencyException {
        return Arrays.stream(Currency.values())
                .filter(currency -> currency.desc.equals(name.toUpperCase()))
                .findFirst()
                .orElseThrow(NoSuchCurrencyException::new);
    }
}
