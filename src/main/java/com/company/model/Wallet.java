package com.company.model;

import com.company.enums.Currency;
import com.company.exceptions.IncorrectPaymentException;
import com.company.exceptions.NotEnoughMoneyException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class Wallet {
    private static final Logger LOGGER = LoggerFactory.getLogger(Wallet.class);
    private Map<Currency, Money> moneyMap;

    public Wallet() {
        moneyMap = new HashMap<>();
    }


    public void confirmReceivingMoney(Money balance, Money payment) throws IncorrectPaymentException {
        Money actualMoney = moneyMap.get(payment.getCurrency());
        BigDecimal afterPayment = balance.getAmount().add(payment.getAmount());
        if (actualMoney.getAmount().compareTo(afterPayment) != 0) {
            throw new IncorrectPaymentException();
        }
        LOGGER.info("RECEIVING MONEY CONFIRMED ! BALANCE: {}.",  actualMoney);
    }

    public Money getBalance(Money money) {
        Currency currency = money.getCurrency();
        if (moneyMap.containsKey(currency)) {
            return moneyMap.get(currency);
        }
        return new Money(BigDecimal.ZERO, currency);
    }

    public void putIn(Money money) {
        Money moneyInCurrency = getMoneyInCurrency(money);
        LOGGER.debug("PUTTING MONEY - {}.", money);
        moneyInCurrency.moneyIn(money);
    }

    public void takeOut(Money money) throws NotEnoughMoneyException {
        Money moneyInCurrency = getMoneyInCurrency(money);
        LOGGER.debug("REMOVING MONEY - {}.", money);
        moneyInCurrency.moneyOut(money);
    }

    public Money getMoneyInCurrency(Money money) {
        Currency currency = money.getCurrency();
        if (!moneyMap.containsKey(currency)) {
            moneyMap.put(currency, new Money(BigDecimal.ZERO, currency));
        }
        Money result = moneyMap.get(currency);
        LOGGER.debug("CURRENT BALANCE - {}.", result);
        return result;
    }

    public String printWallet() {
        return moneyMap.values().stream()
                .map(Money::toString)
                .collect(Collectors.joining("; ")).trim();
    }

    public Map<Currency, Money> getMoneyMap() {
        return moneyMap;
    }
}
