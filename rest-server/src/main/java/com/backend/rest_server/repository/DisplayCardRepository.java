package com.backend.rest_server.repository;

import com.backend.rest_server.model.DisplayCard;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DisplayCardRepository extends JpaRepository<DisplayCard, Long> {
}
