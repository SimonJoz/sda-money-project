package com.company;

public class Main {

    public static void main(String[] args) throws InterruptedException {
        long start = System.currentTimeMillis();
        MainControl control = new MainControl();
        control.mainMenu(args);
        long end = System.currentTimeMillis();
        System.out.println(end - start);





    }
}