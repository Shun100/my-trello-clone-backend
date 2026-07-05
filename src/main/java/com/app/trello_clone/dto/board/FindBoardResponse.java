package com.app.trello_clone.dto.board;

import com.app.trello_clone.entity.Board;
import com.app.trello_clone.entity.Card;
import com.app.trello_clone.entity.Lane;
import java.util.List;

public record FindBoardResponse (
  Board board,
  List<Lane> lanes,
  List<Card> cards
) {
  // ここにメソッドを書くことも可能
}
