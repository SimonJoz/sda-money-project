package com.company.model;

import com.company.curencyExchange.CurrencyExchange;
import com.company.enums.Currency;
import com.company.enums.MatcherType;
import com.company.exceptions.IncorrectPaymentException;
import com.company.exceptions.NoSuchItemException;
import com.company.exceptions.NotEnoughMoneyException;
import com.company.matchingOffers.OfferMatcher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

import static java.lang.String.format;

public class Person {
    private static final Logger LOGGER = LoggerFactory.getLogger(Person.class);
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
            LOGGER.warn("\nYOU HAVEN'T GOT ENOUGH MONEY TO COMPLETE TRANSACTION.");
        }
    }

    public void buy(Person seller, String reqItem, Currency currency, MatcherType type) {
        LOGGER.info("MATCHING OFFERS.");
        Offer buyerOffer = getOffer(itemsToBuy, reqItem);
        LOGGER.debug("REQUIRED ITEM - {}.", buyerOffer);
        Offer sellerOffer = getOffer(seller.itemsForSale, reqItem);
        LOGGER.debug("SELLER MATCHING ITEM - {}.", sellerOffer);
        OfferMatcher matcher = new OfferMatcher();
        try {
            Money matchingPrice = matcher.getMatchingOffer(buyerOffer, sellerOffer, type);
            LOGGER.info("\"{}\" BUYING '{}' FROM \"{}\".", this.name, reqItem, seller.getName());
            Money sellerBalance = seller.checkBalance(matchingPrice);
            pay(seller, currency, matchingPrice);
            seller.confirmPayment(sellerBalance, matchingPrice);
            updateBuyerLists(buyerOffer);
            updateSellerLists(seller, sellerOffer, matchingPrice);
            LOGGER.info("TRANSACTION COMPLETED !!!");
        } catch (NoSuchItemException e) {
            LOGGER.warn("NO MATCHING ITEM FIND FOR: \"{}\".", reqItem);
        } catch (NotEnoughMoneyException e) {
            LOGGER.warn("TRANSACTION FAILED.. NOT ENOUGH MONEY.");
        } catch (IncorrectPaymentException e) {
            LOGGER.warn("TRANSACTION FAILED.. INCORRECT PAYMENT !!");
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
        myItems.add(buyerOffer.getName());
        LOGGER.debug("ITEM ADDED TO {}'s MY_ITEMS LIST - \"{}\".",
                this.getName(), buyerOffer.getName());
    }

    private void removeItemFromBuyList(Offer buyerOffer) {
        itemsToBuy.remove(buyerOffer);
        LOGGER.debug("ITEM REMOVED FROM {}'s ITEMS_TO_BUY LIST - \"{}\".",
                this.getName(), buyerOffer.getName());
    }

    private void updateSellerLists(Person seller, Offer sellerOffer, Money money) {
        sellerOffer.updatePricesList(money);
        if (sellerOffer.getPrices().isEmpty()) {
            seller.itemsForSale.remove(sellerOffer);
            LOGGER.debug("\"{}\" - REMOVED FROM SELLER LIST.", sellerOffer.getName());
            return;
        }
        LOGGER.debug("PRICE - {} - REMOVED FROM \"{}\" PRICES LIST.", money, sellerOffer.getName());
    }

    private void pay(Person seller, Currency currency, Money money) throws NotEnoughMoneyException {
        CurrencyExchange cantor = CurrencyExchange.getInstance();
        Money exchanged = cantor.changeMoney(currency, money);
        LOGGER.info("\"{}\" IS PAYING...", this.getName());
        LOGGER.debug("ORIGINAL MONEY - {};  EXCHANGED - {}", money, exchanged);
        wallet.takeOut(exchanged);
        seller.receiveMoney(money); // initial currency
    }

    public void receiveMoney(Money money) {
        LOGGER.info("SELLER WALLET:");
        wallet.putIn(money);
    }

    public void addItemForSale(Offer offer) {
        LOGGER.info("ADDING ITEM TO \"{}\" SELL LIST.", this.getName());
        itemsForSale.add(offer);
        LOGGER.debug("ITEM: {}", offer);
        LOGGER.info("ADDING ITEM COMPLETED !");
    }

    public void addItemToBuy(Offer offer) {
        LOGGER.info("ADDING ITEM TO \"{}\" BUY LIST.", this.getName());
        itemsToBuy.add(offer);
        LOGGER.debug("ITEM: {}", offer);
        LOGGER.info("ADDING ITEM COMPLETED !");
    }

    private Offer getOffer(List<Offer> list, String item) {
        return list.stream().filter(offer -> item.equals(offer.getName()))
                .findFirst()
                .orElse(Offer.NO_OFFERS);
    }

    public void logItemsForSale() {
        if (itemsForSale.isEmpty()) LOGGER.debug("SELLER OFFER - NO OFFERS..");
        itemsForSale.forEach(item -> LOGGER.debug("SELLER OFFER - {}", item));
    }

    public void logItemsToBuy() {
        if (itemsToBuy.isEmpty()) LOGGER.debug("BUYER OFFER - NO OFFERS..");
        itemsToBuy.forEach(item -> LOGGER.debug("BUYER OFFER - {}", item));
    }

    public void logMyItems() {
        if (myItems.isEmpty()) LOGGER.debug("BUYER MY ITEMS - NO ITEMS..");
        myItems.forEach(item -> LOGGER.debug("BUYER MY ITEMS - {}", item));
    }

    public Wallet getWallet() {
        return wallet;
    }

    public List<String> getMyItems() {
        return myItems;
    }

    public List<Offer> getItemsForSale() {
        return itemsForSale;
    }

    public List<Offer> getItemsToBuy() {
        return itemsToBuy;
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
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
