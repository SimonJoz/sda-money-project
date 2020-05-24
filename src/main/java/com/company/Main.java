package com.company;

import com.company.controllers.MainControl;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        MainControl mainControl = new MainControl();
        mainControl.mainMenu(args);
    }
}
