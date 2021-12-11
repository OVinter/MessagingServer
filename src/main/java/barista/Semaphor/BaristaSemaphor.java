package barista.Semaphor;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;

import java.time.Duration;
import java.util.Arrays;
import java.util.Random;
import java.util.Vector;
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
        KafkaConsumer kafkaConsumer = new KafkaConsumer(getKafkaProps(true));
        kafkaConsumer.subscribe(Arrays.asList("coffeesTest"));
        while(true)
        {
            ConsumerRecords<String, String> recs = kafkaConsumer.poll(Duration.ofMillis(10));
            if (!(recs.count() == 0))
            {   orderSem.acquireUninterruptibly();
                for (ConsumerRecord<String, String> rec : recs)
                {
                    int nrOfCoffees = Integer.valueOf(rec.value());
                    orderReady = false;
                    while(orderReady == false)
                    {
                        while(nrOfCoffees > getMaxNrOfCoffees())
                        {
                            serveCoffees(getMaxNrOfCoffees());
                            if(orderReady == true)
                            {
                                nrOfCoffees = nrOfCoffees - getMaxNrOfCoffees();
                            }
                        }
                        serveCoffees(nrOfCoffees);
                    }
                    orderSem.release();
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    break;
                }
            }
        }
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
            addCoffe();
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

    static void addCoffe()
    {
        barSem.acquireUninterruptibly();
        int index = getNextFreeCoffePlace();
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
                index = getNextAvailableCoffe();
                if (index != -1) {
                    bar.set(index, 0);
                }
            }
            orderReady = true;
            printBar();
            barSem.release();
        }
        else
        {
            orderReady = false;
            barSem.release();
        }
    }

    static int getNextFreeCoffePlace()
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

    private static int getNextAvailableCoffe()
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
