package com.dal.database.DataStorage;

import com.dal.database.fetchdatabase.FetchDataFromDataFile;

import java.io.Serializable;
import java.util.*;

public class Table implements Serializable {

    public Map<String, String> columnNamesAndInputType;
    public List<TableRowEntryStructure> rows;
    public String tableName = null;
    public String primaryKey = null;
    public String foreignKey = null;
    public static String space = Database.space+"\t\t";

    public Table() {
        columnNamesAndInputType = new LinkedHashMap<>();
        rows = new ArrayList<>();
    }

    public Table duplicateTable(){
        Table dup = new Table();
        dup.columnNamesAndInputType = this.columnNamesAndInputType;
        dup.rows = new ArrayList<>(this.rows);
        dup.tableName = this.tableName;
        return dup;
    }

    public String getMyTableData(){
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("{\n");

        stringBuilder.append(space+"\t"+ "PrimaryKey"+" : " + primaryKey + "\n");
        stringBuilder.append(space+"\t"+ "ForeignKey"+" : " + foreignKey + "\n");

        stringBuilder.append(space+"\tColumnStructure"+" : [\n");
        boolean first = true;

        for(Map.Entry<String, String> entry : columnNamesAndInputType.entrySet()){
            if(!first){
                stringBuilder.append(" , \n");
            }
            else{
                first = false;
            }
            stringBuilder.append(space+"\t\t"+entry.getKey()+" : '"+ entry.getValue()+"'");
        }
        stringBuilder.append("\n");
        stringBuilder.append(space + "\t\t"+"]\n");

        stringBuilder.append(space+"\t"+ tableName+" : [\n");

        first = true;
        for(TableRowEntryStructure row : rows){
            if(!first){
                stringBuilder.append(" , \n");
            }
            else{
                first = false;
            }
            stringBuilder.append(space+"\t"+row.getMyRowData());
        }
        stringBuilder.append("\n");
        stringBuilder.append(space + "\t\t"+"]\n");

        stringBuilder.append(space+"}");

        return stringBuilder.toString();
    }

    public static Table fetchMyTable(String keyword){
        Table table = new Table();
        boolean keywordReceived = false;

        for(;;) {
            if (keyword == null) {
                return table;
            }
            if(keyword.equals("]")){
                return table;
            }
            if(keyword.equals("}")){
                keyword = FetchDataFromDataFile.getNextKeyword();
                continue;
            }
            if (keyword.equalsIgnoreCase("PrimaryKey")) {
                table.primaryKey = FetchDataFromDataFile.getNextKeyword();
                if (table.primaryKey.equalsIgnoreCase("NULL")) {
                    table.primaryKey = null;
                }
                keyword = FetchDataFromDataFile.getNextKeyword();
                continue;
            }
            else if (keyword.equalsIgnoreCase("ForeignKey")) {
                table.foreignKey = FetchDataFromDataFile.getNextKeyword();
                if (table.foreignKey.equalsIgnoreCase("NULL")) {
                    table.foreignKey = null;
                }
                keyword = FetchDataFromDataFile.getNextKeyword();
                continue;
            }
            else if(keyword.equalsIgnoreCase("ColumnStructure")){
                fetchColumnStructure(keyword, table);
                keyword = FetchDataFromDataFile.getNextKeyword();
                continue;
            }
            if(!keywordReceived){
                table.tableName = keyword;
                keywordReceived = !keywordReceived;
                keyword = FetchDataFromDataFile.getNextKeyword();
                continue;
            }
            else{
                //keywordReceived = !keywordReceived;
                TableRowEntryStructure row = TableRowEntryStructure.fetchMyRow(keyword);
                table.rows.add(row);
            }

            keyword = FetchDataFromDataFile.getNextKeyword();
        }
    }

    public static String fetchColumnStructure(String keyword, Table table){
        String key = null;
        String value = null;
        boolean entryReceived = false;
        keyword = FetchDataFromDataFile.getNextKeyword();

        for(;;){
            if(key != null && value != null){
                if(value.equalsIgnoreCase("null")){
                    value = null;
                }
                table.columnNamesAndInputType.put(key, value);
                key = null;
                value = null;
            }
            if (FetchDataFromDataFile.tokens == null || keyword == null) {
                return keyword;
            }
            if(keyword.equals("]") || keyword.equals("}") ){
                return keyword;
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
