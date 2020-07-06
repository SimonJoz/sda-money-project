package com.company.model;

import com.company.enums.Currency;
import com.company.exceptions.IncorrectAmountException;
import com.company.exceptions.NotEnoughMoneyException;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.math.BigDecimal;

@Slf4j
@Getter
@EqualsAndHashCode
public class Money implements Comparable<Money> {
    private BigDecimal amount;
    private final Currency currency;

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
        log.debug("AMOUNT AFTER MONEY IN - {}.", amount);
    }

    public void moneyOut(Money money) throws NotEnoughMoneyException {
        if (amount.compareTo(money.amount) < 0) {
            throw new NotEnoughMoneyException();
        }
        amount = amount.subtract(money.amount);
        log.debug("AMOUNT AFTER MONEY OUT -- {}", amount);
    }

    private void amountValidator(BigDecimal value) {
        if (value.compareTo(BigDecimal.ZERO) < 0) {
            log.error("NEGATIVE VALUE OF MONEY AMOUNT.");
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
}

