package entities;

import lombok.*;

// TODO: a table
@Data
@ToString
@EqualsAndHashCode
@NoArgsConstructor
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
}
