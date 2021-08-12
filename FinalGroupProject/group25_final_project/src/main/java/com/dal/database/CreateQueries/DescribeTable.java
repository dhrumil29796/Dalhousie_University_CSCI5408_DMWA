package com.dal.database.CreateQueries;

import com.dal.database.DataStorage.Database;
import com.dal.database.DataStorage.Table;
import com.dal.database.PrintInfo;
import com.dal.database.utils.BasicInformation;

import java.util.Map;

public class DescribeTable {

    public boolean descThisTable(Table table){
        return descThisTable(table, BasicInformation.getInstance().fetchDatabase());
    }

    public boolean descThisTable(Table table, Database database){
        return descThisTable(table, BasicInformation.getInstance().fetchDatabase(), false);
    }

    public boolean descThisTable(Table table, Database database, boolean ERD){

        /*
                if(ERD is true) --->> save to file output
         */

        if(table == null || table.columnNamesAndInputType == null){
            PrintInfo.getInstance().printError("\n\tTable is Invalid!!\n");
            return false;
        }
        PrintInfo.getInstance().printMessage("\n#----------------------------------------------#\n");
        PrintInfo.getInstance().printMessage("\t Database::"+database.databaseName
                +"\t\t----->\t\tTableName:: "+table.tableName + "\n");
        PrintInfo.getInstance().printMessage("#----------------------------------------------#\n");
        for(Map.Entry<String, String> entry : table.columnNamesAndInputType.entrySet()){
            if(table.primaryKey != null && table.primaryKey.equalsIgnoreCase(entry.getKey())){
                PrintInfo.getInstance().printMessage("\n\t\t*Primary Key:");
            }
            else if(table.foreignKey != null && table.foreignKey.equalsIgnoreCase(entry.getKey())){
                PrintInfo.getInstance().printMessage("\n\t\t*Foreign Key:");
            }
            else{
                PrintInfo.getInstance().printMessage("\n\t\t\t\t\t");
            }

            PrintInfo.getInstance().printMessage("\t\t"+entry.getKey()+"\t\t----->\t\t"+ entry.getValue());
        }
        PrintInfo.getInstance().printMessage("\n#----------------------------------------------#\n");

        return true;
    }
}
