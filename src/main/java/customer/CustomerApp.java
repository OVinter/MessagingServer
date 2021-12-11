package customer;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.log4j.BasicConfigurator;

import java.util.ArrayList;
import java.util.Properties;
import java.util.Scanner;

import static appsUtilities.Utilities.getKafkaProps;

public class CustomerApp {


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
