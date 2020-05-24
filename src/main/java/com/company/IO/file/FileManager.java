package com.company.IO.file;

import com.company.enums.Currency;
import com.company.model.Money;
import com.company.model.Offer;
import com.company.model.Person;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class FileManager {
    private static final Logger LOGGER = LoggerFactory.getLogger(FileManager.class);

    public List<Person> importPeople(String path) {
        List<Person> people = new ArrayList<>();
        try {
            Files.newBufferedReader(Path.of(path), StandardCharsets.UTF_8)
                    .lines().forEach(line -> people.add(createPerson(line)));
            LOGGER.info("PEOPLES IMPORT SUCCESSFUL.");
        } catch (IOException e) {
            LOGGER.error("FILE \"{}\" NOT FOUND.", path);
            System.exit(0);
        }
        return people;
    }

    public List<Offer> importOffers(String path) {
        List<Offer> offers = new ArrayList<>();
        try {
            Files.newBufferedReader(Path.of(path), StandardCharsets.UTF_8)
                    .lines().forEach(line -> offers.add(createOffer(line)));
            LOGGER.info("OFFERS IMPORT SUCCESSFUL.");
        } catch (IOException e) {
            LOGGER.error("FILE \"{}\" NOT FOUND.",path);
            System.exit(0);
        }
        return offers;
    }

    private Person createPerson(String line) {
        String[] words = line.split(";");
        Person person = new Person(words[0], words[1]);
        for (int i = 2; i < words.length; i += 2) {
            Currency currency = Currency.valueOf(words[i]);
            BigDecimal amount = new BigDecimal(words[i + 1]);
            person.getWallet().getMoneyMap()
                    .put(currency, new Money(amount, currency));
        }
        return person;
    }

    private Offer createOffer(String line) {
        List<Money> prices = new ArrayList<>();
        String[] words = line.split(";");
        String itemName = words[0];
        for (int i = 1; i < words.length; i += 2) {
            Currency currency = Currency.valueOf(words[i]);
            BigDecimal amount = new BigDecimal(words[i + 1]);
            prices.add(new Money(amount, currency));
        }
        return new Offer(itemName, prices);
    }
}
