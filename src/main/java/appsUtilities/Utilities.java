package appsUtilities;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.producer.ProducerConfig;

import java.util.Properties;
import java.util.UUID;

public final class Utilities {

    public static Properties getKafkaProps(boolean isConsumer, boolean needInSingleGroup) {
        return setKafkaProps(isConsumer, needInSingleGroup);
    }

    public static Properties getKafkaProps(boolean isConsumer) {
        return setKafkaProps(isConsumer, false);
    }

    private static Properties setKafkaProps(boolean isConsumer, boolean needInSingleGroup) {
        Properties props = new Properties();
        String groupId = "test-consumer-group";
        if(needInSingleGroup) {
            groupId = UUID.randomUUID().toString();
        }
        if(isConsumer) {
            props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
            props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringDeserializer");
            props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringDeserializer");
            props.put(ConsumerConfig.GROUP_ID_CONFIG, groupId);
        } else {
            props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
            props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringSerializer");
            props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringSerializer");
        }
        return props;
    }

    public static String setCompleteOrderMessage(String nrOfCoffees) {
        return "Order completed: " + nrOfCoffees;
    }

}
