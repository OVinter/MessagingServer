package customer;

import appsUtilities.PayloadObject;
import com.google.gson.Gson;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.log4j.BasicConfigurator;

import java.time.Duration;
import java.util.*;

import static appsUtilities.Utilities.getKafkaProps;
import static appsUtilities.Utilities.setCompleteOrderMessage;

public class CustomerApp {

    private static UUID userId;
    private static boolean stillRunning = false;

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
                if(nrOfCoffees <= 0) {
                    System.out.println("Enter a positive number!");
                    return true;
                }
                sendToBaristaNrOfCoffees(nrOfCoffees);
            } else if (option == 2) {
                stillRunning = false;
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
                    new Gson().toJson(new PayloadObject(Integer.toString(nrOfCoffees), userId, false))
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

    private static void receiveCompletedOrderMessage() {
        KafkaConsumer consumer = new KafkaConsumer(getKafkaProps(true, true));
        consumer.subscribe(Arrays.asList("coffeesTest"));
        new Thread(() -> {
            while (stillRunning) {
                ConsumerRecords<String, String> recs = consumer.poll(Duration.ofMillis(10));
                if (!(recs.count() == 0)) {
                    for (ConsumerRecord<String, String> rec : recs) {
                        PayloadObject payloadObject = new Gson().fromJson(rec.value(), PayloadObject.class);
                        if(payloadObject.getUserId().equals(userId) && payloadObject.isResponse()) {
                            System.out.println("\n" + setCompleteOrderMessage(payloadObject.getPayload()));
                        }
                        try {
                            Thread.sleep(100);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }).start();
    }

    public static void main(String[] args) {
        userId = UUID.randomUUID();
        stillRunning = true;
        System.out.println("\nHello\n");
        receiveCompletedOrderMessage();
        while(customerMenu());
    }
}
