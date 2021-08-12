package com.dal.database.DataStorage;

import com.dal.database.fetchdatabase.FetchDataFromDataFile;

import javax.xml.crypto.Data;
import java.io.Serializable;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class Database implements Serializable {

    public Map<String, Table > tables;

    public String databaseName = null;
    public static String space = AllDatabases.space+"\t";

    public Database(){
        tables = new HashMap<>();
    }

    public String getMyDatabase(){
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append( "{\n");
        stringBuilder.append(space+"\t"+ databaseName +" : [\n");

        boolean first = true;
        for(Map.Entry<String, Table> entry : tables.entrySet()){
            if(!first){
                stringBuilder.append(" , \n");
            }
            else{
                first = false;
            }
            stringBuilder.append(space + "\t\t" + entry.getKey() + " : " + entry.getValue().getMyTableData());
        }
        stringBuilder.append("\n");

        stringBuilder.append(space + "\t"+"]\n");
        stringBuilder.append(space+ "}");

        return stringBuilder.toString();
    }

    public static Database fetchMyDatabase(String databaseNameOriginal){
        Database database  = new Database();
        database.databaseName = databaseNameOriginal;
        String tableName = null;
        Table table = null;
        String keyword = FetchDataFromDataFile.getNextKeyword();

        boolean keywordReceived = false;
        for(;;){
            if(tableName != null && table != null){
                database.tables.put(tableName, table);
                tableName = null;
                table = null;
            }

            if (keyword == null) {
                return database;
            }

            if(keyword.equals("]")){
                return database;
            }
            if(keyword.equals("}")){
                keyword = FetchDataFromDataFile.getNextKeyword();
                continue;
            }

            if(!keywordReceived){
                keywordReceived = true;
                tableName = keyword;
            }
            else{
                keywordReceived = false;
                table = Table.fetchMyTable(keyword);
            }

            keyword = FetchDataFromDataFile.getNextKeyword();
        }

    }


}
