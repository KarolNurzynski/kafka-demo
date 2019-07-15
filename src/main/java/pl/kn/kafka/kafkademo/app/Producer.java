package pl.kn.kafka.kafkademo.app;

public interface Producer<T> {

    void produce(Iterable<T> messages);

}