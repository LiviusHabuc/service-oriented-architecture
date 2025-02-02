package com.backend.message_pub_server.controller;

import com.backend.message_pub_server.service.MqttPublisherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/msg")
public class MessageQueueController {

    @Autowired
    private MqttPublisherService mqttPublisherService;

    @PostMapping("/send")
    public ResponseEntity<Map<String, String>> sendMqtt(@RequestBody String messageText) {

        if (messageText == null || messageText.trim().isEmpty()) {
            Map<String, String> responseEmpty = new HashMap<>();
            responseEmpty.put("message", "Message text cannot be empty!");
            return ResponseEntity.badRequest().body(responseEmpty);
        }

        mqttPublisherService.publishMessage(messageText);

        Map<String, String> responseMessage = new HashMap<>();
        responseMessage.put("message", "Successfully sent: " + messageText);
        return ResponseEntity.ok(responseMessage);
    }
}
