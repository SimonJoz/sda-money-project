package com.company.IO;

import com.company.model.Person;

public class MyPrinter {
    public static void printBuyerList(Person buyer) {
        System.out.print("====== Buyer =====\nMy items: ");

        if (buyer.getMyItems().isEmpty()) {
            System.out.println("Empty..");
        } else {
            System.out.println();
            buyer.getMyItems().forEach(value -> System.out.printf("- %s\n", value));
        }
        System.out.print("Items to buy: ");
        if (buyer.getItemsToBuy().isEmpty()) {
            System.out.println("empty..");
        } else {
            System.out.println();
            buyer.getItemsToBuy().forEach(value -> System.out.printf("- %s\n", value));
        }
        System.out.println();
    }

    public static void printSellerLists(Person seller) {
        System.out.print("====== Seller =====\nItems for sale: ");
        if (seller.getItemsForSale().isEmpty()) {
            System.out.println("empty..");
        } else {
            System.out.println();
            seller.getItemsForSale().forEach(value -> System.out.printf("- %s\n", value));
        }
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
