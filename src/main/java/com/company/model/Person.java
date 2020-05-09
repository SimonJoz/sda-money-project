package com.company.model;

import com.company.curencyExchange.CurrencyExchange;
import com.company.enums.Currency;
import com.company.enums.MatcherType;
import com.company.enums.MyColor;
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
    private String name;
    private String surname;
    private Wallet wallet;
    private List<String> myItems;
    private List<Offer> itemsForSale;
    private List<Offer> itemsToBuy;
    private CurrencyExchange cantor = new CurrencyExchange();

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
            LOGGER.warn("\n{}YOU HAVEN'T GOT ENOUGH MONEY TO COMPLETE TRANSACTION.{}",
                    MyColor.RED_BOLD, MyColor.RESET);
        }
    }

    public void buy(Person seller, String reqItem, Currency currency, MatcherType type) {
        LOGGER.info("{}MATCHING OFFERS.{}", MyColor.YELLOW_BOLD, MyColor.RESET);
        Offer buyerOffer = getOffer(itemsToBuy, reqItem);
        LOGGER.debug("{}REQUIRED ITEM - {}.{}", MyColor.CYAN_BOLD, buyerOffer, MyColor.RESET);
        Offer sellerOffer = getOffer(seller.itemsForSale, reqItem);
        LOGGER.debug("{}SELLER MATCHING ITEM - {}.{}", MyColor.CYAN_BOLD, sellerOffer, MyColor.RESET);
        OfferMatcher matcher = new OfferMatcher();
        try {
            Money matchingPrice = matcher.getMatchingOffer(buyerOffer, sellerOffer, type);
            LOGGER.info("{}\"{}\" BUYING '{}' FROM \"{}\"{}.", MyColor.YELLOW_BOLD, this.name,
                    reqItem, seller.getName(), MyColor.RESET);
            Money sellerBalance = seller.checkBalance(matchingPrice);
            pay(seller, currency, matchingPrice);
            seller.confirmPayment(sellerBalance, matchingPrice);
            updateBuyerLists(buyerOffer);
            updateSellerLists(seller, sellerOffer, matchingPrice);
            LOGGER.info("{}TRANSACTION COMPLETED !!!{}", MyColor.YELLOW_BOLD, MyColor.RESET);
        } catch (NoSuchItemException e) {
            LOGGER.warn("{}NO MATCHING ITEM FIND FOR: \"{}\".{}", MyColor.RED_BOLD, reqItem, MyColor.RESET);
        } catch (NotEnoughMoneyException e) {
            LOGGER.warn("{}TRANSACTION FAILED.. NOT ENOUGH MONEY.{}", MyColor.RED_BOLD, MyColor.RESET);
        } catch (IncorrectPaymentException e) {
            LOGGER.warn("{}TRANSACTION FAILED.. INCORRECT PAYMENT !!{}", MyColor.RED_BOLD, MyColor.RESET);
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
        LOGGER.debug("{}ITEM ADDED TO {}'s MY_ITEMS LIST - \"{}\".{}"
                , MyColor.CYAN_BOLD, this.getName(), buyerOffer.getName(), MyColor.RESET);
    }

    private void removeItemFromBuyList(Offer buyerOffer) {
        itemsToBuy.remove(buyerOffer);
        LOGGER.debug("{}ITEM REMOVED FROM {}'s ITEMS_TO_BUY LIST - \"{}\".{}"
                , MyColor.CYAN_BOLD, this.getName(), buyerOffer.getName(), MyColor.RESET);
    }

    private void updateSellerLists(Person seller, Offer sellerOffer, Money money) {
        sellerOffer.updatePricesList(money);
        if (sellerOffer.getPrices().isEmpty()) {
            seller.itemsForSale.remove(sellerOffer);
            LOGGER.debug("{}\"{}\" - REMOVED FROM SELLER LIST.{}",
                    MyColor.CYAN_BOLD, sellerOffer.getName(), MyColor.RESET);
            return;
        }
        LOGGER.debug("{}PRICE - {} - REMOVED FROM \"{}\" PRICES LIST.{}",
                MyColor.CYAN_BOLD, money, sellerOffer.getName(), MyColor.RESET);
    }

    private void pay(Person seller, Currency currency, Money money) throws NotEnoughMoneyException {
        Money exchanged = cantor.changeMoney(currency, money);
        LOGGER.info("{}\"{}\" IS PAYING...{}", MyColor.YELLOW_BOLD, this.getName(), MyColor.RESET);
        LOGGER.debug("{}ORIGINAL MONEY - {};  EXCHANGED - {}{}",
                MyColor.CYAN_BOLD, money, exchanged, MyColor.RESET);
        wallet.takeOut(exchanged);
        seller.receiveMoney(money); // initial currency
    }

    public void receiveMoney(Money money) {
        LOGGER.info("{}SELLER WALLET:{}", MyColor.YELLOW_BOLD, MyColor.RESET);
        wallet.putIn(money);
    }

    public void addItemForSale(Offer offer) {
        LOGGER.info("{}ADDING ITEM TO \"{}\" SELL LIST.{}", MyColor.YELLOW_BOLD, this.getName(), MyColor.RESET);
        itemsForSale.add(offer);
        LOGGER.debug("{}ITEM: {}{}", MyColor.CYAN_BOLD, offer, MyColor.RESET);
        LOGGER.info("{}ADDING ITEM COMPLETED !{}", MyColor.YELLOW_BOLD, MyColor.RESET);
    }

    public void addItemToBuy(Offer offer) {
        LOGGER.info("{}ADDING ITEM TO \"{}\" BUY LIST.{}", MyColor.YELLOW_BOLD, this.getName(), MyColor.RESET);
        itemsToBuy.add(offer);
        LOGGER.debug("{}ITEM: {}{}", MyColor.CYAN_BOLD, offer, MyColor.RESET);
        LOGGER.info("{}ADDING ITEM COMPLETED !{}", MyColor.YELLOW_BOLD, MyColor.RESET);
    }

    private Offer getOffer(List<Offer> list, String item) {
        return list.stream().filter(offer -> item.equals(offer.getName()))
                .findFirst()
                .orElse(Offer.NO_OFFERS);
    }

    public void logItemsForSale() {
        if (itemsForSale.isEmpty()) LOGGER.debug("{}SELLER OFFER - NO OFFERS..{}", MyColor.MAGENTA, MyColor.RESET);
        itemsForSale.forEach(item -> LOGGER.debug("{}SELLER OFFER - {}{}"
                , MyColor.MAGENTA, item, MyColor.RESET));
    }

    public void logItemsToBuy() {
        if (itemsToBuy.isEmpty()) LOGGER.debug("{}BUYER OFFER - NO OFFERS..{}", MyColor.MAGENTA, MyColor.RESET);
        itemsToBuy.forEach(item -> LOGGER.debug("{}BUYER OFFER - {}{}"
                , MyColor.MAGENTA, item, MyColor.RESET));
    }

    public void logMyItems() {
        if (myItems.isEmpty()) LOGGER.debug("{}BUYER MY ITEMS - NO ITEMS..{}", MyColor.MAGENTA, MyColor.RESET);
        myItems.forEach(item -> LOGGER.debug("{}BUYER MY ITEMS - {}{}"
                , MyColor.MAGENTA, item, MyColor.RESET));
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
