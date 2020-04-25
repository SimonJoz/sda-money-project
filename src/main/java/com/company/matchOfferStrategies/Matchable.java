package com.company.matchOfferStrategies;

import com.company.exceptions.InvalidTransactionException;
import com.company.model.Money;
import com.company.model.Offer;

public interface Matchable {
    Money getMatch(Offer buyerOffer, Offer sellerOffer) throws InvalidTransactionException;
}
