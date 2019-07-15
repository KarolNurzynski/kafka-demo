package pl.kn.kafka.kafkademo.app;

import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import pl.kn.kafka.kafkademo.model.Product;
import pl.kn.kafka.kafkademo.model.TransactionData;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import static org.junit.Assert.*;

@ExtendWith(MockitoExtension.class)
public class KafkaConsumerTest {

    @InjectMocks
    private KafkaConsumer kafkaConsumer;

    @Mock
    Producer<TransactionData> producer;

    @Captor
    ArgumentCaptor<Iterable<TransactionData>> captor;

    @Test
    public void shouldTransformDataCorrectly() {
        //given
        MockitoAnnotations.initMocks(this);
        Iterable<TransactionData> transactionData = generateTestData();

        //when
        kafkaConsumer.consume(transactionData);

        //then
        Mockito.verify(producer).produce(captor.capture());
        Iterable<TransactionData> iterable = captor.getValue();

        assertEquals(iterable.iterator().next().getTotalAmount(), BigDecimal.valueOf(203));

    }

    private Iterable<TransactionData> generateTestData() {
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