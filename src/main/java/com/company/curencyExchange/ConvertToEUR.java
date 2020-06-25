package com.company.curencyExchange;

import com.company.enums.Currency;
import com.company.model.Money;

import java.math.BigDecimal;
import java.util.Map;

class ConvertToEUR implements Converter {
    private final Map<Currency, BigDecimal> exchangeRates = Map.of(
            Currency.EUR, BigDecimal.ONE,
            Currency.USD, BigDecimal.valueOf(0.93),
            Currency.PLN, BigDecimal.valueOf(0.22),
            Currency.GBP, BigDecimal.valueOf(1.15));

    @Override
    public Money convert(Money money) {
        BigDecimal rate = exchangeRates.get(money.getCurrency());
        return new Money(money.getAmount().multiply(rate), Currency.EUR);
    }
}
