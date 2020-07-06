package com.company.model;

import com.company.IO.file.FileManager;
import com.company.enums.Currency;
import com.company.enums.MatcherType;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Slf4j
public class Simulation {
    public static final int TOP_UP_LIMIT = 1000;
    @Getter
    private List<Person> peoples = new ArrayList<>();
    @Getter
    private List<Offer> offers = new ArrayList<>();
    private final Random random = new Random();
    private final FileManager fileManager = new FileManager();


    public Person generatePersonWithEmptyWallet(String text) {
        log.info("GENERATING PERSON WITH EMPTY WALLET.");
        Person person = peoples.get(random.nextInt(peoples.size()));
        Person personWithEmptyWallet = new Person(person.getName(), person.getSurname());
        log.debug("{} - {}.", text, personWithEmptyWallet);
        return personWithEmptyWallet;
    }

    public Person getRandomPerson(String text) {
        log.info("GENERATING RANDOM PERSON.");
        Person person = peoples.get(random.nextInt(peoples.size()));
        log.debug("{} - {}.", text, person);
        return person;
    }

    public Offer getRandomOffer() {
        return offers.get(random.nextInt(offers.size()));
    }

    public Offer getRandomItemToBuy(List<Offer> list) {
        return list.get(random.nextInt(list.size()));
    }

    public MatcherType chooseMatcherType() {
        log.info("CHOOSING RANDOM MATCHER TYPE");
        MatcherType type = MatcherType.values()[random.nextInt(MatcherType.values().length)];
        log.debug("MATCHER TYPE - {}.", type);
        return type;
    }

    public Currency chooseCurrency() {
        log.info("CHOOSING RANDOM CURRENCY.");
        Currency currency = Currency.values()[random.nextInt(Currency.values().length)];
        log.debug("PAYMENT CURRENCY - {}.", currency);
        return currency;
    }

    public Money generateMoney() {
        log.info("GENERATING MONEY.");
        Money money = new Money(BigDecimal.valueOf(random.nextInt(TOP_UP_LIMIT)), chooseCurrency());
        log.debug("AMOUNT - {}", money);
        return money;
    }

    public void simulateWalletTopUp() {
        Person randomPerson = getRandomPerson("PERSON");
        randomPerson.receiveMoney(generateMoney());
        log.debug("{}", randomPerson);
    }

    public void generateBuyOffersForAllPeoples(int number) {
        log.info("GENERATING BUY OFFERS:");
        peoples.forEach(person -> generateUniqueRandomOffers(person.getItemsToBuy(), number));
        log.info("GENERATING OFFERS COMPLETED !\n");

    }

    public void generateSellOffersForAllPeoples(int number) {
        log.info("GENERATING SELL OFFERS:");
        peoples.forEach(person -> generateUniqueRandomOffers(person.getItemsForSale(), number));
        log.info("GENERATING OFFERS COMPLETED !\n");
    }

    public void generateUniqueRandomOffers(List<Offer> list, int number) {
        int i = 0;
        while (i < number) {
            Offer randomOffer = getRandomOffer();
            if (!list.contains(randomOffer)) {
                list.add(randomOffer);
                log.debug("OFFER ADDED TO LIST - {}.", randomOffer);
                i++;
            }
        }
    }

    public void provideDataForSimulation() {
        peoples = fileManager.importPeople("src/main/java/com/company/peoples");
        offers = fileManager.importOffers("src/main/java/com/company/offers");
    }
}
