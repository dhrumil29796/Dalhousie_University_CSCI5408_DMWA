package com.dal.database.utils;

import com.dal.database.DataStorage.AllDatabases;
import com.dal.database.DataStorage.Database;
import com.dal.database.PrintInfo;

import java.io.Serializable;

public class BasicInformation {

  private static BasicInformation instance = null;
  private String lockedDatabase = null;
  private String loginUser = null;

  private BasicInformation() {
  }

  public static BasicInformation getInstance() {
    if (instance == null) {
      instance = new BasicInformation();
    }
    return instance;
  }

  public String getLockedDatabase() {
    return lockedDatabase;
  }

  public void setLockedDatabase(String lockedDatabase) {
    this.lockedDatabase = lockedDatabase;
  }

  public Database fetchDatabase() {
    String databaseName = BasicInformation.getInstance().getLockedDatabase();
    if (databaseName == null || databaseName.trim().isEmpty()) {
      return null;
    }
    Database database = AllDatabases.getInstance().databaseMap.get(databaseName);

    return database;
  }

  public String getLoginUser() {
    return loginUser;
  }

  public void setLoginUser(String loginUser) {
    this.loginUser = loginUser;
  }
}
