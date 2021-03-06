package com.company.curencyExchange;

import com.company.enums.Currency;
import com.company.model.Money;

import java.math.BigDecimal;
import java.util.Map;

class ConvertToPLN implements Converter {
    private final Map<Currency, BigDecimal> exchangeRates = Map.of(
            Currency.PLN, BigDecimal.ONE,
            Currency.USD, BigDecimal.valueOf(4.22),
            Currency.EUR, BigDecimal.valueOf(4.54),
            Currency.GBP, BigDecimal.valueOf(5.21));

    @Override
    public Money convert(Money money) {
        BigDecimal rate = exchangeRates.get(money.getCurrency());
        return new Money(money.getAmount().multiply(rate), Currency.PLN);
    }


}
