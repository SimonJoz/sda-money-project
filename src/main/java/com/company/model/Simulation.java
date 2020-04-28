package com.company.model;

import com.company.IO.file.FileManager;
import com.company.enums.Currency;
import com.company.enums.MatcherType;
import com.company.enums.MyColor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.util.List;
import java.util.Random;

public class Simulation {
    private static final Logger LOGGER = LoggerFactory.getLogger(Simulation.class);
    private static final int TOP_UP_LIMIT = 1000;
    private List<Person> peoples;
    private List<Offer> offers;
    private Random random = new Random();
    private FileManager fileManager = new FileManager();


    public void generateSellOfferForRandomPerson(){
        LOGGER.info("{}GENERATING SELL OFFER :{}", MyColor.YELLOW_BOLD, MyColor.RESET);
        getRandomPerson("SELLER").getItemsForSale().add(getRandomOffer());
    }
    public void generateBuyOfferForRandomPerson(){
        LOGGER.info("{}GENERATING BUY OFFER :{}", MyColor.YELLOW_BOLD, MyColor.RESET);
        getRandomPerson("BUYER").getItemsToBuy().add(getRandomOffer());
    }

    public Person generatePersonWithEmptyWallet(String text) {
        Person person = peoples.get(random.nextInt(peoples.size()));
        Person personWithEmptyWallet = new Person(person.getName(), person.getSurname());
        LOGGER.debug("{}GENERATED {} -- {}.{}", MyColor.CYAN_BOLD, text, personWithEmptyWallet, MyColor.RESET);
        return personWithEmptyWallet;
    }

    public Person getRandomPerson(String text) {
        Person person = peoples.get(random.nextInt(peoples.size()));
        LOGGER.debug("{}{} -- {}.{}", MyColor.CYAN_BOLD, text, person, MyColor.RESET);
        return person;
    }

    public Offer getRandomOffer() {
        return offers.get(random.nextInt(offers.size()));
    }

    public Offer getRandomItemToBuy(List<Offer> list) {
        return list.get(random.nextInt(list.size()));
    }

    public MatcherType chooseMatcherType() {
        MatcherType type = MatcherType.values()[random.nextInt(MatcherType.values().length)];
        LOGGER.debug("{}CHOSEN MATCHER TYPE -- {}.{}", MyColor.CYAN_BOLD, type, MyColor.RESET);
        return type;
    }

    public Currency chooseCurrency() {
        Currency currency = Currency.values()[random.nextInt(Currency.values().length)];
        LOGGER.debug("{}CHOSEN CURRENCY TO PAY IN -- {}.{}", MyColor.CYAN_BOLD, currency, MyColor.RESET);
        return currency;
    }

    public Money generateMoney() {
        return new Money(BigDecimal.valueOf(random.nextInt(TOP_UP_LIMIT)), chooseCurrency());
    }

    public void simulateWalletTopUp(Person person) {
        person.receiveMoney(generateMoney());
    }

    public void generateBuyOffersForAllPeoples(int number) {
        LOGGER.info("{}GENERATING BUY OFFERS:{}", MyColor.YELLOW_BOLD, MyColor.RESET);
        peoples.forEach(person -> generateUniqueRandomOffers(person.getItemsToBuy(), number));
    }

    public void generateSellOffersForAllPeoples(int number) {
        LOGGER.info("{}GENERATING SELL OFFERS:{}", MyColor.YELLOW_BOLD, MyColor.RESET);
        peoples.forEach(person -> generateUniqueRandomOffers(person.getItemsForSale(),number));
    }

    private void generateUniqueRandomOffers(List<Offer> list, int number) {
        int i = 0;
        while (i < number) {
            Offer randomOffer = getRandomOffer();
            if (!list.contains(randomOffer)) {
                list.add(randomOffer);
                i++;
            }
        }
    }

    public void importData() {
        peoples = fileManager.importPeople("./peoples");
        offers = fileManager.importOffers("./offers");
    }
}
