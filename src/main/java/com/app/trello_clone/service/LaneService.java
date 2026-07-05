package com.app.trello_clone.service;

import com.app.trello_clone.dto.lane.CreateLaneRequest;
import com.app.trello_clone.dto.lane.UpdateLaneRequest;
import com.app.trello_clone.entity.Lane;
import com.app.trello_clone.repository.LaneRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class LaneService {
  private final LaneRepository laneRepository;

  /**
   * サンプルレーン作成
   * @param boardId
   * @return laneId
   */
  public UUID createSample(UUID boardId) {
    return laneRepository
      .create(boardId, "Sample Lane", 0)
      .getId();
  }

  /**
   * レーン作成
   */
  public Lane create(CreateLaneRequest request) {
    return laneRepository.create(
      request.getBoardId(),
      request.getTitle(),
      request.getPosition()
    );
  }

  /**
   * レーン更新
   * @param requestDTOs
   */
  public void update(List<UpdateLaneRequest> requestDTOs) {

    List<Lane> lanes = requestDTOs.stream()
      .map(dto -> {
        Lane lane = new Lane();
        lane.setId(dto.getId());
        lane.setTitle(dto.getTitle());
        lane.setPosition(dto.getPosition());
        return lane;
      })
      .toList();

    laneRepository.update(lanes);
  }

  /**
   * レーン削除
   * @param id
   */
  public void delete(UUID id) {
    laneRepository.delete(id);
  }
}
