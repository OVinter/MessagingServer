package com.example.pcbe;

import apps.CoffeeProviderApp;
import apps.CoffeeShopApp;
import apps.CustomerApp;

public class JarRunner {

    // TODO: based on User's name that is try to login, make a main call to that app
    // ex: if User Alex is a CUSTOMER type then call CustomerApp.main(args);
    public static void startApp(String[] args) {
        if(args.length == 0) {
            PcbeApplication.main(args);
        } else if("Customer".equals(args[0])) {
            CustomerApp.main(args);
        } else if("Shop".equals(args[0])) {
            CoffeeShopApp.main(args);
        } else if("Provider".equals(args[0])) {
            CoffeeProviderApp.main(args);
        } else {
            System.out.println("Bad argument!\nTry again");
        }
    }

    public static void main(String[] args) {
        // TODO: make a menu with: 1.Login and 2.Register
        // TODO: let a new customer to register (you also need to update the user table)
        startApp(args);
    }
}
