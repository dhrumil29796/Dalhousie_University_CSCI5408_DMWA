package com.dal.database.CreateQueries;

import com.dal.database.DataStorage.AllDatabases;
import com.dal.database.DataStorage.Database;
import com.dal.database.PrintInfo;
import com.dal.database.saveData.WriteDatabaseToFile;

public class CreateDatabase {

    public Database database;
    public CreateDatabase(){
    }

    public boolean addDatabase(String databaseName){
        databaseName = databaseName.toUpperCase();
        if(AllDatabases.getInstance().databaseMap.containsKey(databaseName)){
            PrintInfo.getInstance().printError("\n\tDatabase Already Exist\n");
            return false;
        }
        database = new Database();
        database.databaseName = databaseName;
        AllDatabases.getInstance().databaseMap.put(databaseName, database);
        WriteDatabaseToFile.getInstance().writeThisDatabasesList(AllDatabases.getInstance());
        return true;
    }


}
