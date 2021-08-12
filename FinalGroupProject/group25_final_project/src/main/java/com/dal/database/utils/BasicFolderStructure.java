package com.dal.database.utils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class BasicFolderStructure {

  String usersDataPath = "./RawData/LoginUsersData/";
  String databaseDataPath = "./RawData/databases/";
  String logFilesPath = "./FilesOfData/Logs/";
  String sqlDump = "./RawData/SQL_Dump/";
  String stringUserData = "./FilesOfData/LoginUsersData/";
  String stringFileData = "./FilesOfData/databases/";
  String stringFileSqlDump = "./FilesOfData/SQL_Dump/";

  public BasicFolderStructure() throws IOException {
    createFolderStructure(usersDataPath);
    createFolderStructure(databaseDataPath);
    createFolderStructure(logFilesPath);
    createFolderStructure(sqlDump);
    createFolderStructure(stringFileData);
    createFolderStructure(stringUserData);
    createFolderStructure(stringFileSqlDump);
  }

  private void createFolderStructure(String pathToCreate) throws IOException {
    Path path = Paths.get(pathToCreate);
    Files.createDirectories(path);
  }
}
