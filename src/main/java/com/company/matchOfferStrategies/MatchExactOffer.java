package com.company.matchOfferStrategies;

import com.company.exceptions.InvalidTransactionException;
import com.company.model.Money;
import com.company.model.Offer;

public class MatchExactOffer implements Matchable {

    @Override
    public Money getMatch(Offer buyOffer, Offer sellOffer) throws InvalidTransactionException {
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
            throw new InvalidTransactionException();
        }
        return result;

    }
}
