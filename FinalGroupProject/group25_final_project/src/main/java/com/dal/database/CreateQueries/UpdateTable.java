package com.dal.database.CreateQueries;

import com.dal.database.DataStorage.Table;
import com.dal.database.DataStorage.TableRowEntryStructure;
import com.dal.database.PrintInfo;

import java.util.LinkedHashSet;
import java.util.Set;

public class UpdateTable {

    public boolean updateThisTable(Table originalTable, String columnName, String value){
        return updateThisTable(originalTable, originalTable, columnName, value);
    }

    public boolean updateThisTable(Table originalTable, Table updateTable, String columnName, String value){

        if(originalTable == null || updateTable == null){
            PrintInfo.getInstance().commandError();
            return false;
        }

        Set<TableRowEntryStructure> rowSet = new LinkedHashSet<>();
        rowSet.addAll(originalTable.rows);

        for(TableRowEntryStructure row : updateTable.rows){
            if(!rowSet.contains(row)){
                PrintInfo.getInstance().printError("Item Does not Exist!!!!");
                return false;
            }
            if(!row.Inputs.containsKey(columnName)){
                PrintInfo.getInstance().printError("\n\tKey Does not Exist!!!\n");
                return false;
            }
            row.Inputs.put(columnName, value);
        }
        PrintInfo.getInstance().printMessage("\n\tUpdated "+updateTable.rows.size()+" rows successfully\n");
        return true;
    }

}
