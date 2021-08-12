package com.dal.database.CreateQueries;

import com.dal.database.DataStorage.AllDatabases;
import com.dal.database.PrintInfo;
import com.dal.database.saveData.WriteDatabaseToFile;
import com.dal.database.utils.BasicFolderStructure;
import com.dal.database.utils.BasicInformation;

public class DropDatabase {

    public DropDatabase(){}

    public boolean dropThisDatabase(String Database){
        if(AllDatabases.getInstance().databaseMap.containsKey(Database)){
            if(Database.equals(BasicInformation.getInstance().getLockedDatabase())) {
                BasicInformation.getInstance().setLockedDatabase(null);
            }
            AllDatabases.getInstance().databaseMap.remove(Database);
            WriteDatabaseToFile.getInstance().writeThisDatabasesList(AllDatabases.getInstance());
            return true;
        }
        else{
            PrintInfo.getInstance().printError("\n\tDatabase does not exist!!\n");
        }
        return false;
    }
}
