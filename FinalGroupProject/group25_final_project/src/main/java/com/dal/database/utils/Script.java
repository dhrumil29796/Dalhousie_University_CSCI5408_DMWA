package com.dal.database.utils;

import com.dal.database.DataStorage.AllDatabases;
import com.dal.database.Login.AllUsers;
import com.dal.database.fetchdatabase.FetchDataFromDataFile;
import com.dal.database.fetchdatabase.FetchDataFromFiles;

import static com.dal.database.utils.Application.AllDatabasesPath;
import static com.dal.database.utils.Application.AllUsersPath;

public class Script {
    public static void runScript(){

        FetchDataFromDataFile.fetchDataFromFile(AllDatabasesPath);
        AllDatabases.loadAllMydatabases();
        FetchDataFromDataFile.fetchDataFromFile(AllUsersPath);
        AllUsers.loadAllUsers();
        //FetchDataFromFiles.fetchAllDatabases();
        //FetchDataFromFiles.fetchAllUsers();

    }
}
