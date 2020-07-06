package com.company.matchingOffers;

import com.company.enums.MatcherType;
import com.company.exceptions.NoSuchItemException;
import com.company.model.Money;
import com.company.model.Offer;
import lombok.AccessLevel;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;

import static com.company.enums.MatcherType.*;

@Slf4j
public class OfferMatcher {

    @Setter(AccessLevel.PRIVATE)
    private Matchable matcher;

    private final Map<MatcherType, Matchable> matchersMap = Map.of(
            FIRST_EXACT_PRICE, new MatchFirstExactOffer(),
            ANY_HIGHEST_PRICE, new HighestPriceInAnyCurrency(),
            ANY_LOWEST_PRICE, new LowestPriceInAnyCurrency(),
            LOWEST_PRICE_IN_CURRENCY, new LowestPriceInBuyerCurrency(),
            HIGHEST_PRICE_IN_CURRENCY, new HighestPriceInBuyerCurrency());

    public Money getMatchingOffer(Offer buyOffer, Offer sellOffer, MatcherType type) throws NoSuchItemException {
        setMatcher(matchersMap.get(type));
        Money match = matcher.getMatch(buyOffer, sellOffer);
        log.debug("MATCHING PRICE - {}.", match);
        return match;
    }

}

