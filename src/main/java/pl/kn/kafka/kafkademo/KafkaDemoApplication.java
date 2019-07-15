package pl.kn.kafka.kafkademo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import pl.kn.kafka.kafkademo.app.BatchConsumerRunner;
import pl.kn.kafka.kafkademo.app.KafkaExternalProducer;
import pl.kn.kafka.kafkademo.app.Producer;
import pl.kn.kafka.kafkademo.model.Product;
import pl.kn.kafka.kafkademo.model.TransactionData;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@SpringBootApplication
public class KafkaDemoApplication {

    public static void main(String[] args) throws Exception {

        ConfigurableApplicationContext context = SpringApplication.run(KafkaDemoApplication.class, args);

        // Getting beans
        Producer<TransactionData> producer = context.getBean(KafkaExternalProducer.class);
        BatchConsumerRunner batchConsumerRunner = context.getBean(BatchConsumerRunner.class);

        //Create test msgs
        List<TransactionData> testData = createTestData();

        // Run producer
        producer.produce(testData);

        // Run consumer
        batchConsumerRunner.startTransformingConsumer();
    }

    private static final List<TransactionData> createTestData() {
        //Test msgs
        Product p1 = Product.builder().name("p1").amount(BigDecimal.valueOf(100)).build();
        Product p2 = Product.builder().name("p2").amount(BigDecimal.valueOf(3)).build();
        List<Product> products1 = Arrays.asList(p1, p1, p2);
        List<Product> products2 = Arrays.asList(p2, p1, p2);

        TransactionData data1 = TransactionData.builder()
                .id(1L)
                .date(LocalDate.now())
                .products(products1)
                .build();

        TransactionData data2 = TransactionData.builder()
                .id(2L)
                .date(LocalDate.now())
                .products(products2)
                .build();

        List<TransactionData> transactionDataList = new ArrayList<>();
        transactionDataList.add(data1);
        transactionDataList.add(data2);
        return  transactionDataList;
    }

}