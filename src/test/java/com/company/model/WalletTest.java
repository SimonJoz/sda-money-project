package com.company.model;

import com.company.enums.Currency;
import com.company.exceptions.IncorrectPaymentException;
import com.company.exceptions.NotEnoughMoneyException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.EnumSource;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.math.BigDecimal;
import java.util.Map;
import java.util.stream.Stream;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.*;

class WalletTest {
    private Wallet wallet = new Wallet();

    @BeforeEach
    public void setMoneyMap() {
        Map<Currency, Money> moneyMap = wallet.getMoneyMap();
        moneyMap.put(Currency.USD, new Money(BigDecimal.TEN, Currency.USD));
        moneyMap.put(Currency.PLN, new Money(BigDecimal.TEN, Currency.PLN));
        moneyMap.put(Currency.EUR, new Money(BigDecimal.TEN, Currency.EUR));
        moneyMap.put(Currency.GBP, new Money(BigDecimal.TEN, Currency.GBP));
    }

    @ParameterizedTest
    @MethodSource("supplyMoneyValues")
    // confirmReceivingPayment()
    public void should_throw_IncorrectPaymentException_if_balance_not_correct(Money payment){
        Map<Currency, Money> moneyMap = wallet.getMoneyMap();
        Money balance = moneyMap.get(payment.getCurrency());
        Money wrongPayment = new Money(payment.getAmount().subtract(BigDecimal.ONE),
                payment.getCurrency());
        wallet.putIn(wrongPayment);
        Exception exception = assertThrows(IncorrectPaymentException.class,
                () -> wallet.confirmReceivingMoney(balance, payment));
        assertNull(exception.getMessage());
    }

    @ParameterizedTest
    @MethodSource("supplyEntrySet")
    public void putInTest_should_add_non_existing_currency(Currency currency, Money value) {
        Wallet emptyWallet = new Wallet();
        Map<Currency, Money> moneyMap = emptyWallet.getMoneyMap();
        assertEquals(moneyMap.size(), 0);

        emptyWallet.putIn(value);
        Money insertedMoney = moneyMap.get(currency);
        assertThat(moneyMap, hasKey(currency));
        assertEquals(insertedMoney.getAmount(), value.getAmount());
        assertEquals(insertedMoney.getCurrency(), value.getCurrency());
    }

    @ParameterizedTest
    @MethodSource("supplyMoneyValues")
    public void putInTest_should_update_current_map(Money value) {
        Map<Currency, Money> moneyMap = wallet.getMoneyMap();
        wallet.putIn(value);
        BigDecimal currentAmount = moneyMap.get(value.getCurrency()).getAmount();
        BigDecimal putInResult = BigDecimal.TEN.add(value.getAmount());
        assertEquals(currentAmount, putInResult);
    }


    @ParameterizedTest
    @MethodSource("supplyEntrySet")
    public void takeOutTest_should_add_non_existing_currency(Currency currency, Money value) {
        Wallet emptyWallet = new Wallet();
        Map<Currency, Money> moneyMap = emptyWallet.getMoneyMap();
        assertEquals(moneyMap.size(), 0);

        Exception exception = assertThrows(NotEnoughMoneyException.class,
                () -> emptyWallet.takeOut(value));
        assertNull(exception.getMessage());

        Money insertedMoney = moneyMap.get(currency);
        assertThat(moneyMap, hasKey(currency));
        assertEquals(insertedMoney.getAmount(), BigDecimal.ZERO);
        assertEquals(insertedMoney.getCurrency(), currency);
    }

    private static Stream<Arguments> supplyEntrySet() {
        return Stream.of(
                Arguments.of(Currency.USD, new Money(BigDecimal.valueOf(231), Currency.USD)),
                Arguments.of(Currency.PLN, new Money(BigDecimal.valueOf(11), Currency.PLN)),
                Arguments.of(Currency.EUR, new Money(BigDecimal.valueOf(2323), Currency.EUR)),
                Arguments.of(Currency.GBP, new Money(BigDecimal.valueOf(564), Currency.GBP)));
    }

    @ParameterizedTest
    @MethodSource("supplyMoneyValues")
    public void takeOutTest_should_update_current_map(Money value) throws NotEnoughMoneyException {
        Map<Currency, Money> moneyMap = wallet.getMoneyMap();
        wallet.takeOut(value);
        BigDecimal currentAmount = moneyMap.get(value.getCurrency()).getAmount();
        BigDecimal takeOutResult = BigDecimal.TEN.subtract(value.getAmount());
        assertEquals(currentAmount, takeOutResult);

    }

    private static Stream<Arguments> supplyMoneyValues() {
        return Stream.of(
                Arguments.of(new Money(BigDecimal.valueOf(5), Currency.USD)),
                Arguments.of(new Money(BigDecimal.valueOf(3), Currency.PLN)),
                Arguments.of(new Money(BigDecimal.valueOf(7), Currency.EUR)),
                Arguments.of(new Money(BigDecimal.valueOf(6), Currency.GBP)));
    }

    @ParameterizedTest
    @EnumSource(Currency.class)
    public void should_contains_all_values_map_Test(Currency currency) {
        assertThat(wallet.getMoneyMap(), hasKey(currency));
        assertThat(wallet.getMoneyMap(), hasValue(wallet.getMoneyMap().get(currency)));
        assertEquals(4, wallet.getMoneyMap().size());
    }

    @ParameterizedTest
    @ValueSource(strings = {"EUR: 10.00", "PLN: 10.00", "USD: 10.00","GBP: 10.00"})
    public void should_print_out_all_wallet_values(String value) {
        String actual = wallet.printWallet();
        assertTrue(actual.contains(value));
    }
}