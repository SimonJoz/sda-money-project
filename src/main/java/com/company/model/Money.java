package com.company.model;

import com.company.enums.Currency;
import com.company.enums.MyColor;
import com.company.exceptions.IncorrectAmountException;
import com.company.exceptions.NotEnoughMoneyException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.util.Objects;

public class Money implements Comparable<Money> {
    private static final Logger LOGGER = LoggerFactory.getLogger(Money.class);
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
        LOGGER.debug("{}AMOUNT AFTER MONEY IN - {}.{}", MyColor.CYAN_BOLD, amount, MyColor.RESET);
    }

    public void moneyOut(Money money) throws NotEnoughMoneyException {
        if (amount.compareTo(money.amount) < 0) {
            throw new NotEnoughMoneyException();
        }
        amount = amount.subtract(money.amount);
        LOGGER.debug("{}AMOUNT AFTER MONEY OUT -- {}{}", MyColor.CYAN_BOLD, amount, MyColor.RESET);
    }

    private void amountValidator(BigDecimal value) {
        if (value.compareTo(BigDecimal.ZERO) < 0) {
            LOGGER.error("{}NEGATIVE VALUE OF MONEY AMOUNT.{}", MyColor.RED_BOLD, MyColor.RESET);
            throw new IncorrectAmountException();
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

