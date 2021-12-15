package barista;

import barista.Semaphor.BaristaSemaphor;

public class BaristaApp{
    public static void main(String[] args){
        BaristaSemaphor barSem = new BaristaSemaphor();
        new Barista(barSem, "Mirel");
        new Barista(barSem,"Ilinca");
    }
}
