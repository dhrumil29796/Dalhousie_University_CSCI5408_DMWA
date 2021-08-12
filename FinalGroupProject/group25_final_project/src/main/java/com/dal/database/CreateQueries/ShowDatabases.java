package com.dal.database.CreateQueries;

import com.dal.database.DataStorage.AllDatabases;
import com.dal.database.DataStorage.Database;
import com.dal.database.utils.BasicInformation;

import java.util.Map;

public class ShowDatabases {

    public ShowDatabases(){
        System.out.println("All Database Tables:");
        System.out.println("###---------------------------------####\n");

    }

    public void showAllDatabases(){

        for(Map.Entry<String, Database> databaseEntry : AllDatabases.getInstance().databaseMap.entrySet()){
            if(databaseEntry.getKey().equals(BasicInformation.getInstance().getLockedDatabase())){
                System.out.print(" * ");
            }
            System.out.println(databaseEntry.getKey());
        }
        System.out.println();
    }

}
