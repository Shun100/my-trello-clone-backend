package com.app.trello_clone.controller;

import com.app.trello_clone.dto.auth.SignupRequest;
import com.app.trello_clone.dto.lane.CreateLaneRequest;
import com.app.trello_clone.dto.lane.UpdateLaneRequest;
import com.app.trello_clone.entity.Lane;
import com.app.trello_clone.service.LaneService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@Controller
@RequiredArgsConstructor
public class LaneController {
  private final LaneService laneService;

  /**
   * レーン作成
   * @param request
   * @return lane
   */
  @PostMapping("/lane/create")
  public ResponseEntity<Lane> create(
    @Valid @RequestBody CreateLaneRequest request
    ) {

    Lane lane = laneService.create(request);

    return ResponseEntity
      .status(HttpStatus.CREATED)
      .body(lane);
  }

  /**
   * レーン更新
   * @param requests
   */
  @PostMapping("/lanes")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void update(
    @Valid @RequestBody List<UpdateLaneRequest> requests
    ) {
    laneService.update(requests);
  }

  /**
   * レーン削除
   * @param id
   */
  @DeleteMapping("/lanes/{id}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void delete(
    @PathVariable UUID id
  ) {
    laneService.delete(id);
  }
}
