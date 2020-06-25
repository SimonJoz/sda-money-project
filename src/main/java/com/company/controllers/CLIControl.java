package com.company.controllers;

import com.company.IO.DataReader;
import com.company.enums.Currency;
import com.company.enums.MatcherType;
import com.company.enums.MyColor;
import com.company.exceptions.NoSuchCurrencyException;
import com.company.exceptions.NoSuchMatcherException;
import com.company.model.Money;
import com.company.model.Offer;
import com.company.model.Person;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;

import static com.company.IO.MyPrinter.*;

class CLIControl {
    private final DataReader reader = new DataReader();

    public void mainLoop(Person buyer, Person seller) {
        Options choice;
        do {
            Options.printOptions();
            choice = getOption();
            switch (choice) {
                case BUY -> buy(buyer, seller);
                case GIVE_MONEY -> donateMoney(buyer, seller);
                case TOP_UP_WALLET -> topUp(buyer);
                case PRINT_BUYER_LISTS -> printBuyerList(buyer);
                case PRINT_SELLER_LISTS -> printSellerLists(seller);
                case BUYER_WALLET -> printWallet(buyer, "====== Buyer =====");
                case SELLER_WALLET -> printWallet(seller, "====== Seller =====");
                case ADD_BUY_OFFER -> addOffer(buyer.getItemsToBuy());
                case ADD_SELL_OFFER -> addOffer(seller.getItemsForSale());
                case EXIT -> reader.exit();
                case NO_SUCH_OPTION -> System.err.println(Options.NO_SUCH_OPTION.desc);
            }
        } while (!choice.equals(Options.EXIT));
    }

    private void topUp(Person buyer) {
        Money inputMoney = getInputMoney();
        buyer.receiveMoney(inputMoney);
        System.out.printf("%sWallet top up completed !%s",
                MyColor.MAGENTA_BOLD, MyColor.RESET);
    }

    private void donateMoney(Person buyer, Person seller) {
        Money inputMoney = getInputMoney();
        buyer.giveMoney(seller, inputMoney);
    }

    private void addOffer(List<Offer> offerList) {
        System.out.println("Item name:");
        String item = reader.readString();
        List<Money> prices = new ArrayList<>();
        String exitLoop = "";
        while (!exitLoop.equalsIgnoreCase("q")) {
            System.out.println("Add price: ");
            Money money = getInputMoney();
            prices.add(money);
            System.out.println("Please type \"q\" to exit or any other to add next price: ");
            exitLoop = reader.readString();
        }
        offerList.add(new Offer(item, prices));
    }

    private void buy(Person buyer, Person seller) {
        System.out.println("Required item: ");
        String item = reader.readString();
        MatcherType type = getMatcherType();
        Currency currency = getInputCurrency();
        buyer.buy(seller, item, currency, type);
        printInfo(buyer, seller);
    }

    private MatcherType getMatcherType() {
        MatcherType type = null;
        boolean inputOk = false;
        while (!inputOk) {
            try {
                type = reader.readMatcherType();
                inputOk = true;
            } catch (InputMismatchException e) {
                System.err.println("Wrong input type !! Try again.");
            } catch (NoSuchMatcherException e) {
                System.err.println("No such matcher type !! Try again.");
            }
        }
        return type;
    }

    private Currency getInputCurrency() {
        Currency currency = null;
        boolean inputOk = false;
        while (!inputOk) {
            try {
                currency = reader.readCurrency();
                inputOk = true;
            } catch (NoSuchCurrencyException e) {
                System.err.println("No such currency !! Try again.");
            }
        }
        return currency;
    }

    private Money getInputMoney() {
        Money money = null;
        boolean inputOk = false;
        while (!inputOk) {
            try {
                money = reader.readMoney();
                inputOk = true;
            } catch (InputMismatchException e) {
                System.err.println("Wrong input type !! Try again.");
            } catch (NoSuchCurrencyException e) {
                System.err.println("No such currency !! Try again.");
            }
        }
        return money;
    }

    private Options getOption() {
        boolean inputOk = false;
        Options choice = null;
        while (!inputOk) {
            try {
                choice = Options.getOption(reader.readInt());
                inputOk = true;
            } catch (InputMismatchException e) {
                System.err.println("Wrong input type. Try again !");
            }
        }
        return choice;
    }

    private enum Options {
        BUY(0, "BUY ITEM."),
        GIVE_MONEY(1, "DONATE MONEY."),
        TOP_UP_WALLET(2, "TOP UP WALLET."),
        PRINT_BUYER_LISTS(3, "BUYER OFFERS"),
        PRINT_SELLER_LISTS(4, "SELLER OFFERS"),
        BUYER_WALLET(5, "BUYER WALLET"),
        SELLER_WALLET(6, "SELLER WALLET"),
        ADD_BUY_OFFER(7, "ADD BUY OFFER"),
        ADD_SELL_OFFER(8, "ADD SELL OFFER"),
        EXIT(9, "EXIT"),
        NO_SUCH_OPTION(10, "NO SUCH OPTION");

        private final int id;
        private final String desc;

        Options(int id, String desc) {
            this.id = id;
            this.desc = desc;
        }

        public static Options getOption(int id) {
            if (id >= Options.values().length) {
                return Options.NO_SUCH_OPTION;
            }
            return Options.values()[id];
        }

        public static void printOptions() {
            for (int i = 0; i < Options.values().length - 1; i++) {
                Options options = getOption(i);
                System.out.printf("%1d -- %s\n", options.id, options.desc);
            }

        }
    }
}
