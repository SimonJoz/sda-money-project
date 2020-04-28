package com.company.matchingOffers;

import com.company.exceptions.NoSuchItemException;
import com.company.model.Money;
import com.company.model.Offer;

class MatchExactOffer implements Matchable {

    @Override
    public Money getMatch(Offer buyOffer, Offer sellOffer) throws NoSuchItemException {
        Money result = null;
        for (Money willBuy : buyOffer.getPrices()) {
            for (Money willSell : sellOffer.getPrices()) {
                if (willBuy.equals(willSell)) {
                    result = willSell;
                    break;
                }
            }
        }
        if (result == null) {
            throw new NoSuchItemException();
        }
        return result;

    }
}
