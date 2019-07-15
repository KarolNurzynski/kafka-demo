package pl.kn.kafka.kafkademo.app;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import pl.kn.kafka.kafkademo.model.TransactionData;

@Component
public class KafkaProducer implements Producer<TransactionData> {


    @Autowired
    private KafkaTemplate<String, TransactionData> kafkaTemplate;

    @Value(value = "${topic.name}")
    private String topicName;

    @Override
    public void produce(Iterable<TransactionData> messages) {
        messages.iterator().forEachRemaining(m -> kafkaTemplate.send(topicName, m));
        System.out.println("Sending: " + messages + " to topic " + topicName);
    }
}