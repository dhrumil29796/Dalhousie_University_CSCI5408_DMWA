package com.dal.database.CreateQueries;

import com.dal.database.fetchdatabase.FetchDataFromFiles;

public class Rollback {

    public Rollback(){
        FetchDataFromFiles.fetchAllDatabases();
        FetchDataFromFiles.fetchAllUsers();
    }

}
