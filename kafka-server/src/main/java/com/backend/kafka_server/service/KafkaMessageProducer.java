package com.backend.kafka_server.service;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
public class KafkaMessageProducer {

    private final KafkaTemplate<String, String> kafkaTemplate;
    private static final long startTime = System.currentTimeMillis();

    public KafkaMessageProducer(KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    @Scheduled(fixedRate = 10000) // Send message every 10 seconds
    public void sendMessage() {
        long secondsElapsed = (System.currentTimeMillis() - startTime) / 1000;
        String message = "It's been " + secondsElapsed + " seconds since Kafka instance is running!";

        kafkaTemplate.send("message-topic", message);
        System.out.println("Sent: " + message);
    }
}
