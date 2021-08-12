package com.dal.database.CreateQueries;

import com.dal.database.DataStorage.AllDatabases;
import com.dal.database.DataStorage.Table;
import com.dal.database.DataStorage.TableRowEntryStructure;
import com.dal.database.PrintInfo;
import com.dal.database.utils.BasicInformation;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class InsertIntoTable {

    public InsertIntoTable(){}

    public boolean InsertIntoTableValues(String tableName, Map<String, Object> Inputs){

        tableName = tableName.toUpperCase();
        if(BasicInformation.getInstance().getLockedDatabase() != null){
            if(BasicInformation.getInstance().fetchDatabase().tables.containsKey(tableName)){
                Table table = BasicInformation.getInstance().fetchDatabase().tables.get(tableName);
                TableRowEntryStructure rowEntry = new TableRowEntryStructure();
                //Map<String, Object> rowEntry = new LinkedHashMap<>();
                for(Map.Entry<String, String> columnEntry : table.columnNamesAndInputType.entrySet()){
                    if(!Inputs.containsKey(table.primaryKey) && table.primaryKey != null){
                        PrintInfo.getInstance().printError("\n\tPrimary Key Cannot be NULL\n");
                        return false;
                    }
                    for(TableRowEntryStructure row : table.rows){
                        if(row.Inputs.get(table.primaryKey).toString().equalsIgnoreCase(Inputs.get(table.primaryKey).toString())){
                            PrintInfo.getInstance().printError("Primary Key Cannot be duplicate\n");
                            return false;
                        }
                    }
                    if(Inputs.containsKey(columnEntry.getKey())){
                        rowEntry.Inputs.put(columnEntry.getKey(), Inputs.get(columnEntry.getKey()));
                    }
                    else{
                        rowEntry.Inputs.put(columnEntry.getKey(), null);
                    }
                }
                table.rows.add(rowEntry);
                return true;
            }
            else{
                PrintInfo.getInstance().printError("\n\tTable not Present!!!\n");
            }
        }
        else{
            PrintInfo.getInstance().printError("\n\tSelect Database First!!!\n");
        }

        return false;
    }

}
