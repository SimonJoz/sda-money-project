package com.company.IO;

import com.company.model.Person;

public class MyPrinter {
    public static void printBuyerList(Person buyer) {
        System.out.println("====== Buyer =====");
        System.out.println("My items: ");
        buyer.getMyItems().forEach(value -> System.out.printf("- %s\n", value));
        System.out.println("Items to buy:");
        buyer.getItemsToBuy().forEach(value -> System.out.printf("- %s\n", value));
        System.out.println();
    }

    public static void printSellerLists(Person seller) {
        System.out.println("====== Seller =====");
        System.out.println("Items for sale: ");
        seller.getItemsForSale().forEach(value -> System.out.printf("- %s\n", value));
        System.out.println();
    }

    public static void printInfo(Person buyer, Person seller) {
        System.out.println();
        printBuyerList(buyer);
        System.out.println(buyer);
        System.out.println();
        printSellerLists(seller);
        System.out.println(seller);
        System.out.println();
    }

    public static void printWallet(Person buyer, String text) {
        System.out.println(text);
        System.out.println(buyer);
        System.out.println();
    }
}
