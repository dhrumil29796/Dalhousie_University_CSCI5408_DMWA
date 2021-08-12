package com.dal.database.CreateQueries;

import com.dal.database.DataStorage.AllDatabases;
import com.dal.database.saveData.WriteDatabaseToFile;

public class Commit {

    public Commit(){
        WriteDatabaseToFile.getInstance().writeThisDatabasesList(AllDatabases.getInstance());
    }

}
