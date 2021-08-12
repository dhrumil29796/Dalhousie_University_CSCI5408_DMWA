package com.dal.database.CreateQueries;

import com.dal.database.DataStorage.AllDatabases;
import com.dal.database.DataStorage.Database;
import com.dal.database.PrintInfo;
import com.dal.database.utils.BasicInformation;
import com.dal.database.saveData.*;
import  com.dal.database.fetchdatabase.ReadObjectFromFile;

import java.io.File;
import java.util.Scanner;


public class SQLDump {

    public SQLDump(){

    }


    public void writeSQLDumpData(){
        Database database = BasicInformation.getInstance().fetchDatabase();

        String path = "./FilesOfData/SQL_Dump/"+database.databaseName;

        WriteStringToFile writeStringToFile = new WriteStringToFile();
        writeStringToFile.writeString(database.getMyDatabase(), path);
    }









    public boolean writeSQLDump(){
        Database database = BasicInformation.getInstance().fetchDatabase();

        String path = "./RawData/SQL_Dump/"+database.databaseName;

        WriteObjectToFile writeObjectToFile = new WriteObjectToFile();
        writeObjectToFile.writeObject(database, path);

        writeSQLDumpData();
        PrintInfo.getInstance().printMessage("\n\n\t\tSQL Dump Written Successfully!!!!\n\n");

        return true;
    }

    public boolean readSQL(){

        String path = "./RawData/SQL_Dump/";
        File dir = new File(path);
        File files[] = dir.listFiles();

        PrintInfo.getInstance().printMessage("\n#------------------------------------------------#\n");
        PrintInfo.getInstance().printMessage("\n\tList of Files:\n\n");
        PrintInfo.getInstance().printMessage("\n#------------------------------------------------#\n");
        int t=0;
        for(File file : files){
            PrintInfo.getInstance().printMessage("\n\t\t"+(++t)+" "+file.getName()+"\n");
        }
        PrintInfo.getInstance().printMessage("\n#------------------------------------------------#\n\n");
        PrintInfo.getInstance().printMessage("\n\tEnter the index of file to add: ");
        Scanner sc = new Scanner(System.in);
        int index = sc.nextInt();
        index = index-1;

        if(index < -1 || index > files.length){
            PrintInfo.getInstance().printError("\n\n\tEnter Correct index\n");
        }

        Database database = (Database) ReadObjectFromFile.getInstance().readObject(files[index].getAbsolutePath());

        AllDatabases.getInstance().databaseMap.put(database.databaseName, database);

        WriteDatabaseToFile.getInstance().writeThisDatabasesList(AllDatabases.getInstance());
        PrintInfo.getInstance().printMessage("\n\n\t\tSQL Dump Read Successfully!!!!\n\n");
        return true;
    }





}
