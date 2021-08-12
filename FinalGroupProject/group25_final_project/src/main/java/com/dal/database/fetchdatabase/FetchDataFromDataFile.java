package com.dal.database.fetchdatabase;

import com.dal.database.DataStorage.AllDatabases;
import com.dal.database.queryManagement.SplitQuery;

import java.io.*;
import java.util.List;

public class FetchDataFromDataFile {

    public static List<String> tokens = null;

    public static List<String> fetchDataFromFile(String path){
        FileReader fileReader = null;
        try {
            File file = new File(path);
            if(!file.exists()){
                return null;
            }
            fileReader = new FileReader(path);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        BufferedReader bufferedReader = new BufferedReader(fileReader);
        StringBuffer stringBuffer = new StringBuffer();
        String val = null;
        while(true){
            try {
                if (!((val = bufferedReader.readLine()) != null)) break;
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
            stringBuffer.append(val);
        }
        String fileData = stringBuffer.toString();
        SplitQuery splitQuery = new SplitQuery(fileData);
        List<String> tokens = splitQuery.splitQueryTokens();
        //splitQuery.printTokens(tokens);
        FetchDataFromDataFile.tokens = tokens;
        return tokens;
    }

    public static List<String> getSubTokensList(List<String> tokens, int startIndex, int endIndex){
        if(tokens == null || startIndex<0 || endIndex<0 || startIndex>=endIndex || endIndex > tokens.size()){
            return null;
        }
        return tokens.subList(startIndex, endIndex);
    }
    public static List<String> getNextTokenList(List<String> tokens){
        if(tokens == null || tokens.size() == 1){
            return null;
        }
        return tokens.subList(1, tokens.size());
    }

    public static int getIndexFromFront(List<String> tokens, String value){
        if(tokens == null){
            return -1;
        }
        int index = 0;

        for( index = 0; index < tokens.size(); index++){
            if(tokens.get(index).equalsIgnoreCase(value)){
                return index;
            }
        }
        return -1;
    }

    public static int getIndexFromReverse(List<String> tokens, String value){
        if(tokens == null){
            return -1;
        }
        int index = 0;

        for( index = tokens.size()-1; index >= 0; index--){
            if(tokens.get(index).equalsIgnoreCase(value)){
                return index;
            }
        }
        return -1;
    }

    public static List<String> getNextTokenList(){
        return getNextTokenList(FetchDataFromDataFile.tokens);
    }

    public static boolean isKeyword(String valToCheck){
        String compareVal = "{[,:'";
        if(FetchDataFromDataFile.tokens == null || compareVal.contains(valToCheck)){
            return false;
        }
        return true;
    }

    public static String getNextKeyword(){
        for(;;){
            String keyword = getFirstToken();
            if(keyword == null){
                return null;
            }
            FetchDataFromDataFile.tokens = FetchDataFromDataFile.getNextTokenList();
            if(isKeyword(keyword)){
                return keyword;
            }
        }
    }
    private static String getFirstToken(){
        if(tokens == null || tokens.size()<=0){
            return null;
        }
        return FetchDataFromDataFile.tokens.get(0);
    }
}
