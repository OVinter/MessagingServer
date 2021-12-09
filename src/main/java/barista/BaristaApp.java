package barista;

import barista.Barista;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;

import java.time.Duration;
import java.util.Arrays;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicInteger;

import static appsUtilities.Utilities.getKafkaProps;

public class BaristaApp{

    private static boolean stillRunning = true;
    private static int cnt = 0;
    private static AtomicInteger messageCounter;

//    public static void receiveMessage() {
//        // TODO
//        System.out.println(getKafkaProps(true));
//        KafkaConsumer consumer = new KafkaConsumer(getKafkaProps(true));
//        consumer.subscribe(Arrays.asList("providerResponseTest"));
//        new Thread(() -> {
//            while (stillRunning) {
//                ConsumerRecords<String, String> recs = consumer.poll(Duration.ofMillis(10));
//                if (!(recs.count() == 0)) {
//                    for (ConsumerRecord<String, String> rec : recs) {
//                        PrivateMessage privateMessage = new Gson().fromJson(rec.value(), PrivateMessage.class);
//                        System.out.println("---" + privateMessage);
//                        cnt++;
//                        messageCounter.incrementAndGet();
//                        System.out.println("received" + messageCounter);
//                        try {
//                            Thread.sleep(1000);
//                        } catch (InterruptedException e) {
//                            e.printStackTrace();
//                        }
//                    }
//                }
//            }
//        }).start();
//    }

//    public static void sendMessage(String payload,
//                                   String destination,
//                                   PrivateMessageType privateMessageType,
//                                   Properties props) {
//        KafkaProducer<String, String> producer = new KafkaProducer<>(props);
//        new Thread(() -> {
//            while(stillRunning) {
//                if(messageCounter.get() > 0) {
//                    ProducerRecord<String, String> data =
//                            new ProducerRecord<>(
//                                    "providerResponseTest",
//                                    0,
//                                    "1",
//                                    new Gson().toJson(new PrivateMessage(
//                                            payload,
//                                            System.currentTimeMillis(),
//                                            destination,
//                                            privateMessageType)
//                                    )
//                            );
//                    producer.send(data);
//                    messageCounter.getAndDecrement();
//                    System.out.println("send " + messageCounter);
//                    try {
//                        Thread.sleep(1000);
//                    } catch (InterruptedException e) {
//                        e.printStackTrace();
//                    }
//                }
//            }
//            producer.close();
//        }).start();
//    }

    private static void receiveNrOfCoffees() {
        KafkaConsumer kafkaConsumer = new KafkaConsumer(getKafkaProps(true));
        kafkaConsumer.subscribe(Arrays.asList("coffeesTest"));
        new Thread(() -> {
            while(stillRunning) {
                ConsumerRecords<String, String> recs = kafkaConsumer.poll(Duration.ofMillis(10));
                if (!(recs.count() == 0)) {
                    for (ConsumerRecord<String, String> rec : recs) {
                        int nrOfCoffees = Integer.valueOf(rec.value());
                        System.out.println("receive: " + nrOfCoffees);
                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }).start();
    }

    public static void main(String[] args) {

//        messageCounter = new AtomicInteger(2);

//        receiveMessage();
        receiveNrOfCoffees();
        Barista barista1 = new Barista("barista1");
        Barista barista2 = new Barista("barista2");

        Scanner scanner = new Scanner(System.in);
        System.out.println("CoffeeProvider App");
//        barista1.makeCoffee();
        while(true) {
            String input = scanner.next();

            if("e".equals(input)) {
                stillRunning = false;
                Barista.stopThreads();
                break;
            }
        }
        System.out.println(cnt);
    }
}
