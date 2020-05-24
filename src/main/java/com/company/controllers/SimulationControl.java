package com.company.controllers;

import com.company.enums.Currency;
import com.company.enums.MatcherType;
import com.company.enums.MyColor;
import com.company.model.Money;
import com.company.model.Offer;
import com.company.model.Person;
import com.company.model.Simulation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

class SimulationControl {
    private static final Logger LOGGER = LoggerFactory.getLogger(SimulationControl.class);
    private Simulation simulation;

    public SimulationControl(Simulation simulation) {
        this.simulation = simulation;
    }

    public void simulation() throws InterruptedException {
        simulatePossibleTransactions();
        simulation.generateSellOffersForAllPeoples(10);
        simulation.generateBuyOffersForAllPeoples(5);

        int cores = Runtime.getRuntime().availableProcessors();
        ScheduledExecutorService executorService = Executors.newScheduledThreadPool(cores);

        executorService.scheduleWithFixedDelay(this::randomTransaction, 2, 5, TimeUnit.SECONDS);
        executorService.scheduleWithFixedDelay(this::topUpSimulation, 3, 5, TimeUnit.SECONDS);
        executorService.scheduleWithFixedDelay(this::offerItemForSaleSimulation, 4, 5, TimeUnit.SECONDS);
        executorService.scheduleWithFixedDelay(this::addBuyReqSimulation, 5, 5, TimeUnit.SECONDS);

        executorService.awaitTermination(60, TimeUnit.SECONDS);
        executorService.shutdown();
    }

    void offerItemForSaleSimulation() {
        System.out.printf("%40sADDING SALE OFFER SIMULATION%s\n", MyColor.BLUE_BOLD, MyColor.RESET);
        Person seller = simulation.getRandomPerson("SELLER");
        seller.logItemsForSale();
        LOGGER.info("GENERATING SELL OFFER");
        simulation.generateUniqueRandomOffers(seller.getItemsForSale(), 1);
        seller.logItemsForSale();
        LOGGER.info("COMPLETED !\n");


    }

    void addBuyReqSimulation() {
        System.out.printf("%40sADDING BUY OFFER SIMULATION%s\n", MyColor.BLUE_BOLD, MyColor.RESET);
        Person buyer = simulation.getRandomPerson("BUYER");
        buyer.logItemsToBuy();
        LOGGER.info("GENERATING BUY OFFER");
        simulation.generateUniqueRandomOffers(buyer.getItemsToBuy(), 1);
        buyer.logItemsToBuy();
        LOGGER.info("COMPLETED !\n");
    }

    void topUpSimulation() {
        System.out.printf("%40sWALLET TOP UP SIMULATION%s\n", MyColor.BLUE_BOLD, MyColor.RESET);
        simulation.simulateWalletTopUp();
        LOGGER.info("TOP UP COMPLETED !\n");
    }

    void randomTransaction() {
        System.out.printf("%40sRANDOM TRANSACTION SIMULATION%s\n", MyColor.BLUE_BOLD, MyColor.RESET);
        Person buyer = simulation.getRandomPerson("BUYER");
        Person seller = simulation.getRandomPerson("SELLER");
        MatcherType type = simulation.chooseMatcherType();
        Currency currency = simulation.chooseCurrency();
        seller.logItemsForSale();
        buyer.logItemsToBuy();
        Offer randomOffer = simulation.getRandomItemToBuy(buyer.getItemsToBuy());
        buyer.buy(seller, randomOffer.getName(), currency, type);
    }


    void simulatePossibleTransactions() {
        notEnoughMoney();
        itemNotFound();
        validTransaction();
    }

    private void validTransaction() {
        System.out.printf("%40sVALID TRANSACTION SIMULATION EXAMPLE%s\n", MyColor.BLUE_BOLD, MyColor.RESET);
        Person buyer = simulation.generatePersonWithEmptyWallet("BUYER");
        Person seller = simulation.generatePersonWithEmptyWallet("SELLER");
        LOGGER.info("GENERATING ITEMS");
        Offer offer = simulation.getRandomOffer();
        buyer.addItemToBuy(offer);
        seller.addItemForSale(offer);
        MatcherType matcherType = simulation.chooseMatcherType();
        Currency currency = simulation.chooseCurrency();
        buyer.receiveMoney(new Money(10000, currency));
        seller.logItemsForSale();
        buyer.logItemsToBuy();
        buyer.buy(seller, offer.getName(), currency, matcherType);
    }

    private void notEnoughMoney() {
        System.out.printf("%40sNOT ENOUGH MONEY SIMULATION EXAMPLE%s\n", MyColor.BLUE_BOLD, MyColor.RESET);
        Person buyer = simulation.generatePersonWithEmptyWallet("BUYER");
        Person seller = simulation.generatePersonWithEmptyWallet("SELLER");
        LOGGER.info("GENERATING ITEMS");
        Offer offer = simulation.getRandomOffer();
        buyer.addItemToBuy(offer);
        seller.addItemForSale(offer);
        seller.logItemsForSale();
        buyer.logItemsToBuy();
        Currency currency = simulation.chooseCurrency();
        MatcherType matcherType = simulation.chooseMatcherType();
        buyer.buy(seller, offer.getName(), currency, matcherType);
    }

    private void itemNotFound() {
        System.out.printf("%40sITEM NOT FOUND SIMULATION EXAMPLE%s\n", MyColor.BLUE_BOLD, MyColor.RESET);
        Person buyer = simulation.generatePersonWithEmptyWallet("BUYER");
        Person seller = simulation.generatePersonWithEmptyWallet("SELLER");
        Offer offer = simulation.getRandomOffer();
        seller.logItemsForSale();
        buyer.addItemToBuy(offer);
        buyer.logItemsToBuy();
        MatcherType matcherType = simulation.chooseMatcherType();
        Currency currency = simulation.chooseCurrency();
        buyer.buy(seller, offer.getName(), currency, matcherType);
    }


}
