package com.backend.kafka_server.service;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Service
public class KafkaMessageConsumer {

    private final SimpMessagingTemplate messagingTemplate;

    public KafkaMessageConsumer(SimpMessagingTemplate messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
    }

    @KafkaListener(topics = "message-topic", groupId = "message-group")
    public void listen(String message) {
        System.out.println("Received from Kafka: " + message);
        messagingTemplate.convertAndSend("/topic/messages", message);
    }
}
