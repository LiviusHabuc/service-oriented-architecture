package com.backend.rest_server.service;

import com.backend.rest_server.model.DisplayCard;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hivemq.client.mqtt.MqttClient;
import com.hivemq.client.mqtt.MqttGlobalPublishFilter;
import com.hivemq.client.mqtt.datatypes.MqttQos;
import com.hivemq.client.mqtt.mqtt5.Mqtt5AsyncClient;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class MqttConsumerService {

    private final DisplayCardService displayCardService;

    private final SimpMessagingTemplate messagingTemplate;

    @Autowired
    public MqttConsumerService(DisplayCardService displayCardService, SimpMessagingTemplate messagingTemplate) {
        this.displayCardService = displayCardService;
        this.messagingTemplate = messagingTemplate;
    }

    @PostConstruct
    public void subscribeToTopic() {
        Mqtt5AsyncClient client = MqttClient.builder()
                .useMqttVersion5()
                .identifier("consumer-id")
                .serverHost("broker.hivemq.com") // "broker.hivemq.com"
                .serverPort(1883) // Default MQTT port
                .automaticReconnectWithDefaultConfig() // Enable automatic reconnect
                .buildAsync();

        client.connectWith()
                .cleanStart(true)
                .sessionExpiryInterval(500)
                .send()
                .whenComplete((connAck, throwable) -> {
                    if (throwable != null) {
                        System.out.println("Connection failed: " + throwable.getMessage());
                    } else {
                        System.out.println("Connected successfully");
                        // Subscribe to a topic
                        client.subscribeWith()
                                .topicFilter("SYSTEMCOMMUNICATION") // Change to your topic
                                .qos(MqttQos.AT_LEAST_ONCE)
                                .send()
                                .whenComplete((subAck, subThrowable) -> {
                                    if (subThrowable != null) {
                                        System.out.println("Subscribe failed: " + subThrowable.getMessage());
                                    } else {
                                        System.out.println("Subscribed successfully");
                                    }
                                });

                        // Set up callback for incoming messages
                        client.toAsync().publishes(MqttGlobalPublishFilter.ALL, publish -> {
                            try {
                                String message = new String(publish.getPayloadAsBytes());
                                ObjectMapper objectMapper = new ObjectMapper();
                                JsonNode jsonNode = objectMapper.readTree(message);

                                String extractedMessage = jsonNode.get("message").asText();
                                String title = LocalDateTime.now().toString();

                                DisplayCard displayCard = new DisplayCard();
                                displayCard.setTitle(title);
                                displayCard.setMessage(extractedMessage);
                                displayCardService.saveDisplayCard(displayCard);

                                List<DisplayCard> allDisplayCards = this.displayCardService.getAll();
                                messagingTemplate.convertAndSend("/topic/display-cards", allDisplayCards);
                            } catch (Exception e) {
                                System.out.println("Failed to process message: " + e.getMessage());
                            }
                        });
                    }
                });
    }
}
