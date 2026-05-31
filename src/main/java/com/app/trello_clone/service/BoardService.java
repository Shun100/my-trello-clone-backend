package com.app.trello_clone.service;

import com.app.trello_clone.constant.CardStatus;
import com.app.trello_clone.dto.board.FindBoardResponse;
import com.app.trello_clone.dto.card.FindCardResponse;
import com.app.trello_clone.dto.card.UpsertCardRequest;
import com.app.trello_clone.dto.lane.FindLaneResponse;
import com.app.trello_clone.entity.Board;
import com.app.trello_clone.repository.BoardRepository;
import com.app.trello_clone.repository.CardRepository;
import com.app.trello_clone.repository.LaneRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class BoardService {
  private final BoardRepository boardRepository;
  private final LaneService laneService;
  private final CardService cardService;

  /**
   * create a sample board
   * @param userId
   * @return response
   */
  @Transactional
  public FindBoardResponse create(UUID userId) {
    // create new board
    String title = "My board";
    UUID boardId = boardRepository.create(userId, title);
    Board created = boardRepository.findByUserId(userId);

    // create a sample lane
    FindLaneResponse laneResponse = laneService.createSample(boardId);

    // create a sample card
    FindCardResponse cardResponse = cardService.createSample(laneResponse.getId());

    // response
    laneResponse.getCards().add(cardResponse);

    FindBoardResponse boardResponse  = new FindBoardResponse();
    boardResponse.setId(boardId);
    boardResponse.setTitle(title);
    boardResponse.getLanes().add(laneResponse);
    boardResponse.setCreatedAt(created.getCreatedAt());
    boardResponse.setUpdatedAt(created.getUpdatedAt());

    return boardResponse;
  }
}
