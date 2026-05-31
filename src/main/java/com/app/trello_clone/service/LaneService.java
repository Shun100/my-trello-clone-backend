package com.app.trello_clone.service;

import com.app.trello_clone.dto.lane.FindLaneResponse;
import com.app.trello_clone.entity.Lane;
import com.app.trello_clone.repository.LaneRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class LaneService {
  private final LaneRepository laneRepository;

  /**
   * create a sample lane
   * @param boardId
   * @return response
   */
  public FindLaneResponse createSample(UUID boardId) {
    UUID laneId = laneRepository.create(boardId, "Sample lane", 1);
    Lane created = laneRepository.findByBoardId(boardId).getFirst();

    FindLaneResponse response = new FindLaneResponse();
    response.setId(laneId);
    response.setTitle(created.getTitle());
    response.setPosition(created.getPosition());
    response.setCreatedAt(created.getCreatedAt());
    response.setUpdatedAt(created.getUpdatedAt());

    return response;
  }
}
