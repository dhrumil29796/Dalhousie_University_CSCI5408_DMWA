package com.dal.database.fetchdatabase;

import com.dal.database.DataStorage.AllDatabases;
import com.dal.database.Login.AllUsers;
import com.dal.database.utils.Application;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;

public class FetchDataFromFiles {


    private FetchDataFromFiles(){
    }

    public static void fetchAllUsers(){
        Object object = ReadObjectFromFile.getInstance().readObject(Application.pathOfUsers);
        if(object==null){
            return;
        }
        AllUsers.setAllUsers((AllUsers)object);
    }

    public static void fetchAllDatabases(){
        Object object = ReadObjectFromFile.getInstance().readObject(Application.pathOfDataBase);
        if(object==null){
            return;
        }
        AllDatabases.setInstance((AllDatabases)object);
    }



}
