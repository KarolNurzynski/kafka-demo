# kafka-demo

AIM
This is a demo project, implementing Consumer and Producer with a given interface - consuming/producing Iterable<T>.

TECHNOLGIES
- Apache Kafka (KafkaTemplate for producing of messages; Standard Java API for Kafka - for message consuming)
- Java 8, SpringBoot + Spring Kafka integration libraries
- Jackson for serialization / deserialization

REASON FOR TECH CHOICES
Interfaces Consumer and Producer are given interface to which I had to fit my app. Due to that I couldnt use Spring @KafkaListener - as this applies for event-driven architectures. Instead I used Kafka Java API - with Consumer.poll, getting a batch of events, then consumed by the interface Consumer.

HOW TO RUN THE APP
0) Download, unzip Kafka
1) Run zookeeper (Kafka download contains it) - bin/zookeeper-server-start.sh config/zookeeper.properties
2) Run Kafka - bin/kafka-server-start.sh config/server.properties
3) Run the SpringBoot app
