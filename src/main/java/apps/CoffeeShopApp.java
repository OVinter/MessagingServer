package apps;

import appsUtilities.CoffeeShop;
import entities.Coffee;
import entities.messages.Message;

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

    public void receiveMessage(Message message) {
        // TODO
    }

    public void sendMessage(Message message) {
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
        Coffee coffee1 = new Coffee("latte",10);
        Coffee coffee2 = new Coffee("espresso",7);
        Coffee coffee3 = new Coffee("latte",10);

        ArrayList<Coffee> coffees = new ArrayList<>();
        coffees.add(coffee1);
        coffees.add(coffee2);
        coffees.add(coffee3);
        this.coffeeShop = CoffeeShop.getInstance(shopName);
        coffeeShop.setCoffees(coffees);
    }


    public CoffeeShop getCoffeeShop() {
        return coffeeShop;
    }

    public static void main(String[] args) {
        System.out.println("CoffeeShop App");
    }
}
