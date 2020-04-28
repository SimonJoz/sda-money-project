package com.company.curencyExchange;

import com.company.enums.Currency;
import com.company.model.Money;

import java.util.Map;

public class CurrencyExchange {
    private Converter converter;
    private Map<Currency, Converter> strategiesMap = Map.of(
            Currency.PLN, new ConvertToPLN(),
            Currency.EUR, new ConvertToEUR(),
            Currency.USD, new ConvertToUSD(),
            Currency.GBP, new ConvertToGBP());

    public Money changeMoney(Currency currency, Money moneyToConvert) {
        setConverter(strategiesMap.get(currency));
        return convert(moneyToConvert);
    }

    private Money convert(Money money) {
        return this.converter.convert(money);
    }

    private void setConverter(Converter converter) {
        this.converter = converter;
    }
}
