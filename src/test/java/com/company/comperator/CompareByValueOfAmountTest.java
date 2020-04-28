package com.company.comperator;

import com.company.enums.Currency;
import com.company.model.Money;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.EnumSource;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

class CompareByValueOfAmountTest {
    private CompareByValueOfAmount comparator = CompareByValueOfAmount.getInstance();

    @ParameterizedTest
    @MethodSource("supplyMoneyPairs")
    public void should_return_negative_value(Money money1, Money money2) {
        int result = comparator.compare(money1, money2);
        Assertions.assertEquals(-1, result);
    }

    @ParameterizedTest
    @MethodSource("supplyMoneyPairs")
    public void should_return_positive_value(Money money1, Money money2) {
        int result = comparator.compare(money2, money1);
        Assertions.assertEquals(1, result);
    }

    private static Stream<Arguments> supplyMoneyPairs() {
        return Stream.of(
                Arguments.of(new Money(3, Currency.PLN), new Money(5, Currency.USD)),
                Arguments.of(new Money(3, Currency.PLN), new Money(2, Currency.EUR)),
                Arguments.of(new Money(3, Currency.EUR), new Money(5, Currency.USD)),
                Arguments.of(new Money(3, Currency.USD), new Money(3, Currency.GBP)),
                Arguments.of(new Money(2, Currency.USD), new Money(5, Currency.USD)),
                Arguments.of(new Money(1, Currency.EUR), new Money(2, Currency.EUR)),
                Arguments.of(new Money(3, Currency.GBP), new Money(4, Currency.GBP)));
    }

    @ParameterizedTest
    @EnumSource(Currency.class)
    public void should_return_zero_value(Currency currency) {
        Money money1 = new Money(12, currency);
        Money money2 = new Money(12, currency);
        int result = comparator.compare(money1, money2);
        Assertions.assertEquals(0, result);
        money1 = new Money(234, currency);
        money2 = new Money(234, currency);
        result = comparator.compare(money1, money2);
        Assertions.assertEquals(0, result);
        money1 = new Money(5, currency);
        money2 = new Money(5, currency);
        result = comparator.compare(money1, money2);
        Assertions.assertEquals(0, result);
    }

}