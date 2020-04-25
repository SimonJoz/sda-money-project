package com.company.converter;

import com.company.model.Currency;
import com.company.model.Money;

public class CurrencyExchange {
    private Converter converter;

    public Money changeMoney(Currency currency, Money moneyToConvert) {
        switch (currency) {
            case PLN:
                setConverter(new ConvertToPLN());
                break;
            case USD:
                setConverter(new ConvertToUSD());
                break;
            case EUR:
                setConverter(new ConvertToEUR());
                break;
            case GBP:
                setConverter(new ConvertToGBP());
                break;
        }
        return convert(moneyToConvert);
    }

    private Money convert(Money money) {
        return this.converter.convert(money);
    }

    private void setConverter(Converter converter) {
        this.converter = converter;
    }
}
