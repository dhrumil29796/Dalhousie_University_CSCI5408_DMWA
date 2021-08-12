package com.dal.database.saveData;

import com.dal.database.DataStorage.AllDatabases;
import com.dal.database.utils.Application;

public class WriteDatabaseToFile {

    private static WriteDatabaseToFile instance = null;
    private WriteDatabaseToFile(){

    }

    public void writeThisDatabasesList(AllDatabases databases){
        WriteObjectToFile writeObjectToFile = new WriteObjectToFile();
        writeObjectToFile.writeObject(databases, Application.pathOfDataBase);
        WriteStringToFile writeStringToFile = new WriteStringToFile();
        writeStringToFile.writeString(databases.getAllMyDatabases(), "./FilesOfData/databases/AllDatabases.txt");
    }

    public static WriteDatabaseToFile getInstance(){
        if(instance == null){
            instance = new WriteDatabaseToFile();
        }
        return instance;
    }


}
