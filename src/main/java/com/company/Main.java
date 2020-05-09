package com.company;

import com.company.controler.MainControl;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        MainControl mainControl = new MainControl();
        mainControl.mainMenu(args);
    }
}
