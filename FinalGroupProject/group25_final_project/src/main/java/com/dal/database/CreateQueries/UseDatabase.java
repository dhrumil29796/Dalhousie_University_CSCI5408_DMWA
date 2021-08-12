package com.dal.database.CreateQueries;

import com.dal.database.DataStorage.AllDatabases;
import com.dal.database.PrintInfo;
import com.dal.database.utils.BasicInformation;

public class UseDatabase {

    public UseDatabase(){}

    public boolean UseThisDatabase(String database){
        database = database.toUpperCase();
        if(AllDatabases.getInstance().databaseMap.containsKey(database)){
            BasicInformation.getInstance().setLockedDatabase(database);
            return true;
        }
        else{
            PrintInfo.getInstance().printError("\n\tDatabase does not exist!!\n");
        }
        return false;
    }

}
