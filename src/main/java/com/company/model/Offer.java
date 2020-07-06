package com.company.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.List;

import static java.lang.String.format;

@Getter
@RequiredArgsConstructor
public class Offer {
    public static final Offer NO_OFFERS = new Offer("No offers", new ArrayList<>());
    private final String itemName;
    private final List<Money> prices;

    public void updatePricesList(Money money) {
        prices.removeIf(value -> value.equals(money));
    }

    private String printList() {
        StringBuilder sb = new StringBuilder();
        prices.forEach(price -> sb.append(price).append("; "));
        return sb.toString().trim();
    }

    @Override
    public String toString() {
        return format("\"%s\" %s", itemName, printList());
    }
}
