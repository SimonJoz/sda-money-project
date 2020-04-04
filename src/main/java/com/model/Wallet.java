package com.model;

import com.exeptions.IncorrectAmountException;
import com.exeptions.NoSuchCurrencyException;
import com.exeptions.NotEnoughMoneyException;

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
        BigDecimal amount = money.getAmount();
        if (amount.compareTo(BigDecimal.ZERO) < 0) {
            throw new IncorrectAmountException();
        } else if (moneyMap.containsKey(currency)) {
            Money currentAmount = moneyMap.get(currency);
            Money updatedAmount = currentAmount.moneyIn(money);
            moneyMap.put(currency, updatedAmount);
        } else {
            moneyMap.put(currency, money);
        }
    }

    public void takeOut(Money money) {
        Currency currency = money.getCurrency();
        BigDecimal amount = money.getAmount();
        if (amount.compareTo(BigDecimal.ZERO) < 0) {
            throw new IncorrectAmountException();
        } else if (!moneyMap.containsKey(currency)) {
            throw new NoSuchCurrencyException();
        } else if (moneyMap.get(currency).getAmount().compareTo(amount) < 0) {
            throw new NotEnoughMoneyException();
        } else {
            Money currentAmount = moneyMap.get(currency);
            Money updatedAmount = currentAmount.moneyOut(money);
            moneyMap.put(currency, updatedAmount);
        }
    }

    public String printWallet() {
        return moneyMap.entrySet().stream()
                .map(ent -> ent.getKey() + " : " + ent.getValue())
                .collect(Collectors.joining("\n"));
    }
}
