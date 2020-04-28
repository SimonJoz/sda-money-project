package com.company.matchingOffers;

import com.company.exceptions.NoSuchItemException;
import com.company.model.Money;
import com.company.model.Offer;

interface Matchable {
    Money getMatch(Offer buyerOffer, Offer sellerOffer) throws NoSuchItemException;
}
