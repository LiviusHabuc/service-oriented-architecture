package com.backend.rest_server.service;

import com.backend.rest_server.model.DisplayCard;
import com.backend.rest_server.repository.DisplayCardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DisplayCardServiceImpl implements DisplayCardService {

    private final DisplayCardRepository displayCardRepository;

    @Autowired
    public DisplayCardServiceImpl(DisplayCardRepository displayCardRepository) {
        this.displayCardRepository = displayCardRepository;
    }

    @Override
    public List<DisplayCard> getAll() {
        return this.displayCardRepository.findAll();
    }

    @Override
    public void saveDisplayCard(DisplayCard displayCard) {
        this.displayCardRepository.save(displayCard);
    }
}
