package com.company.controler;

import com.company.model.Person;
import com.company.model.Simulation;

public class MainControl {
    private static final String SIMULATION_MODE = "SIMULATION";
    private static final String CLI_MODE = "CLI";

    private CLIControl control = new CLIControl();
    private Simulation simulation = new Simulation();
    private SimulationControl simulationControl = new SimulationControl(simulation);

    public void mainMenu(String[] args) throws InterruptedException {
        String mode = SIMULATION_MODE;
        simulation.provideDataForSimulation();
        if (args.length >= 1) {
            mode = args[0];
        }
        switch (mode) {
            case CLI_MODE:
                Person buyer = simulation.getRandomPerson("Buyer");
                Person seller = simulation.getRandomPerson("Seller");
                simulation.generateUniqueRandomOffers(buyer.getItemsToBuy(), 10);
                simulation.generateUniqueRandomOffers(seller.getItemsForSale(), 10);
                control.mainLoop(buyer, seller);
                break;
            case SIMULATION_MODE:
                simulationControl.simulation();
        }
    }
}