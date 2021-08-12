package com.dal.database.DataStorage;

import com.dal.database.fetchdatabase.FetchDataFromDataFile;

import java.io.Serializable;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

public class TableRowEntryStructure implements Serializable {
    public Map<String, Object> Inputs = null;
    private String space = Table.space+"\t\t";
    public TableRowEntryStructure(){
        Inputs = new LinkedHashMap<>();
    }

    public String getMyRowData(){
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("{\n");
        boolean first = true;

        for(Map.Entry<String, Object> entry : Inputs.entrySet()){
            if(!first){
                stringBuilder.append(" , \n");
            }
            else{
                first = false;
            }
            stringBuilder.append(space+"\t"+entry.getKey() + " : '" + entry.getValue()+"'");
        }
        stringBuilder.append("\n");

        stringBuilder.append(space+"}");

        return stringBuilder.toString();
    }

    public static TableRowEntryStructure fetchMyRow(String keyword){
        TableRowEntryStructure row = new TableRowEntryStructure();
        String key = null;
        String value = null;
        boolean entryReceived = false;
        for(;;){
            if(key != null && value != null){
                if(value.equalsIgnoreCase("null")){
                    value = null;
                }
                row.Inputs.put(key, value);
                key = null;
                value = null;
            }
            if (FetchDataFromDataFile.tokens == null || keyword == null) {
                return row;
            }
            if(keyword.equals("]") || keyword.equals("}") ){
                return row;
            }
            if(!entryReceived){
                key = keyword;
                entryReceived = !entryReceived;
                keyword = FetchDataFromDataFile.getNextKeyword();
                continue;
            }
            else{
                value = keyword;
                entryReceived = !entryReceived;
                keyword = FetchDataFromDataFile.getNextKeyword();
                continue;
            }
        }
    }

}
