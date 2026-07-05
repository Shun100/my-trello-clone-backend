package com.app.trello_clone.controller;

import com.app.trello_clone.dto.card.CreateCardRequest;
import com.app.trello_clone.dto.card.UpdateCardPositionRequest;
import com.app.trello_clone.dto.card.UpdateCardRequest;
import com.app.trello_clone.entity.Card;
import com.app.trello_clone.service.CardService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.UUID;

@Controller
@RequiredArgsConstructor
public class CardController {
  private final CardService cardService;

  /**
   * カード新規作成
   * @param requestDTO
   * @return createdCard
   */
  @PostMapping("/cards/create")
  public ResponseEntity<Card> create(
    @Valid @RequestBody CreateCardRequest requestDTO) {
    Card newCard = cardService.create(requestDTO);

    return ResponseEntity
      .status(HttpStatus.CREATED)
      .body(newCard);
  }

  /**
   * カード更新
   * @param requestDTO
   */
  @PostMapping("/cards/update")
  public ResponseEntity<Void> update(
    @Valid @RequestBody UpdateCardRequest requestDTO) {
    System.out.println(requestDTO.toString());
    cardService.update(requestDTO);

    return ResponseEntity.ok().build();
  }

  /**
   * カード位置更新
   * @param requestDTOs
   * @return responseEntity
   */
  @PostMapping("/cards/update/position")
  public ResponseEntity<Void> updatePosition(
    @Valid @RequestBody List<UpdateCardPositionRequest> requestDTOs) {
    cardService.updatePosition(requestDTOs);

    return ResponseEntity.ok().build();
  }

  /**
   * カード削除
   * @param cardId
   */
  @DeleteMapping("/cards/{cardId}")
  public ResponseEntity<Void> delete(@PathVariable UUID cardId) {
    cardService.delete(cardId);

    return ResponseEntity.ok().build();
  }
}
