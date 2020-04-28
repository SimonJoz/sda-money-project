package com.company;

import com.company.enums.Currency;
import com.company.enums.MatcherType;
import com.company.enums.MyColor;
import com.company.model.Money;
import com.company.model.Offer;
import com.company.model.Person;
import com.company.model.Simulation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SimulationControl {
    private static final Logger LOGGER = LoggerFactory.getLogger(SimulationControl.class);
    private Simulation simulation;

    public SimulationControl(Simulation simulation) {
        this.simulation = simulation;
    }
    void offerItemForSaleSimulation(){
        LOGGER.info("{}ADDING SALE OFFER SIMULATION{}",MyColor.BLUE_BOLD,MyColor.RESET);
        simulation.generateSellOfferForRandomPerson();
    }
    void addBuyReqSimulation(){
        LOGGER.info("{}ADDING BUY OFFER SIMULATION{}",MyColor.BLUE_BOLD,MyColor.RESET);
        simulation.generateBuyOfferForRandomPerson();
    }

    void topUpSimulation() {
        LOGGER.info("{}TOP UP SIMULATION.{}",MyColor.BLUE_BOLD,MyColor.RESET);
        Person randomPerson = simulation.getRandomPerson("PERSON");
        LOGGER.debug("{}{}{}", MyColor.CYAN_BOLD, randomPerson, MyColor.RESET);
        simulation.simulateWalletTopUp(randomPerson);
    }

    void randomTransaction() {
        LOGGER.info("{}RANDOM TRANSACTION SIMULATION:{}", MyColor.BLUE_BOLD, MyColor.RESET);
        Person buyer = simulation.getRandomPerson("BUYER");
        Person seller = simulation.getRandomPerson("SELLER");
        MatcherType type = simulation.chooseMatcherType();
        Currency currency = simulation.chooseCurrency();
        buyer.getItemsToBuy().forEach(item -> LOGGER.info("{}BUYER OFFER -- {}{}"
                , MyColor.MAGENTA, item, MyColor.RESET));
        seller.getItemsForSale().forEach(item -> LOGGER.info("{}SELLER OFFER -- {}{}"
                , MyColor.MAGENTA, item, MyColor.RESET));
        Offer randomOffer = simulation.getRandomItemToBuy(buyer.getItemsToBuy());
        buyer.buy(seller, randomOffer.getName(), currency, type);
    }

    void simulatePossibleTransactions() {
        notEnoughMoney();
        System.out.println();
        itemNotFound();
        System.out.println();
        validTransaction();
        System.out.println();
    }

    private void validTransaction() {
        LOGGER.info("{}VALID TRANSACTION SIMULATION:{}", MyColor.BLUE_BOLD, MyColor.RESET);
        Person buyer = simulation.generatePersonWithEmptyWallet("BUYER");
        Person seller = simulation.generatePersonWithEmptyWallet("SELLER");
        Offer offer = simulation.getRandomOffer();
        buyer.addItemToBuy(offer);
        seller.offerItemForSale(offer);
        MatcherType matcherType = simulation.chooseMatcherType();
        Currency currency = simulation.chooseCurrency();
        buyer.receiveMoney(new Money(10000, currency));
        LOGGER.debug("{}BUYER OFFER: {}{}", MyColor.CYAN_BOLD, offer, MyColor.RESET);
        LOGGER.debug("{}SELLER OFFER: {}{}", MyColor.CYAN_BOLD, offer, MyColor.RESET);
        buyer.buy(seller, offer.getName(), currency, matcherType);
    }

    private void notEnoughMoney() {
        LOGGER.info("{}NOT ENOUGH MONEY SIMULATION:{}", MyColor.BLUE_BOLD, MyColor.RESET);
        Person buyer = simulation.generatePersonWithEmptyWallet("BUYER");
        Person seller = simulation.generatePersonWithEmptyWallet("SELLER");
        Offer offer = simulation.getRandomOffer();
        buyer.addItemToBuy(offer);
        seller.offerItemForSale(offer);
        LOGGER.debug("{}BUYER OFFER: {}{}", MyColor.CYAN_BOLD, offer, MyColor.RESET);
        LOGGER.debug("{}SELLER OFFER: {}{}", MyColor.CYAN_BOLD, offer, MyColor.RESET);
        Currency currency = simulation.chooseCurrency();
        MatcherType matcherType = simulation.chooseMatcherType();
        buyer.buy(seller, offer.getName(), currency, matcherType);
    }

    private void itemNotFound() {
        LOGGER.info("{}ITEM NOT FOUND SIMULATION:{}", MyColor.BLUE_BOLD, MyColor.RESET);
        Person buyer = simulation.generatePersonWithEmptyWallet("BUYER");
        Person seller = simulation.generatePersonWithEmptyWallet("SELLER");
        Offer offer = simulation.getRandomOffer();
        buyer.addItemToBuy(offer);
        LOGGER.debug("{}BUYER OFFER: {}{}", MyColor.CYAN_BOLD, offer, MyColor.RESET);
        LOGGER.debug("{}SELLER OFFER: NO OFFERS..{}", MyColor.CYAN_BOLD, MyColor.RESET);
        Currency currency = simulation.chooseCurrency();
        MatcherType matcherType = simulation.chooseMatcherType();
        buyer.buy(seller, offer.getName(), currency, matcherType);
    }


}
