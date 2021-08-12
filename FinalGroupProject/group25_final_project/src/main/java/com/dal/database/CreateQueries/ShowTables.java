package com.dal.database.CreateQueries;

import com.dal.database.DataStorage.Database;
import com.dal.database.DataStorage.Table;
import com.dal.database.PrintInfo;
import com.dal.database.utils.BasicInformation;

import java.util.Map;

public class ShowTables {

    public ShowTables(){
        Database database = BasicInformation.getInstance().fetchDatabase();
        PrintInfo.getInstance().printMessage("\n\n#----------------------------------------------#\n");
        PrintInfo.getInstance().printMessage("\t Database::"+database.databaseName+"\n");
        PrintInfo.getInstance().printMessage("#----------------------------------------------#\n");
        int t = 0;
        for(Map.Entry<String, Table> entry : database.tables.entrySet()){
            PrintInfo.getInstance().printMessage("\n\t\t Table"+(++t)+":: "+ entry.getKey());
        }
        PrintInfo.getInstance().printMessage("\n#----------------------------------------------#\n");
    }
}
