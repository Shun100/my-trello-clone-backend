package com.app.trello_clone.service;

import com.app.trello_clone.constant.CardStatus;
import com.app.trello_clone.dto.card.CreateCardRequest;
import com.app.trello_clone.dto.card.FindCardResponse;
import com.app.trello_clone.dto.card.UpsertCardRequest;
import com.app.trello_clone.entity.Card;
import com.app.trello_clone.repository.CardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;

@Service
@RequiredArgsConstructor
public class CardService {
  private final CardRepository cardRepository;

  /**
   * サンプルカード作成
   * @param laneId
   * @return cardId
   */
  public UUID createSample(UUID laneId) {
    UpsertCardRequest upsertCardRequest = new UpsertCardRequest();
    upsertCardRequest.setLaneId(laneId);
    upsertCardRequest.setTitle("Sample Card");
    upsertCardRequest.setPosition(0);
    upsertCardRequest.setStatus(CardStatus.TODO);

    Calendar cal = Calendar.getInstance();
    cal.setTime(new Date()); // 現在日時
    cal.add(Calendar.DAY_OF_MONTH, 14); // 14日後
    upsertCardRequest.setDueDate(cal.getTime());

    return cardRepository.create(upsertCardRequest);
  }

  /**
   * カード検索
   * @param laneId
   * @return findCardResponse
   */
  public List<FindCardResponse> find(UUID laneId) {
    List<FindCardResponse> cardDTOs = new ArrayList<>();

    List<Card> cardEntities = cardRepository.findByLaneId(laneId);
    for (Card cardEntity : cardEntities) {
      FindCardResponse cardDTO = new FindCardResponse();
      cardDTO.setId(cardEntity.getId());
      cardDTO.setTitle(cardEntity.getTitle());
      cardDTO.setPosition(cardEntity.getPosition());
      cardDTO.setStatus(cardEntity.getStatus());
      cardDTO.setDueDate(cardEntity.getDueDate());
      cardDTO.setCreatedAt(cardEntity.getCratedAt());
      cardDTO.setUpdatedAt(cardEntity.getUpdatedAt());
      cardDTOs.add(cardDTO);
    }

    return cardDTOs;
  }

  public Card create(CreateCardRequest requestDTO) {
    Card card = new Card();
    card.setTitle(requestDTO.getTitle());
    card.setLaneId(requestDTO.getLaneId());
    card.setPosition(requestDTO.getPosition());
    card.setStatus(CardStatus.TODO);
    card.setDescription("");
    card.setDueDate(requestDTO.getDueDate());

    return cardRepository.create(card);
  }
}
