package com.company.model;

import com.company.comperator.CompareByValueOfAmount;
import com.company.exceptions.InvalidTransactionException;

import java.util.ArrayList;
import java.util.List;

import static java.lang.String.format;

public class Offer {

    private String itemName;
    private List<Money> prices;
    private CompareByValueOfAmount comparator = CompareByValueOfAmount.getInstance();

    public Offer(String item, List<Money> prices) {
        this.itemName = item;
        this.prices = prices;
    }

    public Money getCheapestMatchingOffer(Offer offer) throws InvalidTransactionException {
        List<Money> matches = new ArrayList<>();
        getAllMatches(matches, offer);
        if (matches.isEmpty()) {
            throw new InvalidTransactionException();
        }
        matches.sort(comparator);
        return matches.get(0);
    }

    private void getAllMatches(List<Money> matches, Offer offer){
        for (Money willBuy : prices) {
            for (Money willSell : offer.prices) {
                if(comparator.compare(willBuy,willSell) >= 0){
                    matches.add(willSell);
                }
            }
        }
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
