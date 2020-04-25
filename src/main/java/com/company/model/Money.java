package com.company.model;

import com.company.exceptions.IncorrectAmountException;
import com.company.exceptions.NotEnoughMoneyException;

import java.math.BigDecimal;
import java.util.Objects;

public class Money implements Comparable<Money> {
    private BigDecimal amount;
    private Currency currency;

    public Money(BigDecimal value, Currency currency) {
        this.amount = value;
        this.currency = currency;
        amountValidator(value);
    }

    public Money(double amount, Currency currency) {
        this(BigDecimal.valueOf(amount), currency);
    }

    public Money(long amount, Currency currency) {
        this(BigDecimal.valueOf(amount), currency);
    }

    public void moneyIn(Money money) {
        amount = amount.add(money.amount);
    }

    public void moneyOut(Money money) throws NotEnoughMoneyException {
        if (amount.compareTo(money.amount) < 0) {
            throw new NotEnoughMoneyException();
        }
        amount = amount.subtract(money.amount);
    }

    private void amountValidator(BigDecimal value) {
        if (value.compareTo(BigDecimal.ZERO) < 0) {
            throw new IncorrectAmountException("Amount cannot be negative value.");
        }
    }

    @Override
    public String toString() {
        return String.format("%s: %.2f", currency, amount);
    }

    @Override
    public int compareTo(Money money) {
        if ((amount.compareTo(money.amount) > 0 && currency.equals(money.currency))) {
            return 1;
        }
        if ((amount.compareTo(money.amount) == 0 && currency.equals(money.currency))) {
            return 0;
        }
        return -1;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Money money = (Money) o;
        return Objects.equals(amount, money.amount) &&
                currency == money.currency;
    }

    @Override
    public int hashCode() {
        return Objects.hash(amount, currency);
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public Currency getCurrency() {
        return currency;
    }
}

