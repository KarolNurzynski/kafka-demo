package pl.kn.kafka.kafkademo.app;

public interface Consumer<T> {

    void consume(Iterable<T> messages);

}