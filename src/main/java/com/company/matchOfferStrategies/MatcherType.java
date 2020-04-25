package com.company.matchOfferStrategies;

import com.company.exceptions.NoSuchMatcherException;

import java.util.Arrays;

public enum MatcherType {
    EXACT(0, "MATCH WITH EXACT OFFER"),
    ANY_HIGHEST_PRICE(1, "HIGHEST PRICE IN ANY CURRENCY"),
    ANY_LOWEST_PRICE(2, "LOWEST PRICE IN ANY CURRENCY"),
    LOWEST_PRICE_IN_CURRENCY(3, "LOWEST PRICE IN SPECIFIED CURRENCIES"),
    HIGHEST_PRICE_IN_CURRENCY(4, "HIGHEST PRICE FROM SPECIFIED CURRENCIES");

    private final int id;
    private final String description;

    MatcherType(int id, String description) {
        this.id = id;
        this.description = description;
    }

    public static MatcherType getMatcherTypeById(int id) throws NoSuchMatcherException {
        if (id >= MatcherType.values().length) {
            throw new NoSuchMatcherException();
        }
        return MatcherType.values()[id];
    }

    public static void printMatchers() {
        Arrays.stream(MatcherType.values())
                .forEach(matcher -> System.out.printf("%d -- %s\n", matcher.id, matcher.description));
    }
}
