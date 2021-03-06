package com.company.matchingOffers;

import com.company.enums.Currency;
import com.company.exceptions.NoSuchItemException;
import com.company.model.Money;
import com.company.model.Offer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class MatchExactOfferTest {
    private Matchable matcher = new MatchFirstExactOffer();
    private Offer sellOffer = new Offer("Item", new ArrayList<>());
    private Offer buyOffer = new Offer("Item", new ArrayList<>());

    @BeforeEach
    public void setup() {
        sellOffer.getPrices().add(new Money(new BigDecimal(120), Currency.USD));
        sellOffer.getPrices().add(new Money(new BigDecimal(50), Currency.PLN));
        sellOffer.getPrices().add(new Money(new BigDecimal(30), Currency.PLN));
        sellOffer.getPrices().add(new Money(new BigDecimal(54), Currency.EUR));
    }

    @Test
    public void should_return_value_matching_exact_buyer_offer() throws NoSuchItemException {
        Money expected = new Money(new BigDecimal(30), Currency.PLN);
        buyOffer.getPrices().add(expected);
        Money actual = matcher.getMatch(buyOffer, sellOffer);
        assertEquals(expected, actual);
    }

    @Test
    public void should_throw_NoSuchItemException_if_no_match_found() {
        buyOffer.getPrices().add(new Money(new BigDecimal(60), Currency.PLN));
        Exception exception = assertThrows(NoSuchItemException.class, () ->
                matcher.getMatch(buyOffer, sellOffer));
        assertNull(exception.getMessage());
    }

}