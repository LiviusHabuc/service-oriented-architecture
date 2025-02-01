package com.backend.message_pub_server.controller;

import com.backend.message_pub_server.service.MqttPublisherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/msg")
public class MessageQueueController {

    @Autowired
    private MqttPublisherService mqttPublisherService;

    @GetMapping("/send")
    public ResponseEntity<String> sendMqtt() {
        mqttPublisherService.publishMessage("{\"message\": \"Hive MQ is working!\"}");
        String message = "Successfully received!";
        return ResponseEntity.ok(message);
    }
}
