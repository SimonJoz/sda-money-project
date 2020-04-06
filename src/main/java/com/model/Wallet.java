package com.model;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class Wallet {

    private Map<Currency, Money> moneyMap;

    public Wallet() {
        moneyMap = new HashMap<>();
    }

    public void putIn(Money money) {
        Currency currency = money.getCurrency();
        Money moneyInCurrency = getMoneyInCurrency(currency);
        moneyInCurrency.moneyIn(money);
    }

    public void takeOut(Money money) {
        Currency currency = money.getCurrency();
        Money moneyOutCurrency = getMoneyInCurrency(currency);
        moneyOutCurrency.moneyOut(money);
    }

    private Money getMoneyInCurrency(Currency currency) {
        if (!moneyMap.containsKey(currency)) {
            moneyMap.put(currency, new Money(BigDecimal.ZERO, currency));
        }
        return moneyMap.get(currency);
    }

    public String printWallet() {
        return moneyMap.values().stream()
                .map(Money::toString)
                .collect(Collectors.joining("\n"));
    }

    public Map<Currency, Money> getMoneyMap() {
        return moneyMap;
    }
}
