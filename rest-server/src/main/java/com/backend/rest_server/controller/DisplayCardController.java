package com.backend.rest_server.controller;

import com.backend.rest_server.model.DisplayCard;
import com.backend.rest_server.service.DisplayCardService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/cards")
public class DisplayCardController {

    private final DisplayCardService displayCardService;

    private static final Logger logger = LoggerFactory.getLogger(DisplayCardController.class);

    @Autowired
    public DisplayCardController(DisplayCardService displayCardService) {
        this.displayCardService = displayCardService;
    }

    @GetMapping("/all-cards")
    public List<DisplayCard> getAll() {
        logger.info("Getting all cards");
        return this.displayCardService.getAll();
    }
}
