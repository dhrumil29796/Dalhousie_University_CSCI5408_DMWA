package com.dal.database.CreateQueries;

import com.dal.database.DataStorage.Database;
import com.dal.database.DataStorage.Table;
import com.dal.database.DataStorage.TableRowEntryStructure;
import com.dal.database.PrintInfo;
import com.dal.database.utils.BasicInformation;

import javax.swing.table.TableCellEditor;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

public class DeleteTableEntry {
    private Database database;

    public DeleteTableEntry(){
        database = BasicInformation.getInstance().fetchDatabase();
        if(database == null) {
            PrintInfo.getInstance().printError("\n\tSelect Database First!!!!\n");
            return;
        }
    }

    public boolean deleteTableEntries(Table originalTable){
        return deleteTableEntries(originalTable, originalTable);
    }

    public boolean deleteTableEntries(Table originalTable, Table removeEntries){
        if(database == null || originalTable == null || removeEntries == null){
            return false;
        }
        Set<TableRowEntryStructure> rowSet = new LinkedHashSet<>();
        rowSet.addAll(originalTable.rows);
        for(TableRowEntryStructure row : removeEntries.rows){
            if(!rowSet.contains(row)){
                PrintInfo.getInstance().printError("Item Does not Exist!!!!");
                return false;
            }
            rowSet.remove(row);
        }
        originalTable.rows = new ArrayList<>(rowSet);
        PrintInfo.getInstance().printMessage("\n\tDeleted "+removeEntries.rows.size()+" rows successfully\n");

        return true;
    }

//    private List<Integer> evaluateCondition(List<String> condition){
//        List<Integer> indexes = new ArrayList<>();
//
//    }





}
