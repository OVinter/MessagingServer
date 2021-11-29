package apps;

import appsUtilities.Utilities;
import com.google.gson.Gson;
import entities.messages.PrivateMessage;
import entities.messages.PrivateMessageType;
import entities.users.User;
import entities.users.UserType;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;

import java.util.ArrayList;
import java.util.Properties;
import java.util.Scanner;

import static appsUtilities.Utilities.getKafkaProps;

public class CustomerApp {

    private ArrayList<User> user;

    public CustomerApp(String name) {
        this.user = new ArrayList<>(3);
    }

    public void receiveMessage() {
        // TODO
    }

    public void sendMessage(PrivateMessage message) {
        // TODO
    }

    public void addUser(String name) {
        this.user.add(new User(name, UserType.CUSTOMER));
    }

    public ArrayList<User> getUser() {
        return user;
    }

    public static boolean customerMenu() {
        System.out.println("Choose option:");
        System.out.println("1. Get coffee");
        System.out.println("2. Exit");
        try {
            Scanner sc = new Scanner(System.in);
            int option = Integer.parseInt(sc.nextLine());
            if (option == 1) {
                System.out.println("Enter the nr of coffees you want to buy:");
                int nrOfCoffees = Integer.parseInt(sc.nextLine());
                System.out.println(nrOfCoffees);
                sendToBaristaNrOfCoffees(nrOfCoffees);
            } else if (option == 2) {
                return false;
            } else {
                System.out.println("Choose a valid option!");
            }
        } catch (NumberFormatException e) {
            System.out.println("Enter an integer, please!");
        }
        return true;
    }

    private static void sendToBaristaNrOfCoffees(int nrOfCoffees) {
        KafkaProducer<String, String> producer = new KafkaProducer<>(getKafkaProps(false));
        new Thread(() -> {
            ProducerRecord<String, String> data = new ProducerRecord<>(
                    "coffeesTest",
                    Integer.toString(nrOfCoffees)
            );
            producer.send(data);
//            messageCounter.getAndDecrement();
//            System.out.println("send " + messageCounter);
            System.out.println(data);
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            producer.close();
        }).start();
    }

    public static void main(String[] args) {
        System.out.println("\nHello\n");
        while(customerMenu());
    }
}
