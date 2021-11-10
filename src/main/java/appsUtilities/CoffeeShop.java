package appsUtilities;

import entities.users.User;
import entities.users.UserType;
import lombok.*;

import java.util.concurrent.atomic.AtomicInteger;

// TODO: a table
@Data
@NoArgsConstructor
@ToString
@EqualsAndHashCode
public final class CoffeeShop {

    private static volatile CoffeeShop instance;
    private int id;
    private static int count = 0;
    private String name;
    private AtomicInteger nrOfCoffees;
//    one user - Coffee Admin
    private User user;
    private AtomicInteger money;

    private CoffeeShop(String name) {
        this.name = name;
        this.id = ++count;
        this.nrOfCoffees = new AtomicInteger(10);
        this.user = null;
        this.money = new AtomicInteger(100);
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

    public void setUser(String name) {
        this.user.setName(name);
        this.user.setUserType(UserType.COFFEE_ADMIN);
    }

    public void setNrOfCoffees(int coffees) {
        this.nrOfCoffees.addAndGet(coffees);
    }
}
