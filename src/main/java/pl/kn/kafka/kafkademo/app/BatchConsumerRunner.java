package pl.kn.kafka.kafkademo.app;

import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.stereotype.Component;
import pl.kn.kafka.kafkademo.model.TransactionData;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Component
public class BatchConsumerRunner {

    @Autowired
    @Qualifier(value = "kafkaConsumer")
    Consumer<TransactionData> kafkaConsumer;

    @Autowired
    @Qualifier(value = "consumerFactory")
    ConsumerFactory<String, TransactionData> consumerFactory;

    public void startTransformingConsumer() {

        //Initialize consumer
        org.apache.kafka.clients.consumer.Consumer<String, TransactionData> consumer = consumerFactory.createConsumer();
        consumer.subscribe(Collections.singleton("external-topic"));

        while (true) {
            // Getting a batch of msgs from consumer
            ConsumerRecords<String, TransactionData> consumerRecords = consumer.poll(Duration.ofSeconds(3));
            List<TransactionData> transactions = new ArrayList<>();
            consumerRecords.iterator().forEachRemaining(record -> transactions.add(record.value()));

            // Passing batch of msgs to 'transforminng-producing consumer'
            kafkaConsumer.consume(transactions);

        }
    }

}