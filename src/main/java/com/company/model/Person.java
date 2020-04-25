package com.company.model;

import com.company.exceptions.IncorrectPaymentException;
import com.company.exceptions.InvalidTransactionException;
import com.company.exceptions.NotEnoughMoneyException;

import java.util.ArrayList;
import java.util.List;

import static java.lang.String.format;

public class Person {
    private String name;
    private String surname;
    private Wallet wallet;
    private List<String> myItems;
    private List<Offer> itemsForSale;
    private List<Offer> itemsToBuy;


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
            System.err.println("\nYou haven't got enough money to complete transaction.");
        }
    }

    public void buy(Person seller, String reqItem, Currency currency) {
        Offer buyerOffer = getOffer(itemsToBuy, reqItem);
        Offer sellerOffer = getOffer(seller.itemsForSale, reqItem);
        try {
            Money matchingPrice = buyerOffer.getCheapestMatchingOffer(sellerOffer);
            pay(seller, currency, matchingPrice);
            seller.confirmPayment(matchingPrice);
            updateBuyerLists(buyerOffer);
            updateSellerLists(seller, sellerOffer, matchingPrice);
        } catch (InvalidTransactionException e) {
            System.err.println(format("\nSeller haven't got \"%s\" in his offer or your max price is to low.", reqItem));
        } catch (NotEnoughMoneyException e) {
            System.err.println("\nTransaction failed.. Not enough money.");
        } catch (IncorrectPaymentException e) {
            System.err.println("Transaction field.. Incorrect payment !!");
        }
    }

    private void confirmPayment(Money money) throws IncorrectPaymentException {
        wallet.confirmReceivingMoney(money);
    }

    private void updateBuyerLists(Offer buyerOffer) {
        myItems.add(buyerOffer.getName());
        itemsToBuy.remove(buyerOffer);
    }

    private void updateSellerLists(Person seller, Offer sellerOffer, Money money) {
        sellerOffer.updateMoneyList(money);
        if (sellerOffer.getPrices().isEmpty()) {
            seller.itemsForSale.remove(sellerOffer);
        }
    }

    private void pay(Person seller, Currency currency, Money money) throws NotEnoughMoneyException {
        Money exchanged = money.changeMoney(currency);
        wallet.takeOut(exchanged);
        seller.receiveMoney(money); // initial currency
    }

    private Offer getOffer(List<Offer> list,String item) {
        return list.stream().filter(offer -> item.equals(offer.getName()))
                .findFirst()
                .orElse(new Offer("No offers", new ArrayList<>()));
    }

    public void addItemToBuy(Offer offer) {
        itemsToBuy.add(offer);
    }

    public void offerItemForSale(Offer offer) {
        itemsForSale.add(offer);
    }

    public void receiveMoney(Money money) {
        wallet.putIn(money);
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

    @Override
    public String toString() {
        String wallet = this.wallet.printWallet();
        if(wallet.length() == 0){
            wallet = "Empty..";
        }
        return format("Person: %s %s.\nWallet:\n%s", name, surname, wallet) ;
    }
}
