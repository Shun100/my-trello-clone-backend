package com.app.trello_clone.service;

import com.app.trello_clone.constant.CardStatus;
import com.app.trello_clone.dto.constant.Constants;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

@Service
public class ConstService {
  public Constants getConstants() {
    Constants constants = new Constants();

    List<String> cardStatus = Arrays
      .stream(CardStatus.values())
      .map(CardStatus::name)
      .toList();
    constants.setCardStatus(cardStatus);

    return constants;
  }
}
