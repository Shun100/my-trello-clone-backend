package com.app.trello_clone.controller;

import com.app.trello_clone.dto.card.CreateCardRequest;
import com.app.trello_clone.entity.Card;
import com.app.trello_clone.service.CardService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
@RequiredArgsConstructor
public class CardController {
  private final CardService cardService;

  @PostMapping("/cards/create")
  public ResponseEntity<Card> create(
    @Valid @RequestBody CreateCardRequest requestDTO) {
    Card newCard = cardService.create(requestDTO);

    return ResponseEntity
      .status(HttpStatus.CREATED)
      .body(newCard);
  }
}
