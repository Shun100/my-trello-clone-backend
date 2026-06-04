package com.app.trello_clone.dto;

public record ApiError(
  String code,
  String message
) {}
