public class KafkaApplication {

    public static void main(String[] args) {
        System.out.println("Inicializando Kafka");

        Kafka kafka = new Kafka();

        //kafka.send();

        kafka.consume();
    }
}