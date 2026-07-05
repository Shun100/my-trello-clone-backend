package com.app.trello_clone.controller;

import com.app.trello_clone.dto.board.FindBoardResponse;
import com.app.trello_clone.service.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.UUID;

@Controller
@RequiredArgsConstructor
public class BoardController {
  private final BoardService boardService;

  @GetMapping("/boards/{userId}")
  public ResponseEntity<FindBoardResponse> find(
    @PathVariable UUID userId) {
    FindBoardResponse data = boardService.find(userId);

    return ResponseEntity
      .status(HttpStatus.OK)
      .body(data);
  }
}
