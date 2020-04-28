package com.company.matchingOffers;

import com.company.comperator.CompareByValueOfAmount;
import com.company.exceptions.NoSuchItemException;
import com.company.model.Money;
import com.company.model.Offer;

import java.util.ArrayList;
import java.util.List;

class LowestPriceInBuyerCurrency implements Matchable {
    private CompareByValueOfAmount comparator = CompareByValueOfAmount.getInstance();

    @Override
    public Money getMatch(Offer buyOffer, Offer sellOffer) throws NoSuchItemException {
        List<Money> matches = new ArrayList<>();
        for (Money willBuy : buyOffer.getPrices()) {
            for (Money willSell : sellOffer.getPrices()) {
                if (willBuy.compareTo(willSell) >= 0) {
                    matches.add(willSell);
                }
            }
        }
        if (matches.isEmpty()) {
            throw new NoSuchItemException();
        }
        matches.sort(comparator);
        return matches.get(0);
    }
}
