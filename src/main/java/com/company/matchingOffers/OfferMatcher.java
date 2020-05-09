package com.company.matchingOffers;

import com.company.enums.MatcherType;
import com.company.enums.MyColor;
import com.company.exceptions.NoSuchItemException;
import com.company.model.Money;
import com.company.model.Offer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

import static com.company.enums.MatcherType.*;

public class OfferMatcher {
    private static final Logger LOGGER = LoggerFactory.getLogger(OfferMatcher.class);
    private Matchable matcher;
    private Map<MatcherType, Matchable> matchersMap = Map.of(
            FIRST_EXACT_PRICE, new MatchFirstExactOffer(),
            ANY_HIGHEST_PRICE, new HighestPriceInAnyCurrency(),
            ANY_LOWEST_PRICE, new LowestPriceInAnyCurrency(),
            LOWEST_PRICE_IN_CURRENCY, new LowestPriceInBuyerCurrency(),
            HIGHEST_PRICE_IN_CURRENCY, new HighestPriceInBuyerCurrency());

    public Money getMatchingOffer(Offer buyOffer, Offer sellOffer, MatcherType type) throws NoSuchItemException {
        setMatcher(matchersMap.get(type));
        Money match = matcher.getMatch(buyOffer, sellOffer);
        LOGGER.debug("{}MATCHING PRICE - {}.{}", MyColor.CYAN_BOLD, match, MyColor.RESET);
        return match;
    }

    private void setMatcher(Matchable offerMatcher) {
        this.matcher = offerMatcher;
    }
}

