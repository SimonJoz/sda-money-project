package com.company.comperator;

import com.company.model.Currency;
import com.company.model.Money;

import java.math.BigDecimal;
import java.util.Comparator;

public class CompareByValueOfAmount implements Comparator<Money> {
    private static CompareByValueOfAmount instance = new CompareByValueOfAmount();

    private CompareByValueOfAmount(){
    }

    public static CompareByValueOfAmount getInstance(){
        return instance;
    }

    @Override
    public int compare(Money money1, Money money2) {
        BigDecimal amount1 = money1.changeMoney(Currency.PLN).getAmount();
        BigDecimal amount2 = money2.changeMoney(Currency.PLN).getAmount();
        return amount1.compareTo(amount2);
    }
}
