package com.company.curencyExchange;

import com.company.enums.Currency;
import com.company.model.Money;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.math.BigDecimal;
import java.util.Map;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ConvertToEURTest {
    private Converter convertToEUR = new ConvertToEUR();
    private Map<Currency, BigDecimal> exchangeRates = Map.of(
            Currency.EUR, BigDecimal.ONE,
            Currency.USD, BigDecimal.valueOf(0.93),
            Currency.PLN, BigDecimal.valueOf(0.22),
            Currency.GBP, BigDecimal.valueOf(1.15));

    @ParameterizedTest
    @MethodSource("supplyMoney")
    public void should_change_currency_to_EUR_and_return_correct_amount(Money money) {
        BigDecimal rate = exchangeRates.get(money.getCurrency());
        BigDecimal amount = money.getAmount().multiply(rate);
        Money actual = convertToEUR.convert(money);
        Money expected = new Money(amount, Currency.EUR);
        int result = actual.getAmount().compareTo(expected.getAmount());
        int currencyResult = actual.getCurrency().compareTo(expected.getCurrency());
        assertEquals(0, result);
        assertEquals(0, currencyResult);
    }

    private static Stream<Arguments> supplyMoney() {
        return Stream.of(
                Arguments.of(new Money(213, Currency.PLN)),
                Arguments.of(new Money(100, Currency.EUR)),
                Arguments.of(new Money(324, Currency.USD)),
                Arguments.of(new Money(13, Currency.PLN)),
                Arguments.of(new Money(10, Currency.EUR)),
                Arguments.of(new Money(5332545, Currency.GBP)),
                Arguments.of(new Money(24, Currency.USD)),
                Arguments.of(new Money(45, Currency.GBP)));
    }
}