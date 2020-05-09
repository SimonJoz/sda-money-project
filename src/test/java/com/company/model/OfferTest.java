package com.company.model;

import com.company.enums.Currency;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

class OfferTest {

    private Offer offer = new Offer("item", new ArrayList<>());

    @BeforeEach
    public void setup() {
        List<Money> prices = offer.getPrices();
        prices.add(new Money(BigDecimal.TEN, Currency.PLN));
        prices.add(new Money(BigDecimal.TEN, Currency.USD));
        prices.add(new Money(BigDecimal.TEN, Currency.EUR));
        prices.add(new Money(BigDecimal.TEN, Currency.GBP));
    }

    @ParameterizedTest
    @MethodSource("supplyMoney")
    void should_remove_matching_price_from_list(Money money) {
        offer.updatePricesList(money);
        assertEquals(3, offer.getPrices().size());
    }

    @ParameterizedTest
    @MethodSource("supplyMoney2")
    void should_not_remove_none_matching_price_from_list(Money money) {
        offer.updatePricesList(money);
        assertEquals(4, offer.getPrices().size());
    }

    private static Stream<Arguments> supplyMoney() {
        return Stream.of(
                Arguments.of(new Money(BigDecimal.TEN, Currency.PLN)),
                Arguments.of(new Money(BigDecimal.TEN, Currency.USD)),
                Arguments.of(new Money(BigDecimal.TEN, Currency.EUR)),
                Arguments.of(new Money(BigDecimal.TEN, Currency.GBP)));
    }

    private static Stream<Arguments> supplyMoney2() {
        return Stream.of(
                Arguments.of(new Money(BigDecimal.valueOf(234), Currency.PLN)),
                Arguments.of(new Money(BigDecimal.valueOf(11), Currency.USD)),
                Arguments.of(new Money(BigDecimal.valueOf(2353), Currency.EUR)),
                Arguments.of(new Money(BigDecimal.valueOf(324), Currency.GBP)));
    }

    @Test
    public void should_return_correct_string() {
        String actual = this.offer.toString();
        String expected = "\"item\" PLN: 10.00; USD: 10.00; EUR: 10.00; GBP: 10.00;";
        assertEquals(expected, actual);
    }
}