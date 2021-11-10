package apps;

import appsUtilities.CoffeeShop;
import entities.Coffee;
import entities.messages.PrivateMessage;

import java.util.ArrayList;

public final class CoffeeShopApp {

    // one instance of CoffeeShop
    private CoffeeShop coffeeShop;
    private static volatile CoffeeShopApp instance;

    // if a user who is a COFFEE_ADMIN and logs in, then we create new instance of CoffeeProviderApp
    private CoffeeShopApp(String shopName) {
        initialize(shopName);
    }

    public CoffeeShopApp() {}

    public void receiveMessage() {
        // TODO
    }

    public void sendMessage(PrivateMessage message) {
        // TODO
    }

    // singleton instance (we don't need multiple instances of coffeeShopApp)
    public static CoffeeShopApp getInstance(String shopName) {

        CoffeeShopApp resultCoffeeShop = instance;

        if (resultCoffeeShop != null) {
            return resultCoffeeShop;
        }

        synchronized (CoffeeShop.class) {
            if(instance == null) {
                instance = new CoffeeShopApp(shopName);
            }
            return instance;
        }
    }

    // get the singleton instance of the CoffeeShop
    public void initialize(String shopName) {
        // TODO : remove lines 43-50 and line 52

        this.coffeeShop = CoffeeShop.getInstance(shopName);
        coffeeShop.setNrOfCoffees(10);
    }


    public CoffeeShop getCoffeeShop() {
        return coffeeShop;
    }

    public static void main(String[] args) {
        System.out.println("CoffeeShop App");
    }
}
