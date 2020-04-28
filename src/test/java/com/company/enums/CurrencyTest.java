package com.company.enums;

import com.company.exceptions.NoSuchCurrencyException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;

class CurrencyTest {

    @ParameterizedTest
    @ValueSource(strings = {"PLN", "EUR", "usd", "gbp", "Eur", "Gbp", "uSd", "pLN"})
    public void should_return_correct_currency_type(String currency) throws NoSuchCurrencyException {
        Currency actual = Currency.getCurrencyByName(currency);
        Currency expected = Currency.valueOf(currency.toUpperCase());
        assertEquals(expected, actual);
    }

    @ParameterizedTest
    @ValueSource(strings = {"sadafs","GBPP","eru","PLLN","UDS","gPb"})
    public void should_throw_NoSuchCurrencyException(String currency){
        Exception exception = assertThrows(NoSuchCurrencyException.class,
                () -> Currency.getCurrencyByName(currency));
        assertNull(exception.getMessage());
    }
}