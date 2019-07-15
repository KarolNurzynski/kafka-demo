package pl.kn.kafka.kafkademo.app;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import pl.kn.kafka.kafkademo.model.Product;
import pl.kn.kafka.kafkademo.model.TransactionData;

import java.math.BigDecimal;
import java.util.*;

@Component
public class KafkaConsumer implements Consumer<TransactionData> {

    @Autowired
    @Qualifier(value = "kafkaProducer")
    Producer<TransactionData> producer;

    @Override
    public void consume(Iterable<TransactionData> messages) {
        System.out.println("Consuming: " + messages + " from external topic");
        Iterator<TransactionData> iterator = messages.iterator();
        while (iterator.hasNext()) {
            TransactionData data = iterator.next();
            Optional<BigDecimal> totalAmount = data.getProducts().stream()
                    .map(Product::getAmount)
                    .reduce(BigDecimal::add);
            data.setTotalAmount(totalAmount.orElse(BigDecimal.ZERO));
        }
        System.out.println("Producing enriched messgaes: " + messages);
        producer.produce(messages);
    }
}