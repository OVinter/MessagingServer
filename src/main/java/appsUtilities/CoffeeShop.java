package appsUtilities;

import entities.Coffee;
import entities.users.User;
import entities.users.UserType;

import java.util.ArrayList;
import java.util.UUID;

// TODO: a table
public final class CoffeeShop {

    private static  volatile CoffeeShop instance;
    private UUID id;
    private String name;
    private ArrayList<Coffee> coffees;
    // one user - Coffee Admin
    private User user;

    private CoffeeShop(String name) {
        this.name = name;
        this.id = UUID.randomUUID();
        this.coffees = new ArrayList<>(5);
        this.user = null;
    }

    // we need singleton instance for CoffeeShop
    public static CoffeeShop getInstance(String name) {

        CoffeeShop resultCoffeeShop = instance;

        if (resultCoffeeShop != null) {
            return resultCoffeeShop;
        }

        synchronized (CoffeeShop.class) {
            if(instance == null) {
                instance = new CoffeeShop(name);
            }
            return instance;
        }
    }

    public ArrayList<Coffee> getCoffees() {
        return coffees;
    }

    public void setCoffees(ArrayList<Coffee> coffees) {
        this.coffees = coffees;
    }

    public void addNewCoffee(Coffee coffee) {
        this.coffees.add(coffee);
    }

    public void deleteCoffee(Coffee coffee) {
        this.coffees.remove(coffee);
    }

    public UUID getId() {
        return id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(String name) {
        this.user.setName(name);
        this.user.setUserType(UserType.COFFEE_ADMIN);
    }

    @Override
    public String toString() {
        return "CoffeeShop{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", coffees=" + coffees +
                ", user=" + user +
                '}';
    }
}
