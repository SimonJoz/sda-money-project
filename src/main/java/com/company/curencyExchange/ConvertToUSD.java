package com.company.curencyExchange;

import com.company.enums.Currency;
import com.company.model.Money;

import java.math.BigDecimal;
import java.util.Map;

class ConvertToUSD implements Converter {
    private Map<Currency, BigDecimal> exchangeRates = Map.of(
            Currency.USD, BigDecimal.ONE,
            Currency.EUR, BigDecimal.valueOf(1.08),
            Currency.PLN, BigDecimal.valueOf(0.24),
            Currency.GBP, BigDecimal.valueOf(1.23));

    @Override
    public Money convert(Money money) {
        BigDecimal rate = exchangeRates.get(money.getCurrency());
        return new Money(money.getAmount().multiply(rate), Currency.USD);
    }
}
