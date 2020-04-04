package com.model;

import java.math.BigDecimal;

public class Money {
    private BigDecimal amount;
    private Currency currency;

    public Money(BigDecimal value, Currency currency) {
        this.amount = value;
        this.currency = currency;
    }

    public Money moneyIn(Money money) {
        amount = amount.add(money.amount);
        return new Money(amount, money.currency);
    }

    public Money moneyOut(Money money) {
        amount = amount.subtract(money.amount);
        return new Money(amount, money.currency);
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

