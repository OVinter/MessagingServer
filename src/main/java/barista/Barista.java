package barista;

public class Barista{

    public Barista(barista.Semaphor.BaristaSemaphor semBarista, String name) {
        this.name = name;
        this.semBarista = semBarista;
        new Thread(barista.Semaphor.BaristaSemaphor::addingCoffeesThread, name+"-MakingCoffee").start();
        new Thread(barista.Semaphor.BaristaSemaphor::givingCoffeesThread, name+"-GivingCoffee").start();
    }

    barista.Semaphor.BaristaSemaphor semBarista;
    String name;
}
