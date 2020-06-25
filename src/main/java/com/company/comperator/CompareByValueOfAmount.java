package com.company.comperator;

import com.company.curencyExchange.CurrencyExchange;
import com.company.model.Money;

import java.math.BigDecimal;
import java.util.Comparator;

public class CompareByValueOfAmount implements Comparator<Money> {
    private static CompareByValueOfAmount instance;

    private CompareByValueOfAmount() {
    }

    public static CompareByValueOfAmount getInstance() {
        if(instance == null) {
            synchronized (CompareByValueOfAmount.class) {
                if(instance == null){
                    instance = new CompareByValueOfAmount();
                }
            }
        }
        return instance;
    }

    @Override
    public int compare(Money money1, Money money2) {
        CurrencyExchange cantor = CurrencyExchange.getInstance();
        BigDecimal amount1 = cantor.changeMoney(money2.getCurrency(), money1).getAmount();
        BigDecimal amount2 = money2.getAmount();
        return amount1.compareTo(amount2);
    }
}
