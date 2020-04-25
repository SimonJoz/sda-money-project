package com.company.model;

import java.util.List;

import static java.lang.String.format;

public class Offer {
    private String itemName;
    private List<Money> prices;

    public Offer(String item, List<Money> prices) {
        this.itemName = item;
        this.prices = prices;
    }

    public void updateMoneyList(Money money) {
        prices.removeIf(value -> value.equals(money));
    }

    public String getName() {
        return itemName;
    }

    public List<Money> getPrices() {
        return prices;
    }

    private String printList() {
        StringBuilder sb = new StringBuilder();
        prices.forEach(price -> sb.append(price).append("; "));
        return sb.toString();
    }

    @Override
    public String toString() {
        return format("%s: %s", itemName, printList());
    }
}
