package com.app.trello_clone.controller;

import com.app.trello_clone.dto.constant.Constants;
import com.app.trello_clone.service.ConstService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class ConstController {
  private final ConstService constService;

  @GetMapping("/constants")
  public ResponseEntity<Constants> getConstants () {
    Constants constants = constService.getConstants();

    return ResponseEntity
      .status(HttpStatus.OK)
      .body(constants);
  }
}
