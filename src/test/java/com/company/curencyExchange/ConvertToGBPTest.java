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

class ConvertToGBPTest {
    private Converter convertToGBP = new ConvertToGBP();
    private Map<Currency, BigDecimal> exchangeRates = Map.of(
            Currency.GBP, BigDecimal.ONE,
            Currency.USD, BigDecimal.valueOf(0.81),
            Currency.PLN, BigDecimal.valueOf(0.19),
            Currency.EUR, BigDecimal.valueOf(0.87));

    @ParameterizedTest
    @MethodSource("supplyMoney")
    public void should_change_currency_to_GBP_and_return_correct_amount(Money money) {
        BigDecimal rate = exchangeRates.get(money.getCurrency());
        BigDecimal amount = money.getAmount().multiply(rate);
        Money actual = convertToGBP.convert(money);
        Money expected = new Money(amount, Currency.GBP);
        int result = actual.getAmount().compareTo(expected.getAmount());
        int currencyResult = actual.getCurrency().compareTo(expected.getCurrency());
        assertEquals(0, result);
        assertEquals(0, currencyResult);
    }

    private static Stream<Arguments> supplyMoney() {
        return Stream.of(
                Arguments.of(new Money(21453, Currency.PLN)),
                Arguments.of(new Money(5400, Currency.EUR)),
                Arguments.of(new Money(354324, Currency.USD)),
                Arguments.of(new Money(14533, Currency.PLN)),
                Arguments.of(new Money(1034, Currency.EUR)),
                Arguments.of(new Money(545, Currency.GBP)),
                Arguments.of(new Money(24, Currency.USD)),
                Arguments.of(new Money(45, Currency.GBP)));
    }
}