package entities;

import java.util.Objects;
import java.util.UUID;

// TODO: a table
public class Coffee {

    private String name;
    private UUID id;

    public Coffee(String name) {
        this.name = name;
        this.id = UUID.randomUUID();
    }

    public UUID getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Coffee{" +
                "name='" + name + '\'' +
                ", id=" + id +
                '}';
    }
}
