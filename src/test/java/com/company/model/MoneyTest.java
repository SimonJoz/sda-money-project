package com.company.model;

import com.company.enums.Currency;
import com.company.exceptions.IncorrectAmountException;
import com.company.exceptions.NotEnoughMoneyException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.math.BigDecimal;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class MoneyTest {
    private Money money;

    @BeforeEach
    public void setup() {
        money = new Money(new BigDecimal(1000), Currency.PLN);
    }

    @ParameterizedTest
    @ValueSource(ints = {5, 10, 100, 356, 150, 200})
    public void should_return_correct_moneyIn_amount(int value) {
        Money moneyIn = new Money(new BigDecimal(value), Currency.PLN);
        money.moneyIn(moneyIn);
        BigDecimal result = BigDecimal.valueOf(1000).add(BigDecimal.valueOf(value));
        assertEquals(money.getAmount(), result);
    }

    @ParameterizedTest
    @ValueSource(ints = {10, 20, 50, 300, 346, 995})
    public void should_return_incorrect_moneyIn_amount(int value) {
        Money moneyIn = new Money(new BigDecimal(value), Currency.PLN);
        money.moneyIn(moneyIn);
        BigDecimal result = BigDecimal.valueOf(1000).subtract(BigDecimal.valueOf(value));
        assertNotEquals(money.getAmount(), result);
    }

    @ParameterizedTest
    @ValueSource(ints = {10, 20, 50, 300, 346, 995})
    public void should_return_correct_moneyOut_amount(int value) throws NotEnoughMoneyException {
        Money moneyOut = new Money(new BigDecimal(value), Currency.PLN);
        money.moneyOut(moneyOut);
        BigDecimal result = BigDecimal.valueOf(1000).subtract(BigDecimal.valueOf(value));
        assertEquals(money.getAmount(), result);
    }

    @ParameterizedTest
    @ValueSource(ints = {10, 20, 50, 300, 346, 995})
    public void should_return_incorrect_moneyOut_amount(int value) throws NotEnoughMoneyException {
        Money moneyOut = new Money(new BigDecimal(value), Currency.PLN);
        money.moneyOut(moneyOut);
        BigDecimal result = BigDecimal.valueOf(1000).add(BigDecimal.valueOf(value));
        assertNotEquals(money.getAmount(), result);
    }

    @ParameterizedTest
    @MethodSource("supplyMoneyValues")
    public void moneyOut_should_throw_NoEnoughMoneyException(Money value) {
        money = new Money(BigDecimal.TEN, Currency.PLN);
        Exception except = Assertions.assertThrows(NotEnoughMoneyException.class,
                () -> money.moneyOut(value));
        assertNull(except.getMessage());
    }

    private static Stream<Arguments> supplyMoneyValues() {
        return Stream.of(
                Arguments.of(new Money(BigDecimal.valueOf(234), Currency.PLN)),
                Arguments.of(new Money(BigDecimal.valueOf(11), Currency.USD)),
                Arguments.of(new Money(BigDecimal.valueOf(2353), Currency.EUR)),
                Arguments.of(new Money(BigDecimal.valueOf(324), Currency.GBP))

        );
    }

    @ParameterizedTest
    @ValueSource(ints = {-5, -123, -Integer.MAX_VALUE})
    public void constructor_should_throw_IncorrectAmountException(int value) {

        RuntimeException except = Assertions.assertThrows(IncorrectAmountException.class,
                () -> money = new Money(BigDecimal.valueOf(value), Currency.USD));
        assertNull(except.getMessage());

        RuntimeException except2 = Assertions.assertThrows(IncorrectAmountException.class,
                () -> money = new Money(BigDecimal.valueOf(value), Currency.PLN));
        assertNull(except2.getMessage());

        RuntimeException except3 = Assertions.assertThrows(IncorrectAmountException.class,
                () -> money = new Money(BigDecimal.valueOf(value), Currency.EUR));
        assertNull(except2.getMessage());
    }




}