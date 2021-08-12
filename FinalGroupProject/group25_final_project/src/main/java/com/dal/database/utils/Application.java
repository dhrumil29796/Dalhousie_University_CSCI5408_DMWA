package com.dal.database.utils;

import com.dal.database.PrintInfo;
import com.dal.database.fetchdatabase.FetchDataFromDataFile;
import com.dal.database.fetchdatabase.FetchDataFromFiles;

import java.io.IOException;
import java.util.Scanner;

public class Application {

  public static String AllDatabasesPath = "./FilesOfData/databases/allDatabases.txt";
  public static String AllUsersPath = "./FilesOfData/LoginUsersData/AllUsers";
  public static String pathOfUsers = "./RawData/LoginUsersData/AllUsers";
  public static String pathOfDataBase = "./RawData/databases/allDatabases";

  public static void main(String[] args) throws IOException {

    Script.runScript();

    final Scanner scanner = new Scanner(System.in);
    final PrintInfo printer = PrintInfo.getInstance();
    final InputFromUser inputFromUser = InputFromUser.getInstance();


    printer.printMessage("\t####################################\n");
    printer.printMessage("\tWelcome to DVM Relational Database:\n");
    printer.printMessage("\t####################################\n");

    while (true) {
      printer.printMessage("\n1. User registration.\n");
      printer.printMessage("2. User login.\n");
      printer.printMessage("3. Exit.\n");
      printer.printMessage("Select an option:\n");
      final String input = scanner.nextLine();

      switch (input) {
        case "1":
          inputFromUser.registerUser(scanner);
          break;
        case "2":
          inputFromUser.inputsFromUser(scanner);
          break;
        case "3":
          System.exit(0);
        default:
          break;
      }
    }
  }
}
