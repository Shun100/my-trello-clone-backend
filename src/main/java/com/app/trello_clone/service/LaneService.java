package com.app.trello_clone.service;

import com.app.trello_clone.dto.lane.FindLaneResponse;
import com.app.trello_clone.entity.Lane;
import com.app.trello_clone.repository.LaneRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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
    return laneRepository.create(boardId, "Sample lane", 1);
  }

  /**
   * レーン検索
   * @param boardId
   * @return response
   */
  public List<FindLaneResponse> find(UUID boardId) {
    List<Lane> lanes = laneRepository.findByBoardId(boardId);

    List<FindLaneResponse> laneDTOs = new ArrayList<>();

    for (Lane lane : lanes) {
      FindLaneResponse laneDTO = new FindLaneResponse();
      laneDTO.setId(lane.getId());
      laneDTO.setTitle(lane.getTitle());
      laneDTO.setPosition(lane.getPosition());
      laneDTO.setCreatedAt(lane.getCreatedAt());
      laneDTO.setUpdatedAt(lane.getUpdatedAt());
      laneDTOs.add(laneDTO);
    }

    return laneDTOs;
  }
}
