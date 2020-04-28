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

class ConvertToUSDTest {
    private Converter convertToUSD = new ConvertToUSD();
    private Map<Currency, BigDecimal> exchangeRates = Map.of(
            Currency.USD, BigDecimal.ONE,
            Currency.EUR, BigDecimal.valueOf(1.08),
            Currency.PLN, BigDecimal.valueOf(0.24),
            Currency.GBP, BigDecimal.valueOf(1.23));

    @ParameterizedTest
    @MethodSource("supplyMoney")
    public void should_change_currency_to_USD_and_return_correct_amount(Money money) {
        BigDecimal rate = exchangeRates.get(money.getCurrency());
        BigDecimal amount = money.getAmount().multiply(rate);
        Money actual = convertToUSD.convert(money);
        Money expected = new Money(amount, Currency.USD);
        int amountResult = actual.getAmount().compareTo(expected.getAmount());
        int currencyResult = actual.getCurrency().compareTo(expected.getCurrency());
        assertEquals(0, amountResult);
        assertEquals(0, currencyResult);
    }

    private static Stream<Arguments> supplyMoney() {
        return Stream.of(
                Arguments.of(new Money(234223, Currency.PLN)),
                Arguments.of(new Money(340, Currency.EUR)),
                Arguments.of(new Money(1, Currency.USD)),
                Arguments.of(new Money(433, Currency.PLN)),
                Arguments.of(new Money(10434, Currency.EUR)),
                Arguments.of(new Money(54435, Currency.GBP)),
                Arguments.of(new Money(242, Currency.USD)),
                Arguments.of(new Money(415, Currency.GBP)));
    }

}