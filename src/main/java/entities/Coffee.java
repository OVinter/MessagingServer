package entities;

import java.util.Objects;
import java.util.UUID;

// TODO: a table
public class Coffee {

    private String name;
    private int id;
    private static int count = 0;
    private float price;

    public Coffee(String name, float price) {
        this.name = name;
        this.id = ++count;
        this.price = price;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public float getPrice() { return price;}

    public void setName(String name) {
        this.name = name;
    }

    public void setPrice(float price) {this.price = price;}

    @Override
    public String toString() {
        return "Coffee{" +
                "name='" + name + '\'' +
                ", id=" + id +
                ", price=" + price +
                '}';
    }
}
