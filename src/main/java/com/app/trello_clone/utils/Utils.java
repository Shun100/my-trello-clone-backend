package com.app.trello_clone.utils;

public class Utils {
  public static java.sql.Date toSqlDate(java.util.Date utilDate) {
    return utilDate == null ? null : new java.sql.Date(utilDate.getTime());
  }
}
