package apps;

import com.example.pcbe.DbContext;
import com.google.gson.*;
import entities.messages.PrivateMessage;
import entities.messages.PrivateMessageType;
import entities.users.User;
import entities.users.UserType;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.Duration;
import java.util.Arrays;
import java.util.Properties;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicInteger;

import static appsUtilities.Utilities.getKafkaProps;

public class CoffeeProviderApp{

    private static boolean stillRunning = true;
    private static int cnt = 0;
    private static AtomicInteger messageCounter;
    public static Properties getKafkaProps(boolean isConsumer) {
        Properties props = new Properties();
        if(isConsumer) {
            props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
            props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringDeserializer");
            props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringDeserializer");
            props.put(ConsumerConfig.GROUP_ID_CONFIG, "test-consumer-group");
        } else {
            props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
            props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringSerializer");
            props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringSerializer");
        }
        return  props;
    }
    public static void receiveMessage() {
        // TODO
        System.out.println(getKafkaProps(true));
        KafkaConsumer consumer = new KafkaConsumer(getKafkaProps(true));
        consumer.subscribe(Arrays.asList("providerResponseTest"));
        new Thread(() -> {
            while (stillRunning) {
                ConsumerRecords<String, String> recs = consumer.poll(Duration.ofMillis(10));
                if (!(recs.count() == 0)) {
                    for (ConsumerRecord<String, String> rec : recs) {
                        PrivateMessage privateMessage = new Gson().fromJson(rec.value(), PrivateMessage.class);
                        System.out.println("---" + privateMessage);
                        cnt++;
                        messageCounter.incrementAndGet();
                        System.out.println("received" + messageCounter);
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

    public static void sendMessage(String payload,
                                   String destination,
                                   PrivateMessageType privateMessageType,
                                   Properties props) {
        KafkaProducer<String, String> producer = new KafkaProducer<>(props);
        new Thread(() -> {
            while(stillRunning) {
                if(messageCounter.get() > 0) {
                    ProducerRecord<String, String> data =
                            new ProducerRecord<>(
                                    "providerResponseTest",
                                    0,
                                    "1",
                                    new Gson().toJson(new PrivateMessage(
                                            payload,
                                            System.currentTimeMillis(),
                                            destination,
                                            privateMessageType)
                                    )
                            );
                    producer.send(data);
                    messageCounter.getAndDecrement();
                    System.out.println("send " + messageCounter);
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
            producer.close();
        }).start();
    }

    public static User getUser(String username){
        DbContext dbContext = DbContext.getInstance();
        String sql = "SELECT id, name, type FROM USERS";
        try{
            Statement stmt = dbContext.getDbConnection();
            ResultSet rs = stmt.executeQuery(sql);
            System.out.println(rs.toString());
            while (rs.next()) {
                String nameFromDb = rs.getString("name");
                if(username.equals(nameFromDb)) {
                    UserType type = Enum.valueOf(UserType.class, rs.getString("type"));
                    int id = rs.getInt("id");
                    return new User(nameFromDb, type, id);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return new User();
    }

    public static void main(String[] args) {
        User user;
//        if(args.length > 0) {
//            user = getUser(args[0]);
//        }
        messageCounter = new AtomicInteger(2);
        System.out.println(getUser("Floricel"));

        sendMessage("10",
                "CoffeeShop",
                PrivateMessageType.PROVIDER_RESPONSE,
                getKafkaProps(false)
        );
        receiveMessage();
        Scanner scanner = new Scanner(System.in);
        System.out.println("CoffeeProvider App");
        while(true) {
            String input = scanner.next();
            if("e".equals(input)) {
                stillRunning = false;
                break;
            }
        }
        System.out.println(cnt);
    }
}
