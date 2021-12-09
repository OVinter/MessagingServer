package barista.Semaphor;

import java.util.Random;
import java.util.Vector;
import java.util.concurrent.Semaphore;

public class BaristaSemaphor {
    private static int n = 10;
    private static volatile Vector<Integer> bar;

    static Semaphore barSem = new Semaphore(1);


    public BaristaSemaphor()
    {
        bar = new Vector<>(n);
        for (int i = 0; i < n; i++)
        {
            bar.add(0);
        }

    }


    public static void addingCoffeesThread() //constant adauga cafele daca nu sunt suficiente
    {
        for(int i=0; i<getMaxNrOfCoffees() ; i++) //while(true)
        {
            try {
                Thread.sleep(generateNumberForSleep()); //aici sleep random
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            addCoffe();
            printBar();
        }
    }

    private static long generateNumberForSleep() {
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
        barSem.acquireUninterruptibly();
        for (int i = 0; i < n; i++)
        {
            System.out.print(bar.get(i));
        }
        System.out.print(" ->" + Thread.currentThread().getName() + "\n");
        barSem.release();
    }

    static void addCoffe()
    {
        barSem.acquireUninterruptibly();
        int index = getNextFreeCoffePlace();
        if(index != -1)
        {
            bar.set(index, 1);
        }
        barSem.release();
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

    public static void givingCoffeesThread() {
    }
}
