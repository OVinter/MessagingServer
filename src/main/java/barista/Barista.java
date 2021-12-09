package barista;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Random;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Barista {

    private String baristaName;
    private static int[] coffeesList = new int[10];
    private static boolean stillRunning = true;

    public void makeCoffee() {
        new Thread(() -> {
            while(stillRunning) {
                try {
                    Thread.sleep(generateNumberForSleep());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                addCoffeeInList();
            }
        }).start();
    }

    public void sellCoffee() {
        Thread sellCoffee = new Thread(() -> {
            while(stillRunning) {

            }
        });
    }

    public static void stopThreads() {
        stillRunning = false;
    }

    public static int[] getCoffeesList() {
        return coffeesList;
    }

    private long generateNumberForSleep() {
        Random random = new Random();
        double randomValue = 2 * random.nextDouble();
        return (long)(randomValue * 1000);
    }

    private void addCoffeeInList() {
        for (int i = 0; i < coffeesList.length; i++) {
            if(coffeesList[i] == 0) {
                System.out.println("i " + i);
                coffeesList[i] = 1;
                break;
            }
        }
        for (int i = 0; i < coffeesList.length; i++) {
            System.out.print(coffeesList[i] + " ");
        }
        System.out.println();
    }
}
