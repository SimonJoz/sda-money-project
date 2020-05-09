package com.company.model;

import com.company.enums.Currency;
import com.company.enums.MatcherType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class SimulationTest {
    private Simulation simulation = new Simulation();
    private List<Person> peoples = simulation.getPeoples();
    private List<Offer> offers = simulation.getOffers();


    @BeforeEach
    public void setup() {
        peoples.add(new Person("NAME1", "SURNAME1"));
        peoples.add(new Person("NAME2", "SURNAME2"));
        peoples.add(new Person("NAME3", "SURNAME3"));

        offers.add(new Offer("OFFER1", new ArrayList<>(List.of(new Money(5, Currency.PLN)))));
        offers.add(new Offer("OFFER2", new ArrayList<>(List.of(new Money(5, Currency.USD)))));
        offers.add(new Offer("OFFER3", new ArrayList<>(List.of(new Money(5, Currency.EUR)))));
        offers.add(new Offer("OFFER4", new ArrayList<>(List.of(new Money(5, Currency.GBP)))));
        offers.add(new Offer("OFFER3", new ArrayList<>(List.of(new Money(5, Currency.GBP)))));
        offers.add(new Offer("OFFER4", new ArrayList<>(List.of(new Money(5, Currency.GBP)))));

    }


    @Test
    void should_generate_person_with_empty_wallet() {
        Money money = new Money(10, Currency.USD);
        peoples.forEach(person -> person.getWallet().getMoneyMap().put(Currency.USD, money));
        long peoplesWithMoneyCount = peoples.stream()
                .filter(person -> person.getWallet().getMoneyMap().size() == 1)
                .count();

        assertEquals(peoples.size(), peoplesWithMoneyCount);
        Person person = simulation.generatePersonWithEmptyWallet("PERSON");
        assertTrue(person.getWallet().getMoneyMap().isEmpty());
    }

    @Test
    void should_get_random_person_from_imported_list() {
        assertTrue(peoples.contains(simulation.getRandomPerson("PERSON")));
    }

    @Test
    void should_get_random_offer_from_imported_list() {
        assertTrue(offers.contains(simulation.getRandomOffer()));
    }


    @Test
    void should_return_random_MatcherType() {
        MatcherType type = simulation.chooseMatcherType();
        assertTrue(Arrays.asList(MatcherType.values()).contains(type));
    }

    @Test
    void should_return_random_Currency() {
        Currency currency = simulation.chooseCurrency();
        assertTrue(Arrays.asList(Currency.values()).contains(currency));
    }

    @Test
    void should_return_random_Money() {
        Money money = simulation.generateMoney();
        assertTrue(money.getAmount().compareTo(BigDecimal.ZERO) >= 0 &&
                money.getAmount().compareTo(BigDecimal.valueOf(Simulation.TOP_UP_LIMIT)) < 0);
    }

    @Test
    void should_generate_unique_buy_offers_for_all_peoples() {
        simulation.generateBuyOffersForAllPeoples(3);
        long peoplesWithOffersCount = peoples.stream()
                .filter(person -> person.getItemsToBuy().size() == 3)
                .count();
        assertEquals(peoples.size(), peoplesWithOffersCount);
    }

    @Test
    void should_generate_unique_sell_offers_for_all_peoples() {
        simulation.generateSellOffersForAllPeoples(3);
        long peoplesWithOffersCount = peoples.stream()
                .filter(person -> person.getItemsForSale().size() == 3)
                .count();
        assertEquals(peoples.size(), peoplesWithOffersCount);
    }

    @Test
    void should_generate_unique_random_offers() {
        Person person = simulation.getRandomPerson("PERSON");
        List<Offer> itemsForSale = person.getItemsForSale();
        List<Offer> itemsToBuy = person.getItemsToBuy();
        simulation.generateUniqueRandomOffers(itemsForSale, 4);
        simulation.generateUniqueRandomOffers(itemsToBuy, 4);
        assertEquals(4, itemsForSale.size());
        assertEquals(4, itemsToBuy.size());

        long forSaleDistinctCount = itemsForSale.stream()
                .distinct()
                .count();
        long toBuyDistinctCount = itemsToBuy.stream()
                .distinct()
                .count();

        assertEquals(itemsForSale.size(), forSaleDistinctCount);
        assertEquals(itemsToBuy.size(), toBuyDistinctCount);
    }
}