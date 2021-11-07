package apps;

import entities.messages.Message;
import entities.users.User;
import entities.users.UserType;

public class CoffeeProviderApp{

    private User coffeeProvider;

    // if a user who is a PROVIDER and logs in, then we create new instance of CoffeeProviderApp
    // maybe we can have more providers, that's why is not a singleton
    public CoffeeProviderApp(String name) {
        this.coffeeProvider = new User(name, UserType.PROVIDER);
    }

    public CoffeeProviderApp() {}

    public void receiveMessage(Message message) {
        // TODO
    }

    public void sendMessage(Message message) {
        // TODO
    }

    public User getCoffeeProvider() {
        return coffeeProvider;
    }

    public void setCoffeeProvider(String name) {
        this.coffeeProvider = new User(name, UserType.PROVIDER);
    }

    public static void main(String[] args) {
        System.out.println("CoffeeProvider App");
    }
}
