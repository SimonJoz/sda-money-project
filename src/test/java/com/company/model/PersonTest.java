package com.company.model;

import com.company.enums.Currency;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.math.BigDecimal;
import java.util.Map;
import java.util.stream.Stream;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.collection.IsMapContaining.hasKey;
import static org.junit.jupiter.api.Assertions.assertEquals;

class PersonTest {
    private Person person1 = new Person("Name", "Surname");
    private Person person2 = new Person("Name2", "Surname2");

    @BeforeEach
    public void setWallet() {
        Map<Currency, Money> walletPerson1 = person1.getWallet().getMoneyMap();
        walletPerson1.put(Currency.PLN, new Money(BigDecimal.valueOf(1000), Currency.PLN));
        walletPerson1.put(Currency.EUR, new Money(BigDecimal.valueOf(1000), Currency.EUR));
        walletPerson1.put(Currency.USD, new Money(BigDecimal.valueOf(1000), Currency.USD));

        Map<Currency, Money> walletPerson2 = person2.getWallet().getMoneyMap();
        walletPerson2.put(Currency.PLN, new Money(BigDecimal.valueOf(1000), Currency.PLN));
        walletPerson2.put(Currency.EUR, new Money(BigDecimal.valueOf(1000), Currency.EUR));
        walletPerson2.put(Currency.USD, new Money(BigDecimal.valueOf(1000), Currency.USD));
    }

    @ParameterizedTest
    @MethodSource("supplyMoneyValue")
    public void should_update_receiver_wallet(Money moneyToPay) {
        Currency currency = moneyToPay.getCurrency();
        BigDecimal amountToPay = moneyToPay.getAmount();
        person1.giveMoney(person2, moneyToPay);
        Map<Currency, Money> person2Wallet = person2.getWallet().getMoneyMap();
        assertThat(person2Wallet, hasKey(currency));
        BigDecimal amountAfterReceivedPayment = person2Wallet.get(currency).getAmount();
        BigDecimal result = BigDecimal.valueOf(1000).add(amountToPay); // 1000 initial amount.
        assertEquals(result, amountAfterReceivedPayment);
    }

    @ParameterizedTest
    @MethodSource("supplyMoneyValue")
    public void should_update_payer_wallet(Money moneyToPay) {
        Currency currency = moneyToPay.getCurrency();
        BigDecimal amountToPay = moneyToPay.getAmount();
        person1.giveMoney(person2, moneyToPay);
        Map<Currency, Money> person1Wallet = person1.getWallet().getMoneyMap();
        assertThat(person1Wallet, hasKey(currency));
        BigDecimal amountAfterPayment = person1Wallet.get(currency).getAmount();
        BigDecimal result = BigDecimal.valueOf(1000).subtract(amountToPay);
        assertEquals(result, amountAfterPayment);
    }

    private static Stream<Arguments> supplyMoneyValue() {
        return Stream.of(
                Arguments.of(new Money(new BigDecimal(30), Currency.PLN)),
                Arguments.of(new Money(new BigDecimal(120), Currency.USD)),
                Arguments.of(new Money(new BigDecimal(54), Currency.EUR)),
                Arguments.of(new Money(new BigDecimal(15), Currency.PLN))
        );
    }
}