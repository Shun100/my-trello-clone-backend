package com.app.trello_clone.controller;

import com.app.trello_clone.dto.auth.SignupRequest;
import com.app.trello_clone.dto.lane.CreateLaneRequest;
import com.app.trello_clone.entity.Lane;
import com.app.trello_clone.service.LaneService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@Controller
@RequiredArgsConstructor
public class LaneController {
  private final LaneService laneService;

  @PostMapping("/lane/create")
  public ResponseEntity<Lane> create(
    @Valid @RequestBody CreateLaneRequest request
    ) {

    Lane lane = laneService.create(request);

    return ResponseEntity
      .status(HttpStatus.CREATED)
      .body(lane);
  }

  @DeleteMapping("/lanes/{id}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void delete(
    @PathVariable UUID id
  ) {
    laneService.delete(id);
  }
}
