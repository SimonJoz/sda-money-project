package com.company.matchOfferStrategies;

import com.company.comperator.CompareByValueOfAmount;
import com.company.exceptions.InvalidTransactionException;
import com.company.model.Money;
import com.company.model.Offer;

import java.util.ArrayList;
import java.util.List;

public class HighestPriceInBuyerCurrency implements Matchable {
    private CompareByValueOfAmount comparator = CompareByValueOfAmount.getInstance();

    @Override
    public Money getMatch(Offer buyOffer, Offer sellOffer) throws InvalidTransactionException {
        List<Money> matches = new ArrayList<>();
        for (Money willBuy : buyOffer.getPrices()) {
            for (Money willSell : sellOffer.getPrices()) {
                if (willBuy.compareTo(willSell) >= 0) {
                    matches.add(willSell);
                }
            }
        }
        sortIfListNotEmpty(matches);
        return matches.get(0);
    }

    private void sortIfListNotEmpty(List<Money> matches) throws InvalidTransactionException {
        if (matches.isEmpty()) {
            throw new InvalidTransactionException();
        }
        matches.sort(comparator.reversed());
    }
}
