package com.dal.database.utils;

import com.dal.database.Login.AllUsers;
import com.dal.database.Login.UserDetails;

import java.util.Map;

public class Validation {

  public static boolean isValidInput(String input) {
    try {
      if (input.isEmpty() && input.trim().isEmpty()) {
        return false;
      } else {
        return true;
      }
    } catch (Exception e) {
      return false;
    }
  }

  public static boolean isAlphaNumeric(String input) {
    try {
      if (input.isEmpty() && input.trim().isEmpty()) {
        return false;
      } else if (input.matches("[A-Za-z0-9]+")) {
        return true;
      } else {
        return false;
      }
    } catch (Exception e) {
      return false;
    }
  }

  public static boolean userExists(String userName, String password) {

    Map<String, UserDetails> usersMap = AllUsers.getInstance().getUsersList();
    try {
      if (usersMap.containsKey(userName)) {
        return true;
      } else {
        return false;
      }
    } catch (Exception e) {
      return true;
    }
  }
}
