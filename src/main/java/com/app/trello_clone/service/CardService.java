package com.app.trello_clone.service;

import com.app.trello_clone.constant.CardStatus;
import com.app.trello_clone.dto.card.FindCardResponse;
import com.app.trello_clone.dto.card.UpsertCardRequest;
import com.app.trello_clone.entity.Card;
import com.app.trello_clone.repository.CardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CardService {
  private final CardRepository cardRepository;

  public FindCardResponse createSample(UUID laneId) {
    UpsertCardRequest upsertCardRequest = new UpsertCardRequest();
    upsertCardRequest.setLaneId(laneId);
    upsertCardRequest.setTitle("Sample Card");
    upsertCardRequest.setPosition(1);
    upsertCardRequest.setStatus(CardStatus.TODO);
    UUID cardId = cardRepository.create(upsertCardRequest);

    Card created = cardRepository.findByLaneId(laneId).getFirst();

    FindCardResponse response = new FindCardResponse();
    response.setId(cardId);
    response.setTitle(created.getTitle());
    response.setPosition(created.getPosition());
    response.setDueDate(created.getDueDate());
    response.setStatus(created.getStatus());
    response.setCreatedAt(created.getCratedAt());
    response.setUpdatedAt(created.getUpdatedAt());

    return response;
  }
}
