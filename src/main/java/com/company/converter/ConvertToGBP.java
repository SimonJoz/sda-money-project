package com.company.converter;

import com.company.model.Currency;
import com.company.model.Money;

import java.math.BigDecimal;

public class ConvertToGBP implements Converter {
    private static final BigDecimal USD_TO_GBP = BigDecimal.valueOf(0.81);
    private static final BigDecimal PLN_TO_GBP = BigDecimal.valueOf(0.19);
    private static final BigDecimal EUR_TO_GBP = BigDecimal.valueOf(0.87);

    @Override
    public Money convert(Money money) {
        Currency currency = money.getCurrency();
        BigDecimal amount = money.getAmount();
        BigDecimal result = BigDecimal.ZERO;
        switch (currency) {
            case GBP:
                return money;
            case PLN:
                result = amount.multiply(PLN_TO_GBP);
                break;
            case USD:
                result = amount.multiply(USD_TO_GBP);
                break;
            case EUR:
                result = amount.multiply(EUR_TO_GBP);
                break;
        }
        return new Money(result, Currency.GBP);
    }
}
