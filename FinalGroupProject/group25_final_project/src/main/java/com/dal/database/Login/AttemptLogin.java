package com.dal.database.Login;

import com.dal.database.utils.BasicInformation;
import com.dal.database.utils.HashAlgorithm;

import java.util.Map;
import java.util.Scanner;

import static java.lang.Thread.sleep;

public class AttemptLogin {

  AllUsers allUsers = null;

  public AttemptLogin() {
    allUsers = AllUsers.getInstance();
  }

  public boolean loginUser(Scanner sc) {

    boolean first = true;
    for (int t = 0; t < 3; t++) {

      if (!first) {
        System.err.println("\n\tEnter Correct Credentials!!!");
        try {
          sleep(100);
        } catch (InterruptedException e) {
          e.printStackTrace();
        }
      }
      first = false;
      System.out.print("\n\tEnter UserName : ");
      String userName = sc.nextLine();
      System.out.print("\n\tEnter Password : ");
      String password = sc.nextLine();

      final String hashedPassword = HashAlgorithm.getSHA256Hash(password);

      Map<String, UserDetails> usersMap = allUsers.getUsersList();

      if (usersMap.containsKey(userName) && (usersMap.get(userName)).getPassword().equals(hashedPassword)) {
        System.out.println("\n\t\tlogin Successful!!!");
        BasicInformation.getInstance().setLoginUser(userName);
        return true;
      }
    }

    System.err.println("\n\tFailed to login!!!");
    try {
      sleep(100);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
    return false;
  }


}
