package com.company.IO;

import com.company.enums.Currency;
import com.company.enums.MatcherType;
import com.company.enums.MyColor;
import com.company.model.Person;

import java.util.Arrays;
import java.util.function.Consumer;

public class MyPrinter {
    private static final Consumer<Object> CONSUMER =
            value -> System.out.printf("%s%3s %s%s\n", MyColor.CYAN_BOLD, "-", value, MyColor.RESET);

    public static void printBuyerList(Person buyer) {
        System.out.printf("%s====== Buyer ======%s\nMy items:\n"
                , MyColor.YELLOW_BOLD, MyColor.RESET);
        if (buyer.getMyItems().isEmpty()) {
            System.out.println("empty..");
        } else {
            buyer.getMyItems().forEach(CONSUMER);
        }
        System.out.println("Buy offers: ");
        if (buyer.getItemsToBuy().isEmpty()) {
            System.out.println("empty..");
        } else {
            buyer.getItemsToBuy().forEach(CONSUMER);
        }
    }

    public static void printSellerLists(Person seller) {
        System.out.printf("%s====== Seller ======%s\nSale offers:\n"
                , MyColor.YELLOW_BOLD, MyColor.RESET);
        if (seller.getItemsForSale().isEmpty()) {
            System.out.println("empty..");
        } else {
            seller.getItemsForSale().forEach(CONSUMER);
        }
    }

    public static void printMatchers() {
        Arrays.stream(MatcherType.values())
                .forEach(matcher -> System.out.printf("%s%3d - %s%s\n", MyColor.CYAN_BOLD
                        , matcher.getId(), matcher.getDescription(), MyColor.RESET));
    }

    public static void printCurrencies() {
        Arrays.stream(Currency.values()).forEach(CONSUMER);
    }

    public static void printInfo(Person buyer, Person seller) {
        printBuyerList(buyer);
        printWallet(buyer,"");
        printSellerLists(seller);
        printWallet(seller,"");
    }

    public static void printWallet(Person person, String text) {
        System.out.printf("%5s%s%s\n", MyColor.YELLOW_BOLD, text, MyColor.RESET);
        System.out.printf("%s%s%s\n\n", MyColor.BLUE_BOLD, person, MyColor.RESET);
    }
}
