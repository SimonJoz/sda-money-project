package com.company.controllers;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;
import com.company.IO.file.FileManager;
import com.company.model.Money;
import com.company.model.Person;
import com.company.model.Simulation;
import com.company.model.Wallet;
import org.slf4j.LoggerFactory;

public class MainControl {
    private static final String SIMULATION_MODE = "SIMULATION";
    private static final String CLI_MODE = "CLI";

    private final CLIControl control = new CLIControl();
    private final Simulation simulation = new Simulation();
    private SimulationControl simulationControl = new SimulationControl(simulation);

    public void mainMenu(String[] args) throws InterruptedException {
        String mode = SIMULATION_MODE;
        if (args.length >= 1) {
            mode = args[0];
        }
        switch (mode) {
            case CLI_MODE -> {
                disableLoggers();
                simulation.provideDataForSimulation();
                Person buyer = simulation.getRandomPerson("Buyer");
                Person seller = simulation.getRandomPerson("Seller");
                simulation.generateUniqueRandomOffers(buyer.getItemsToBuy(), 10);
                simulation.generateUniqueRandomOffers(seller.getItemsForSale(), 10);
                control.mainLoop(buyer, seller);
            }
            case SIMULATION_MODE -> {
                simulation.provideDataForSimulation();
                simulationControl.simulation();
            }
        }
    }

    private void disableLoggers() {
        ch.qos.logback.classic.Logger fileManager =
                (ch.qos.logback.classic.Logger) LoggerFactory.getLogger(FileManager.class);
        Logger simulationLog = (Logger) LoggerFactory.getLogger(Simulation.class);
        Logger person = (Logger) LoggerFactory.getLogger(Person.class);
        Logger wallet = (Logger) LoggerFactory.getLogger(Wallet.class);
        Logger money = (Logger) LoggerFactory.getLogger(Money.class);

        fileManager.setLevel(Level.OFF);
        simulationLog.setLevel(Level.OFF);
        person.setLevel(Level.OFF);
        wallet.setLevel(Level.OFF);
        money.setLevel(Level.OFF);
    }

}