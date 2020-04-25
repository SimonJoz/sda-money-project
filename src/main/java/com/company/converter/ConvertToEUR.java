package com.company.converter;

import com.company.model.Currency;
import com.company.model.Money;

import java.math.BigDecimal;

public class ConvertToEUR implements Converter {
    private static final BigDecimal USD_TO_EUR = BigDecimal.valueOf(0.93);
    private static final BigDecimal PLN_TO_EUR = BigDecimal.valueOf(0.22);
    private static final BigDecimal GBP_TO_EUR = BigDecimal.valueOf(1.15);

    @Override
    public Money convert(Money money) {
        Currency currency = money.getCurrency();
        BigDecimal amount = money.getAmount();
        BigDecimal result = BigDecimal.ZERO;
        switch (currency) {
            case EUR:
                return money;
            case PLN:
                result = amount.multiply(PLN_TO_EUR);
                break;
            case USD:
                result = amount.multiply(USD_TO_EUR);
                break;
            case GBP:
                result = amount.multiply(GBP_TO_EUR);
                break;
        }
        return new Money(result, Currency.EUR);
    }
}
