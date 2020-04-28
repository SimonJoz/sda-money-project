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

class ConvertToPLNTest {
    private Converter convertToPLN = new ConvertToPLN();
    private Map<Currency, BigDecimal> exchangeRates = Map.of(
            Currency.PLN, BigDecimal.ONE,
            Currency.USD, BigDecimal.valueOf(4.22),
            Currency.EUR, BigDecimal.valueOf(4.54),
            Currency.GBP, BigDecimal.valueOf(5.21));

    @ParameterizedTest
    @MethodSource("supplyMoney")
    public void should_change_currency_to_PLN_and_return_correct_amount(Money money) {
        BigDecimal rate = exchangeRates.get(money.getCurrency());
        BigDecimal amount = money.getAmount().multiply(rate);
        Money actual = convertToPLN.convert(money);
        Money expected = new Money(amount, Currency.PLN);
        int result = actual.getAmount().compareTo(expected.getAmount());
        int currencyResult = actual.getCurrency().compareTo(expected.getCurrency());
        assertEquals(0, result);
        assertEquals(0, currencyResult);
    }

    private static Stream<Arguments> supplyMoney() {
        return Stream.of(
                Arguments.of(new Money(23, Currency.PLN)),
                Arguments.of(new Money(340, Currency.EUR)),
                Arguments.of(new Money(3524, Currency.USD)),
                Arguments.of(new Money(4233, Currency.PLN)),
                Arguments.of(new Money(10434, Currency.EUR)),
                Arguments.of(new Money(54435, Currency.GBP)),
                Arguments.of(new Money(242, Currency.USD)),
                Arguments.of(new Money(415, Currency.GBP)));
    }
}