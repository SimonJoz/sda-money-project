package com.company.curencyExchange;

import com.company.enums.Currency;
import com.company.model.Money;

import java.math.BigDecimal;
import java.util.Map;

class ConvertToGBP implements Converter {
    private Map<Currency, BigDecimal> exchangeRates = Map.of(
            Currency.GBP, BigDecimal.ONE,
            Currency.USD, BigDecimal.valueOf(0.81),
            Currency.PLN, BigDecimal.valueOf(0.19),
            Currency.EUR, BigDecimal.valueOf(0.87));

    @Override
    public Money convert(Money money) {
        BigDecimal rate = exchangeRates.get(money.getCurrency());
        return new Money(money.getAmount().multiply(rate), Currency.GBP);
    }
}
