package com.company.converter;

import com.company.model.Currency;
import com.company.model.Money;

import java.math.BigDecimal;

public class ConvertToPLN implements Converter {
    private static final BigDecimal EUR_TO_PLN = BigDecimal.valueOf(4.54);
    private static final BigDecimal USD_TO_PLN = BigDecimal.valueOf(4.22);
    private static final BigDecimal GBP_TO_PLN = BigDecimal.valueOf(5.21);

    @Override
    public Money convert(Money money){
        Currency currency = money.getCurrency();
        BigDecimal amount = money.getAmount();
        BigDecimal result = BigDecimal.ZERO;
        switch (currency){
            case PLN:
                return money;
            case USD:
               result = amount.multiply(USD_TO_PLN);
                break;
            case EUR:
                result = amount.multiply(EUR_TO_PLN);
                break;
            case GBP:
                result = amount.multiply(GBP_TO_PLN);
                break;

        }
        return new Money(result, Currency.PLN);
    }
}
