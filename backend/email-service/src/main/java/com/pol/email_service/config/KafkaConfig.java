package com.pol.email_service.config;

//import io.confluent.kafka.serializers.KafkaAvroDeserializer;
//import io.confluent.kafka.serializers.KafkaAvroDeserializerConfig;
//import org.apache.kafka.clients.consumer.ConsumerConfig;
//import org.apache.kafka.common.serialization.StringDeserializer;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.kafka.annotation.EnableKafka;
//import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
//import org.springframework.kafka.core.ConsumerFactory;
//import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
//
//import java.util.HashMap;
//import java.util.Map;
//
//@EnableKafka
//@Configuration
public class KafkaConfig {

//    private static final String BOOTSTRAP_SERVERS = "localhost:9092";
//    private static final String SCHEMA_REGISTRY_URL = "http://localhost:8086";
//    private static final String GROUP_ID = "notification-service";
//
//    // Consumer Configuration for Avro
//    @Bean
//    public ConsumerFactory<String, Object> consumerFactory() {
//        Map<String, Object> props = new HashMap<>();
//        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, BOOTSTRAP_SERVERS);
//        props.put(ConsumerConfig.GROUP_ID_CONFIG, GROUP_ID);
//        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
//        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, KafkaAvroDeserializer.class);
//
//        // Schema Registry Configuration
//        props.put(KafkaAvroDeserializerConfig.SCHEMA_REGISTRY_URL_CONFIG, SCHEMA_REGISTRY_URL);
//        props.put(KafkaAvroDeserializerConfig.SPECIFIC_AVRO_READER_CONFIG, true); // Use specific Avro classes
//
//        // Additional Configurations
//        props.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, true); // Disable auto-commit
//        props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest"); // Start from the beginning if no offset exists
//        props.put(ConsumerConfig.MAX_POLL_INTERVAL_MS_CONFIG, 600000); // 5 minutes
//        props.put(ConsumerConfig.SESSION_TIMEOUT_MS_CONFIG, 30000); // 30 seconds (default range: 6-30000ms)
//        props.put(ConsumerConfig.HEARTBEAT_INTERVAL_MS_CONFIG, 3000); // Heartbeat every 10 seconds
//        props.put(ConsumerConfig.CONNECTIONS_MAX_IDLE_MS_CONFIG, 300000); // 5 minutes
//
//        return new DefaultKafkaConsumerFactory<>(props);
//    }
//
//    @Bean
//    public ConcurrentKafkaListenerContainerFactory<String, Object> kafkaListenerContainerFactory() {
//        ConcurrentKafkaListenerContainerFactory<String, Object> factory =
//                new ConcurrentKafkaListenerContainerFactory<>();
//        factory.setConsumerFactory(consumerFactory());
//        factory.setConcurrency(3); // Adjust concurrency as needed
//        return factory;
//    }
}
