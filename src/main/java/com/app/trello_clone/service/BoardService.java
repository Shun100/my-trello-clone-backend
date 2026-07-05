package com.app.trello_clone.service;

import com.app.trello_clone.dto.board.FindBoardResponse;
import com.app.trello_clone.entity.Board;
import com.app.trello_clone.entity.Card;
import com.app.trello_clone.entity.Lane;
import com.app.trello_clone.repository.BoardRepository;
import com.app.trello_clone.repository.CardRepository;
import com.app.trello_clone.repository.LaneRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class BoardService {
  private final BoardRepository boardRepository;

  // ビジネスロジックがあるときは他機能のServiceを使う
  private final LaneService laneService;
  private final CardService cardService;

  // ただ情報を取るだけなら他機能のRepositoryを直接使う
  private final LaneRepository laneRepository;
  private final CardRepository cardRepository;

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
    List<Lane> lanes = laneRepository.findByBoardId(board.getId());
    List<UUID> laneIds = lanes.stream()
      .map(Lane::getId)
      .toList();
    List<Card> cards = cardRepository.findByLaneIds(laneIds);

    return new FindBoardResponse(board, lanes, cards);
  }
}
