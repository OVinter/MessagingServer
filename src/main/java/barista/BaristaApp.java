package barista;

import barista.Barista;
import barista.Semaphor.BaristaSemaphor;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.log4j.BasicConfigurator;

import java.time.Duration;
import java.util.Arrays;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicInteger;

import static appsUtilities.Utilities.getKafkaProps;

public class BaristaApp{
    public static void main(String[] args){
        BaristaSemaphor barSem = new BaristaSemaphor();
        new Barista(barSem, "Mirel");
        new Barista(barSem,"Ilinca");
    }
}
