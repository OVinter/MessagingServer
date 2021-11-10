package apps;

import appsUtilities.CoffeeShop;
import com.example.pcbe.DbContext;
import com.example.pcbe.Producer;
import entities.Coffee;
import entities.messages.Message;
import entities.messages.PrivateMessage;
import entities.messages.PrivateMessageType;
import entities.users.User;
import entities.users.UserType;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.protocol.types.Field;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLOutput;
import java.sql.Statement;
import java.time.DateTimeException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Properties;

import static com.fasterxml.jackson.databind.type.LogicalType.DateTime;

public final class CoffeeShopApp {

    // one instance of CoffeeShop
    private CoffeeShop coffeeShop;
    private static volatile CoffeeShopApp instance;

    // if a user who is a COFFEE_ADMIN and logs in, then we create new instance of CoffeeProviderApp
//    private CoffeeShopApp(String shopName) {
//        initialize(shopName);
//    }

    public CoffeeShopApp() {}

    public void receiveMessage(Message message) {
        // TODO

    }

    public void sendMessage(Message message) {
        // TODO
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
        Coffee coffee1 = new Coffee("latte",10);
        Coffee coffee2 = new Coffee("espresso",7);
        Coffee coffee3 = new Coffee("latte",10);

        ArrayList<Coffee> coffees = new ArrayList<>();
        coffees.add(coffee1);
        coffees.add(coffee2);
        coffees.add(coffee3);
        this.coffeeShop = CoffeeShop.getInstance(shopName);
        coffeeShop.setCoffees(coffees);
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
                    return new User(dbName,userType,id);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return new User();
        //TODO return null instead of new user
    }

    public static void sendMessage(PrivateMessage message, Properties props) throws InterruptedException {
        KafkaProducer<String, String> coffeeShopAdmin = new KafkaProducer<>(props);
        ProducerRecord<String, String> data = new ProducerRecord<>("Coffee Shop test message",0,"1",message.toString());
        coffeeShopAdmin.send(data);
        Thread.sleep(1L);
        coffeeShopAdmin.close();
    }

    public static void main(String[] args) throws InterruptedException {

        System.out.println(getUser("Cornel"));

        Properties props = new Properties();
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringSerializer");
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringSerializer");

        sendMessage(
                new PrivateMessage("Da-mi cafea",
                                    System.currentTimeMillis(),
                                    "sss",
                                    PrivateMessageType.COFFEE_MESSAGE
                ),
                props);

        System.out.println("CoffeeShop App");
    }
}
