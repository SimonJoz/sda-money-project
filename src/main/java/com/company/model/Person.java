package com.company.model;

import com.company.curencyExchange.CurrencyExchange;
import com.company.enums.Currency;
import com.company.enums.MatcherType;
import com.company.exceptions.IncorrectPaymentException;
import com.company.exceptions.NoSuchItemException;
import com.company.exceptions.NotEnoughMoneyException;
import com.company.matchingOffers.OfferMatcher;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

import static java.lang.String.format;

@Slf4j
@Getter
public class Person {
    private final String name;
    private final String surname;
    private final Wallet wallet;
    private final List<String> myItems;
    private final List<Offer> itemsForSale;
    private final List<Offer> itemsToBuy;

    public Person(String name, String surname) {
        wallet = new Wallet();
        myItems = new ArrayList<>();
        itemsForSale = new ArrayList<>();
        itemsToBuy = new ArrayList<>();
        this.name = name;
        this.surname = surname;
    }

    public void giveMoney(Person person, Money money) {
        try {
            wallet.takeOut(money);
            person.receiveMoney(money);
        } catch (NotEnoughMoneyException ex) {
            log.warn("\nYOU HAVEN'T GOT ENOUGH MONEY TO COMPLETE TRANSACTION.");
        }
    }

    public void buy(Person seller, String reqItem, Currency currency, MatcherType type) {
        log.info("MATCHING OFFERS.");
        Offer buyerOffer = getOffer(itemsToBuy, reqItem);
        log.debug("REQUIRED ITEM - {}.", buyerOffer);
        Offer sellerOffer = getOffer(seller.itemsForSale, reqItem);
        log.debug("SELLER MATCHING ITEM - {}.", sellerOffer);
        OfferMatcher matcher = new OfferMatcher();
        try {
            Money matchingPrice = matcher.getMatchingOffer(buyerOffer, sellerOffer, type);
            log.info("\"{}\" BUYING '{}' FROM \"{}\".", this.name, reqItem, seller.getName());
            Money sellerBalance = seller.checkBalance(matchingPrice);
            pay(seller, currency, matchingPrice);
            seller.confirmPayment(sellerBalance, matchingPrice);
            updateBuyerLists(buyerOffer);
            updateSellerLists(seller, sellerOffer, matchingPrice);
            log.info("TRANSACTION COMPLETED !!!");
        } catch (NoSuchItemException e) {
            log.warn("NO MATCHING ITEM FIND FOR: \"{}\".", reqItem);
        } catch (NotEnoughMoneyException e) {
            log.warn("TRANSACTION FAILED.. NOT ENOUGH MONEY.");
        } catch (IncorrectPaymentException e) {
            log.warn("TRANSACTION FAILED.. INCORRECT PAYMENT !!");
        } finally {
            seller.logItemsForSale();
            this.logItemsToBuy();
            this.logMyItems();
            System.out.println();
        }
    }

    private void confirmPayment(Money currentBalance, Money payment) throws IncorrectPaymentException {
        wallet.confirmReceivingMoney(currentBalance, payment);
    }

    private Money checkBalance(Money money) {
        Money tempBalance = wallet.getBalance(money);
        return new Money(tempBalance.getAmount(), tempBalance.getCurrency());
    }

    private void updateBuyerLists(Offer buyerOffer) {
        addItemToMyList(buyerOffer);
        removeItemFromBuyList(buyerOffer);
    }

    private void addItemToMyList(Offer buyerOffer) {
        myItems.add(buyerOffer.getItemName());
        log.debug("ITEM ADDED TO {}'s MY_ITEMS LIST - \"{}\".",
                this.getName(), buyerOffer.getItemName());
    }

    private void removeItemFromBuyList(Offer buyerOffer) {
        itemsToBuy.remove(buyerOffer);
        log.debug("ITEM REMOVED FROM {}'s ITEMS_TO_BUY LIST - \"{}\".",
                this.getName(), buyerOffer.getItemName());
    }

    private void updateSellerLists(Person seller, Offer sellerOffer, Money money) {
        sellerOffer.updatePricesList(money);
        if (sellerOffer.getPrices().isEmpty()) {
            seller.itemsForSale.remove(sellerOffer);
            log.debug("\"{}\" - REMOVED FROM SELLER LIST.", sellerOffer.getItemName());
            return;
        }
        log.debug("PRICE - {} - REMOVED FROM \"{}\" PRICES LIST.", money, sellerOffer.getItemName());
    }

    private void pay(Person seller, Currency currency, Money money) throws NotEnoughMoneyException {
        CurrencyExchange cantor = CurrencyExchange.getInstance();
        Money exchanged = cantor.changeMoney(currency, money);
        log.info("\"{}\" IS PAYING...", this.getName());
        log.debug("ORIGINAL MONEY - {};  EXCHANGED - {}", money, exchanged);
        wallet.takeOut(exchanged);
        seller.receiveMoney(money); // initial currency
    }

    public void receiveMoney(Money money) {
        log.info("SELLER WALLET:");
        wallet.putIn(money);
    }

    public void addItemForSale(Offer offer) {
        log.info("ADDING ITEM TO \"{}\" SELL LIST.", this.getName());
        itemsForSale.add(offer);
        log.debug("ITEM: {}", offer);
        log.info("ADDING ITEM COMPLETED !");
    }

    public void addItemToBuy(Offer offer) {
        log.info("ADDING ITEM TO \"{}\" BUY LIST.", this.getName());
        itemsToBuy.add(offer);
        log.debug("ITEM: {}", offer);
        log.info("ADDING ITEM COMPLETED !");
    }

    private Offer getOffer(List<Offer> list, String item) {
        return list.stream().filter(offer -> item.equals(offer.getItemName()))
                .findFirst()
                .orElse(Offer.NO_OFFERS);
    }

    public void logItemsForSale() {
        if (itemsForSale.isEmpty()) log.debug("SELLER OFFER - NO OFFERS..");
        itemsForSale.forEach(item -> log.debug("SELLER OFFER - {}", item));
    }

    public void logItemsToBuy() {
        if (itemsToBuy.isEmpty()) log.debug("BUYER OFFER - NO OFFERS..");
        itemsToBuy.forEach(item -> log.debug("BUYER OFFER - {}", item));
    }

    public void logMyItems() {
        if (myItems.isEmpty()) log.debug("BUYER MY ITEMS - NO ITEMS..");
        myItems.forEach(item -> log.debug("BUYER MY ITEMS - {}", item));
    }

    @Override
    public String toString() {
        String wallet = this.wallet.printWallet();
        if (wallet.length() == 0) {
            wallet = "Empty..";
        }
        return format("%s %s. Wallet: %s", name, surname, wallet);
    }
}
