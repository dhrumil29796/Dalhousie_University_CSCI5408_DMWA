package com.dal.database.utils;

import com.dal.database.PrintInfo;

import java.io.File;
import java.io.FileWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

public class LogGenerator {

  private static LogGenerator instance = null;
  private static String previousDate = "";
  private static boolean eventLogDate = false;
  private static boolean generalLogDate = false;
  private static boolean queryLogDate = false;

  private LogGenerator() {
    logFileGenerator();
  }

  public static LogGenerator getInstance() {
    if (instance == null) {
      instance = new LogGenerator();
    }
    return instance;
  }

  public static String pathOfGeneralLogFiles = "./FilesOfData/Logs/GeneralLogs.txt";
  public static String pathOfEventLogFiles = "./FilesOfData/Logs/EventLogs.txt";
  public static String pathOfQueryLogFiles = "./FilesOfData/Logs/QueryLogs.txt";
  final PrintInfo printer = PrintInfo.getInstance();

  FileWriter event;
  FileWriter general;
  FileWriter query;

  private void logFileGenerator() {
    try {
      File eventFile = new File(pathOfEventLogFiles); //default event log text file
      File generalFile = new File(pathOfGeneralLogFiles);  //default general log text
      File queryFile = new File(pathOfQueryLogFiles);  //default query log text
      // file
      if (eventFile.createNewFile())   //if no file exists, we create one
      {
        System.out.println("New Event Logs created!");
      }
      if (generalFile.createNewFile())   //if no file exists, we create one
      {
        System.out.println("New General Logs created!");
      }
      if (queryFile.createNewFile())   //if no file exists, we create one
      {
        System.out.println("New General Logs created!");
      }
      event = new FileWriter(eventFile, true);   //true means while is
      // appended
      general = new FileWriter(generalFile, true);
      query = new FileWriter(queryFile, true);
    } catch (Exception e) {
      printer.printError(e.getMessage());
    }
  }

  private String getDate(){

    Date date = new Date();
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MMM/YYYY - EEE");
    String dateVal = simpleDateFormat.format(date);
    dateVal = "\n\n\t[ "+dateVal+" ] #-----------------------------------------------------------------------# \n\n";

    return dateVal;
  }

  public void writeToGeneralLogFile(String input) {
    try {
      String dateVal = getDate();

      if(dateVal.equals(previousDate) && generalLogDate){
        dateVal =  "";
      }
      else{
        previousDate = dateVal;
      }
      generalLogDate = true;
      general.append(dateVal);
      general.append(input);
      general.flush();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  public void writeToEventLogFile(String input) {
    try {
      String dateVal = getDate();

      if(dateVal.equals(previousDate) && eventLogDate){
        dateVal =  "";
      }
      else{
        previousDate = dateVal;
      }
      previousDate = dateVal;
      eventLogDate = true;
      event.append(dateVal);
      event.append(input);
      event.flush();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  public void writeToQueryLogFile(String input) {
    try {
      String dateVal = getDate();

      if(dateVal.equals(previousDate) && queryLogDate){
        dateVal =  "";
      }
      else{
        previousDate = dateVal;
      }
      previousDate = dateVal;
      queryLogDate = true;
      query.append(dateVal);
      query.append(input);
      query.flush();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
