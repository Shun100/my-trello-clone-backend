package com.app.trello_clone.service;

import com.app.trello_clone.constant.CardStatus;
import com.app.trello_clone.dto.board.FindBoardResponse;
import com.app.trello_clone.dto.card.FindCardResponse;
import com.app.trello_clone.dto.card.UpsertCardRequest;
import com.app.trello_clone.dto.lane.FindLaneResponse;
import com.app.trello_clone.entity.Board;
import com.app.trello_clone.entity.Lane;
import com.app.trello_clone.repository.BoardRepository;
import com.app.trello_clone.repository.CardRepository;
import com.app.trello_clone.repository.LaneRepository;
import lombok.RequiredArgsConstructor;
import org.hibernate.annotations.processing.Find;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class BoardService {
  private final BoardRepository boardRepository;
  private final LaneService laneService;
  private final CardService cardService;

  /**
   * ボード作成
   * @param userId
   * @return response
   */
  @Transactional
  public void create(UUID userId) {
    String title = "My board";
    UUID boardId = boardRepository.create(userId, title);
    UUID laneId = laneService.createSample(boardId);
    cardService.createSample(laneId);
  }

  /**
   * ボード検索
   * @param userId
   * @return findBoardResponse
   */
  public FindBoardResponse find(UUID userId) {
    Board board = boardRepository.findByUserId(userId);

    List<FindLaneResponse> laneDTOs = laneService.find(board.getId());
    for (FindLaneResponse laneDTO : laneDTOs) {
      List<FindCardResponse> cardDTOs = cardService.find(laneDTO.getId());
      laneDTO.setCards(cardDTOs);
    }

    FindBoardResponse boardDTO = new FindBoardResponse();
    boardDTO.setId(board.getId());
    boardDTO.setTitle(board.getTitle());
    boardDTO.setLanes(laneDTOs);
    boardDTO.setCreatedAt(board.getCreatedAt());
    boardDTO.setUpdatedAt(board.getUpdatedAt());

    return boardDTO;
  }
}
