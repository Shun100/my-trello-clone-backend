package com.app.trello_clone.controller;

import com.app.trello_clone.dto.board.FindBoardResponse;
import com.app.trello_clone.service.BoardService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.UUID;

@Controller
@RequiredArgsConstructor
public class BoardController {
  private final BoardService boardService;

  /**
   * 新規board登録
   * @param userId
   * @return responseEntity
   */
  @PostMapping("/board/create")
  public ResponseEntity<FindBoardResponse> create(
    @Valid @RequestBody UUID userId) {
      FindBoardResponse response = boardService.create(userId);

      return ResponseEntity
        .status(HttpStatus.CREATED)
        .body(response);
  }


}
