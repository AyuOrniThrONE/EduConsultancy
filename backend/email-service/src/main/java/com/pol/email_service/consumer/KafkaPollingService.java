package com.pol.email_service.consumer;

//import org.apache.kafka.clients.consumer.ConsumerRecord;
//import org.apache.kafka.clients.consumer.ConsumerRecords;
//import org.apache.kafka.clients.consumer.KafkaConsumer;
//import org.springframework.kafka.core.ConsumerFactory;
//import org.springframework.scheduling.annotation.Scheduled;
//import org.springframework.stereotype.Service;
//
//import java.time.Duration;
//import java.util.Collections;
//
//@Service
public class KafkaPollingService {

//    private final ConsumerFactory<String, Object> consumerFactory;
//    private final KafkaConsumer<String, Object> consumer;
//
//    public KafkaPollingService(ConsumerFactory<String, Object> consumerFactory) {
//        this.consumerFactory = consumerFactory;
//        this.consumer = (KafkaConsumer<String, Object>) consumerFactory.createConsumer();
//        this.consumer.subscribe(Collections.singletonList("user-registered-topic")); // Replace with your topic
//    }
//
//    @Scheduled(fixedRate = 600_000) // Poll every 10 minutes
//    public void pollKafka() {
//        ConsumerRecords<String, Object> records = consumer.poll(Duration.ofMillis(10000));
//        for (ConsumerRecord<String, Object> record : records) {
//            System.out.println("Key: " + record.key() + ", Value:" + record.value());
//            System.out.println("Partition:" + record.partition() + ", Offset:" + record.offset());
//        }
//    }
}
