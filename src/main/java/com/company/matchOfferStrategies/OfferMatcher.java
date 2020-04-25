package com.company.matchOfferStrategies;

import com.company.exceptions.InvalidTransactionException;
import com.company.model.Money;
import com.company.model.Offer;

public class OfferMatcher {
    private Matchable matcher;

    public Money getMatchingOffer(Offer buyOffer, Offer sellOffer, MatcherType type) throws InvalidTransactionException {
        switch (type){
            case EXACT:
                setMatcher(new MatchExactOffer());
                break;
            case ANY_HIGHEST_PRICE:
                setMatcher(new HighestPriceInAnyCurrency());
                break;
            case ANY_LOWEST_PRICE:
                setMatcher(new LowestPriceInAnyCurrency());
                break;
            case LOWEST_PRICE_IN_CURRENCY:
                setMatcher(new LowestPriceInBuyerCurrency());
                break;
            case HIGHEST_PRICE_IN_CURRENCY:
                setMatcher(new HighestPriceInBuyerCurrency());
                break;
        }
        return getMatches(buyOffer,sellOffer);

    }

    private void setMatcher(Matchable offerMatcher) {
        this.matcher = offerMatcher;
    }

    private Money getMatches(Offer buyOffer, Offer sellOffer) throws InvalidTransactionException {
        return matcher.getMatch(buyOffer, sellOffer);
    }
}

