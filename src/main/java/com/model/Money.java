package com.model;

import com.exceptions.IncorrectAmountException;
import com.exceptions.NotEnoughMoneyException;

import java.math.BigDecimal;

public class Money {
    private BigDecimal amount;
    private Currency currency;

    public Money(BigDecimal value, Currency currency) {
        this.amount = value;
        this.currency = currency;
        if (value.compareTo(BigDecimal.ZERO) < 0) {
            throw new IncorrectAmountException("Amount cannot be negative value.");
        }
    }

    public void moneyIn(Money money) {
        amount = amount.add(money.amount);
    }

    public void moneyOut(Money money) {
        if (amount.compareTo(money.amount) < 0) {
            throw new NotEnoughMoneyException();
        }
        amount = amount.subtract(money.amount);
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public Currency getCurrency() {
        return currency;
    }

    @Override
    public String toString() {
        return String.format("%s %.2f", currency, amount);
    }

}

