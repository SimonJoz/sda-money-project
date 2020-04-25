package com.company.converter;

import com.company.model.Currency;
import com.company.model.Money;

import java.math.BigDecimal;

public class ConvertToUSD implements Converter {
    private static final BigDecimal EUR_TO_USD = BigDecimal.valueOf(1.08);
    private static final BigDecimal PLN_TO_USD = BigDecimal.valueOf(0.24);
    private static final BigDecimal GBP_TO_USD = BigDecimal.valueOf(1.23);

    @Override
    public Money convert(Money money) {
        Currency currency = money.getCurrency();
        BigDecimal amount = money.getAmount();
        BigDecimal result = BigDecimal.ZERO;
        switch (currency){
            case USD:
                return money;
            case PLN:
                result = amount.multiply(PLN_TO_USD);
                break;
            case EUR:
                result = amount.multiply(EUR_TO_USD);
                break;
            case GBP:
                result = amount.multiply(GBP_TO_USD);
                break;
        }
        return new Money(result, Currency.USD);
    }
}
