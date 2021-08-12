package com.dal.database.CreateQueries;


import com.dal.database.DataStorage.Table;
import com.dal.database.DataStorage.TableRowEntryStructure;
import com.dal.database.PrintInfo;

import java.util.Map;

public class Select {

    public Select(){
        PrintInfo.getInstance().printMessage("\n\t#-----------------------------------------------------#\n");

    }

    public boolean printThisTable(Table table){
        return printThisTable(table, table.columnNamesAndInputType);
    }

    public boolean printThisTable(Table table, Map<String,String> columnNames){
        if(table == null || columnNames == null){
            PrintInfo.getInstance().commandError();
            return false;
        }
        printTableHeader(columnNames);
        int rowCount = 0;
        for(TableRowEntryStructure row : table.rows){
            PrintInfo.getInstance().printMessage("\t\t\t");
            PrintInfo.getInstance().printMessage(Integer.toString(++rowCount));
            for(Map.Entry<String, String> entry: columnNames.entrySet()){
                PrintInfo.getInstance().printMessage("\t\t\t");
                Object object = row.Inputs.get(entry.getKey());
                if(object == null){
                    PrintInfo.getInstance().printMessage("NULL");
                }
                else{
                    PrintInfo.getInstance().printMessage(object.toString());
                }
            }
            PrintInfo.getInstance().printMessage("\n");
        }
        printEndOfTable();
        return true;
    }

    private boolean printTableHeader(Map<String,String> columnNames){

        PrintInfo.getInstance().printMessage("\n\n");
        PrintInfo.getInstance().printMessage("\t\t\t");
        PrintInfo.getInstance().printMessage("SNo.");

        for(Map.Entry<String, String> entry: columnNames.entrySet()){
            PrintInfo.getInstance().printMessage("\t\t\t");
            PrintInfo.getInstance().printMessage(entry.getKey());
        }
        PrintInfo.getInstance().printMessage("\n\n");

        return true;

    }

    private void printEndOfTable(){
        PrintInfo.getInstance().printMessage("\n\t#-----------------------------------------------------#\n");
    }

}
