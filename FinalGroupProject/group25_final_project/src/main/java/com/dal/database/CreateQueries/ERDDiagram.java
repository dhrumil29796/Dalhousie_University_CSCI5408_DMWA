package com.dal.database.CreateQueries;
import com.dal.database.DataStorage.Database;
import com.dal.database.DataStorage.AllDatabases;
import com.dal.database.DataStorage.Table;
import com.dal.database.PrintInfo;
import com.dal.database.utils.BasicInformation;

import java.util.Map;

public class ERDDiagram {

    public boolean makeERDDiagram(){
        Map<String, Database> allDatabases = AllDatabases.getInstance().databaseMap;

        for(Map.Entry<String, Database> entry : allDatabases.entrySet()){
            DescribeTable desc = new DescribeTable();
            Database database = entry.getValue();
            if(database == null){
                continue;
            }
            for(Map.Entry<String, Table> table : database.tables.entrySet()){
                desc.descThisTable(table.getValue(), database, true);
            }
            }
            return true;
    }

    public boolean erdDiagram(){
        Database database = BasicInformation.getInstance().fetchDatabase();
        if (database == null) {
            PrintInfo.getInstance().printError("\n\tDatabase is not selected!!!!\n");
            return false;
        }
        DescribeTable desc = new DescribeTable();
        for(Map.Entry<String, Table> table : database.tables.entrySet()){
            desc.descThisTable(table.getValue(), database, true);
        }

        PrintInfo.getInstance().printMessage("\n\n#----------------------------------------------------------#\n");
        PrintInfo.getInstance().printMessage("\t\tER Diagram:\n");
        PrintInfo.getInstance().printMessage("\n#----------------------------------------------------------#\n");
        PrintInfo.getInstance().printMessage("\n\n\t\t\tPrimary Key:\t------------>\tForeign Key\n");

        for(Map.Entry<String, Table> entry1 : database.tables.entrySet()){
            for(Map.Entry<String, Table> entry2 : database.tables.entrySet()){

                if(entry1.getValue().primaryKey.equalsIgnoreCase(entry2.getValue().foreignKey)){
                    PrintInfo.getInstance().printMessage("\n\t\t\t"+entry1.getKey()+"\t------------>\t"+entry2.getKey());
                }
            }
        }
        PrintInfo.getInstance().printMessage("\n#----------------------------------------------------------#\n");

        return true;
    }
}
