package com.company.model;

import com.company.exceptions.IncorrectPaymentException;
import com.company.exceptions.NotEnoughMoneyException;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class Wallet {

    private Map<Currency, Money> moneyMap;

    public Wallet() {
        moneyMap = new HashMap<>();
    }


    public void confirmReceivingMoney(Money money) throws IncorrectPaymentException {
            BigDecimal currentBalance = moneyMap.get(money.getCurrency()).getAmount();
            BigDecimal expectedBalance = currentBalance.add(money.getAmount());
            if(currentBalance.add(money.getAmount()).compareTo(expectedBalance) != 0){
                throw new IncorrectPaymentException();
            }
            System.out.println("Receiving money confirmed ! ");
    }

    public void putIn(Money money) {
        Currency currency = money.getCurrency();
        Money moneyInCurrency = getMoneyInCurrency(currency);
        moneyInCurrency.moneyIn(money);
    }

    public void takeOut(Money money) throws NotEnoughMoneyException {
        Currency currency = money.getCurrency();
        Money moneyOutCurrency = getMoneyInCurrency(currency);
        moneyOutCurrency.moneyOut(money);
    }

    public Money getMoneyInCurrency(Currency currency) {
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
