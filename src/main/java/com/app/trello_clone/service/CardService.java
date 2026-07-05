package com.app.trello_clone.service;

import com.app.trello_clone.constant.CardStatus;
import com.app.trello_clone.dto.card.CreateCardRequest;
import com.app.trello_clone.dto.card.UpdateCardPositionRequest;
import com.app.trello_clone.dto.card.UpdateCardRequest;
import com.app.trello_clone.entity.Card;
import com.app.trello_clone.repository.CardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

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
    Card card = new Card();
    card.setLaneId(laneId);
    card.setTitle("Sample Card");
    card.setPosition(0);
    card.setStatus(CardStatus.TODO);

    Calendar cal = Calendar.getInstance();
    cal.setTime(new Date()); // 現在日時
    cal.add(Calendar.DAY_OF_MONTH, 14); // 14日後
    card.setDueDate(cal.getTime());

    return cardRepository.create(card).getId();
  }

  /**
   * カード新規作成
   * @param requestDTO
   * @return createdCard
   */
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

  /**
   * カード更新
   * @param requestDTO
   */
  public void update(UpdateCardRequest requestDTO) {
    Card card = new Card();
    card.setId(requestDTO.getId());
    card.setTitle(requestDTO.getTitle());
    card.setStatus(requestDTO.getStatus());
    card.setDueDate(requestDTO.getDueDate());
    card.setDescription(requestDTO.getDescription());

    cardRepository.update(card);
  }

  /**
   * カード位置更新
   * @param requestDTOs
   */
  public void updatePosition(List<UpdateCardPositionRequest> requestDTOs) {
    List<Card> cards = new ArrayList<>();

    requestDTOs.forEach(requestDTO -> {
      Card card = new Card();
      card.setId(requestDTO.getCardId());
      card.setLaneId(requestDTO.getLaneId());
      card.setPosition(requestDTO.getPosition());
      cards.add(card);
    });

    cardRepository.updatePosition(cards);
  }

  /**
   * カード削除
   * @param cardId
   */
  public void delete(UUID cardId) {
    cardRepository.delete(cardId);
  }
}
