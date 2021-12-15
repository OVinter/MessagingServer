package barista.Semaphor;

import appsUtilities.PayloadObject;
import com.google.gson.Gson;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;

import java.time.Duration;
import java.util.*;
import java.util.concurrent.Semaphore;

import static appsUtilities.Utilities.getKafkaProps;

public class BaristaSemaphor {
    private static int n = 10;
    private static volatile Vector<Integer> bar;
    static Semaphore barSem = new Semaphore(1);
    static Semaphore orderSem = new Semaphore(1);
    private static boolean orderReady;
    public BaristaSemaphor()
    {
        bar = new Vector<>(n);
        for (int i = 0; i < n; i++)
        {
            bar.add(0);
        }
    }

    public static void givingCoffeesThread()
    {
        while(true) {
            ConsumerRecords<String, String> recs = getRecsFromKafka();
            orderReady = false;
            orderSem.acquireUninterruptibly();
            for (ConsumerRecord<String, String> rec : recs) {
                PayloadObject payloadObject = new Gson().fromJson(rec.value(), PayloadObject.class);
                int nrOfCoffees = Integer.parseInt(payloadObject.getPayload());

                if (payloadObject.isResponse() || nrOfCoffees <= 0) {
                    continue;
                }

                System.out.println("\n" + "Coffee order: " + nrOfCoffees + "\n");


                while (orderReady == false) {
                    while (nrOfCoffees > getMaxNrOfCoffees()) {
                        serveCoffees(getMaxNrOfCoffees());
                        if (orderReady == true) {
                            nrOfCoffees = nrOfCoffees - getMaxNrOfCoffees();
                        }
                    }
                    serveCoffees(nrOfCoffees);
                }

                sendCompleteOrderMessage(payloadObject);

            }
            orderSem.release();

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private static ConsumerRecords<String, String> getRecsFromKafka() {
        orderSem.acquireUninterruptibly();
        KafkaConsumer kafkaConsumer = new KafkaConsumer(getKafkaProps(true, false));
        kafkaConsumer.subscribe(Arrays.asList("coffeesTest"));

        while(true) {
            ConsumerRecords<String, String> recs = kafkaConsumer.poll(Duration.ofMillis(10));
            if (!(recs.count() == 0)) {
                kafkaConsumer.close();
                orderSem.release();

                return recs;
            }
        }
    }

    private static void sendCompleteOrderMessage(PayloadObject payloadObject) {
        payloadObject.setResponse(true);
        KafkaProducer<String, String> producer = new KafkaProducer<>(getKafkaProps(false));
        new Thread(() -> {
            ProducerRecord<String, String> data = new ProducerRecord<>(
                    "coffeesTest",
                    new Gson().toJson(payloadObject)
            );
            producer.send(data);
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            producer.close();
        }).start();
    }

    public static void addingCoffeesThread()
    {
        while(true)
        {
            try {
                Thread.sleep(generateNumberForSleep()); //aici sleep random
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            addCoffee();
        }
    }

    private static long generateNumberForSleep()
    {
        Random random = new Random();
        double randomValue = 2 * random.nextDouble();
        return (long)(randomValue * 1000);
    }

    public static int getMaxNrOfCoffees()
    {
        return n;
    }

    public static void printBar()
    {
        for (int i = 0; i < n; i++)
        {
            System.out.print(bar.get(i));
        }
        System.out.print(" ->" + Thread.currentThread().getName() + "\n");
    }

    public static void printBar(int nrCoffee)
    {
        for (int i = 0; i < n; i++)
        {
            System.out.print(bar.get(i));
        }
        System.out.print(" ->" + Thread.currentThread().getName() + nrCoffee + "\n");
    }

    static void addCoffee()
    {
        barSem.acquireUninterruptibly();
        int index = getNextFreeCoffeePlace();
        if(index != -1)
        {
            bar.set(index, 1);
            printBar();
        }
        barSem.release();
    }

    private static void serveCoffees(int nrCoffees)
    {
        barSem.acquireUninterruptibly();
        if(getAvailableCoffees() >= nrCoffees)
        {
            int index = 0;
            for (int i = 0; i < nrCoffees; i++) {
                index = getNextAvailableCoffee();
                if (index != -1) {
                    bar.set(index, 0);
                }
            }
            orderReady = true;
            printBar(nrCoffees);
        }
        else
        {
            orderReady = false;
        }

        barSem.release();
    }

    static int getNextFreeCoffeePlace()
    {
        for(int i=0; i<n; i++)
        {
            if(bar.get(i) == 0)
            {
                return i;
            }
        }
        return -1;
    }

    private static int getAvailableCoffees()
    {
        int count = 0;
        for (int i = 0; i < n; i++)
        {
            if(bar.get(i) == 1)
            {
                count ++;
            }
        }
        return count;
    }

    private static int getNextAvailableCoffee()
    {
        for(int i=0; i<n; i++)
        {
            if(bar.get(i) == 1)
            {
                return i;
            }
        }
        return -1;
    }
}
