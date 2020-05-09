//package com.company.controler;
//
//import com.company.model.Offer;
//import com.company.model.Person;
//import com.company.model.Simulation;
//import org.junit.jupiter.api.Test;
//
//import java.util.List;
//
//import static org.junit.jupiter.api.Assertions.*;
//
//class SimulationControlTest {
//
//    private SimulationControl control = new SimulationControl(new Simulation());
//
//    @Test
//    void should_add_auto_generated_sell_offer_for_random_person() {
//        generateSellOffersForAllPeoples();
//        Offer offer = simulation.getRandomOffer();
//        Person person = simulation.getRandomPerson("PERSON");
//        List<Offer> itemsForSale = person.getItemsForSale();
//        itemsForSale.add(offer);
//        assertTrue(itemsForSale.contains(offer));
//    }
//    @Test
//    void should_add_auto_generated_buy_offer_for_random_person(){
//        Offer offer = offers.get(random.nextInt(offers.size()));
//        Person person = peoples.get(random.nextInt(peoples.size()));
//        List<Offer> itemsToBuy = person.getItemsToBuy();
//        itemsToBuy.add(offer);
//        assertTrue(itemsToBuy.contains(offer));
//
//    @Test
//    void topUpSimulation() {
//    }
//
//    @Test
//    void randomTransaction() {
//    }
//}