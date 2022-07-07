import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;

import java.time.Duration;
import java.time.Instant;
import java.util.Collections;
import java.util.Properties;

public class Kafka {

    private Properties prop;

    private final String TOPIC_NAME = "testeKafka";

    private final String HOST = "localhost:29092";

    public void send() {
        System.out.println("Inicio send()");
        KafkaProducer<String, String> producer;

        ProducerRecord<String, String> record;

        prop = new Properties();
        prop.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, HOST);
        prop.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        prop.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());

        producer = new KafkaProducer<> (prop);

        record = new ProducerRecord<>(TOPIC_NAME, "Datahora: " + Instant.now());

        producer.send(record);

        producer.close();

        System.out.println("Fim send()");
    }

    public void consume() {
        System.out.println("Inicio consume()");

        prop = new Properties();
        prop.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, HOST);
        prop.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        prop.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        prop.put(ConsumerConfig.GROUP_ID_CONFIG, "group1");
        prop.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");

        try(KafkaConsumer<String, String> consumer = new KafkaConsumer<>(prop)){

            consumer.subscribe(Collections.singletonList(TOPIC_NAME));

            while(true) {

                ConsumerRecords<String, String> records = consumer.poll(Duration.ofSeconds(1));

                for (ConsumerRecord<String, String> record : records) {
                    System.out.println(record.value());
                }
            }
        }

    }




}
