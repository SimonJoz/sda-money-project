package com.company.matchingOffers;

import com.company.comperator.CompareByValueOfAmount;
import com.company.enums.Currency;
import com.company.enums.MatcherType;
import com.company.exceptions.NoSuchItemException;
import com.company.model.Money;
import com.company.model.Offer;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

class OfferMatcherTest {
    private OfferMatcher matcher = new OfferMatcher();
    CompareByValueOfAmount comparator = CompareByValueOfAmount.getInstance();

    private static Stream<Arguments> supplyValues() {
        return Stream.of(
                Arguments.of(new ArrayList<>(List.of(new Money(1, Currency.PLN),
                        new Money(1, Currency.EUR), new Money(1, Currency.USD)))),
                Arguments.of(new ArrayList<>(List.of(new Money(1, Currency.EUR),
                        new Money(1, Currency.USD), new Money(1, Currency.GBP)))),
                Arguments.of(new ArrayList<>(List.of(new Money(20, Currency.PLN),
                        new Money(1, Currency.GBP), new Money(3, Currency.EUR)))),
                Arguments.of(new ArrayList<>(List.of(new Money(10, Currency.PLN),
                        new Money(3, Currency.GBP), new Money(3, Currency.USD)))));
    }


    @ParameterizedTest
    @MethodSource("supplyValues")
    @SuppressWarnings("OptionalGetWithoutIsPresent")
    void should_return_matching_price(List<Money> list) throws NoSuchItemException {
        Offer offer1 = new Offer("OFFER", new ArrayList<>(list));
        Offer offer2 = new Offer("OFFER", new ArrayList<>(list));

        // first exact offer matching buyer offer (price and currency)
        Money firstExactMatch = matcher.getMatchingOffer(offer1, offer2, MatcherType.FIRST_EXACT_PRICE);
        Money expected = offer2.getPrices().get(0);
        assertEquals(expected, firstExactMatch);

        // lowest price in any currency available in seller offer
        Money lowest = matcher.getMatchingOffer(offer1, offer2, MatcherType.ANY_LOWEST_PRICE);
        Money expected2 = offer2.getPrices().stream().min(comparator).get();
        assertEquals(expected2, lowest);

        // highest price in any currency
        Money highest = matcher.getMatchingOffer(offer1, offer2, MatcherType.ANY_HIGHEST_PRICE);
        Money expected3 = offer2.getPrices().stream().max(comparator).get();
        assertEquals(expected3, highest);

        // lowest price matching currencies from buyer offers
        List<Money> prices = offer1.getPrices();
        prices.remove(0);
        Money lowestInCurrency = matcher.getMatchingOffer(offer1, offer2, MatcherType.LOWEST_PRICE_IN_CURRENCY);
        Money expected4 = offer2.getPrices().stream()
                .filter(prices::contains)
                .min(comparator)
                .get();

        assertEquals(expected4, lowestInCurrency);

        // highest price matching currencies from buyer offers
        List<Money> prices2 = offer1.getPrices();
        Money highestInCurrency = matcher.getMatchingOffer(offer1, offer2, MatcherType.HIGHEST_PRICE_IN_CURRENCY);
        Money expected5 = offer2.getPrices().stream()
                .filter(prices::contains)
                .max(comparator)
                .get();

        assertEquals(expected5, highestInCurrency);

    }
}