package apps;

import appsUtilities.CoffeeShop;
import com.example.pcbe.DbContext;
import com.example.pcbe.Producer;
import com.google.gson.Gson;
import entities.Coffee;
import entities.messages.PrivateMessage;
import entities.messages.PrivateMessage;
import entities.messages.PrivateMessageType;
import entities.users.User;
import entities.users.UserType;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.protocol.types.Field;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLOutput;
import java.sql.Statement;
import java.time.DateTimeException;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Properties;
import java.util.concurrent.atomic.AtomicInteger;

import static appsUtilities.Utilities.getKafkaProps;
import static com.fasterxml.jackson.databind.type.LogicalType.DateTime;

public final class CoffeeShopApp {

    // one instance of CoffeeShop
    private CoffeeShop coffeeShop;
    private static volatile CoffeeShopApp instance;
    private static boolean stillRunning = true;
    private static AtomicInteger messageCounter;

    // if a user who is a COFFEE_ADMIN and logs in, then we create new instance of CoffeeProviderApp
//    private CoffeeShopApp(String shopName) {
//        initialize(shopName);
//    }

    public CoffeeShopApp() {}

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

    // singleton instance (we don't need multiple instances of coffeeShopApp)
   /* public static CoffeeShopApp getInstance(String shopName) {

        CoffeeShopApp resultCoffeeShop = instance;

        if (resultCoffeeShop != null) {
            return resultCoffeeShop;
        }

        synchronized (CoffeeShop.class) {
            if(instance == null) {
                instance = new CoffeeShopApp(shopName);
            }
            return instance;
        }
    }

    // get the singleton instance of the CoffeeShop
    public void initialize(String shopName) {
        // TODO : remove lines 43-50 and line 52

        this.coffeeShop = CoffeeShop.getInstance(shopName);
        coffeeShop.setNrOfCoffees(10);
    }


    public CoffeeShop getCoffeeShop() {
        return coffeeShop;
    }
*/
    public static User getUser(String username)
    {
        DbContext dbContext = DbContext.getInstance();
        String sql = "SELECT id, name, type " +
                "FROM USERS";
        try{

            Statement statement = dbContext.getDbConnection();
            ResultSet resultSet = statement.executeQuery(sql);
            System.out.println(resultSet.toString());

            while (resultSet.next())
            {
                String dbName = resultSet.getString("name");
                if(username.equals(dbName))
                {
                    UserType userType = Enum.valueOf(UserType.class, resultSet.getString("type"));
                    int id = resultSet.getInt("id");
                    return new User(dbName,userType);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return new User();
        //TODO return null instead of new user
    }

    public static void main(String[] args) {

        messageCounter = new AtomicInteger(2);
        sendMessage("10",
                "CoffeeProvider",
                PrivateMessageType.RUN_OUT_OF_COFFEE,
                getKafkaProps(false)
        );

        receiveMessage();

        System.out.println("CoffeeShop App");
    }
}
