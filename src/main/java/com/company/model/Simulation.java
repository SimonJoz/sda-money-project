package com.company.model;

import com.company.IO.file.FileManager;
import com.company.enums.Currency;
import com.company.enums.MatcherType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Simulation {
    private static final Logger LOGGER = LoggerFactory.getLogger(Simulation.class);
    public static final int TOP_UP_LIMIT = 1000;
    private List<Person> peoples = new ArrayList<>();
    private List<Offer> offers = new ArrayList<>();
    private Random random = new Random();
    private FileManager fileManager = new FileManager();


    public Person generatePersonWithEmptyWallet(String text) {
        LOGGER.info("GENERATING PERSON WITH EMPTY WALLET.");
        Person person = peoples.get(random.nextInt(peoples.size()));
        Person personWithEmptyWallet = new Person(person.getName(), person.getSurname());
        LOGGER.debug("{} - {}.", text, personWithEmptyWallet);
        return personWithEmptyWallet;
    }

    public Person getRandomPerson(String text) {
        LOGGER.info("GENERATING RANDOM PERSON.");
        Person person = peoples.get(random.nextInt(peoples.size()));
        LOGGER.debug("{} - {}.", text, person);
        return person;
    }

    public Offer getRandomOffer() {
        return offers.get(random.nextInt(offers.size()));
    }

    public Offer getRandomItemToBuy(List<Offer> list) {
        return list.get(random.nextInt(list.size()));
    }

    public MatcherType chooseMatcherType() {
        LOGGER.info("CHOOSING RANDOM MATCHER TYPE");
        MatcherType type = MatcherType.values()[random.nextInt(MatcherType.values().length)];
        LOGGER.debug("MATCHER TYPE - {}.", type);
        return type;
    }

    public Currency chooseCurrency() {
        LOGGER.info("CHOOSING RANDOM CURRENCY.");
        Currency currency = Currency.values()[random.nextInt(Currency.values().length)];
        LOGGER.debug("PAYMENT CURRENCY - {}.", currency);
        return currency;
    }

    public Money generateMoney() {
        LOGGER.info("GENERATING MONEY.");
        Money money = new Money(BigDecimal.valueOf(random.nextInt(TOP_UP_LIMIT)), chooseCurrency());
        LOGGER.debug("AMOUNT - {}", money);
        return money;
    }

    public void simulateWalletTopUp() {
        Person randomPerson = getRandomPerson("PERSON");
        randomPerson.receiveMoney(generateMoney());
        LOGGER.debug("{}", randomPerson);
    }

    public void generateBuyOffersForAllPeoples(int number) {
        LOGGER.info("GENERATING BUY OFFERS:");
        peoples.forEach(person -> generateUniqueRandomOffers(person.getItemsToBuy(), number));
        LOGGER.info("GENERATING OFFERS COMPLETED !\n");

    }

    public void generateSellOffersForAllPeoples(int number) {
        LOGGER.info("GENERATING SELL OFFERS:");
        peoples.forEach(person -> generateUniqueRandomOffers(person.getItemsForSale(), number));
        LOGGER.info("GENERATING OFFERS COMPLETED !\n");
    }

    public void generateUniqueRandomOffers(List<Offer> list, int number) {
        int i = 0;
        while (i < number) {
            Offer randomOffer = getRandomOffer();
            if (!list.contains(randomOffer)) {
                list.add(randomOffer);
                LOGGER.debug("OFFER ADDED TO LIST - {}.", randomOffer);
                i++;
            }
        }
    }

    public void provideDataForSimulation() {
        peoples = fileManager.importPeople("src/main/java/com/company/peoples");
        offers = fileManager.importOffers("src/main/java/com/company/offers");
    }

    public List<Person> getPeoples() {
        return peoples;
    }

    public List<Offer> getOffers() {
        return offers;
    }
}
