package com.dal.database.queryManagement;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SplitQuery {

    String query = "";

    public SplitQuery(String query){
        this.query = query;
    }

    public List<String> splitQueryTokens(){

        String regex = "[^\\s\"']+|\"[^\"]*\"|'[^']*'";
        List<String> tokens = new ArrayList<>();

        Pattern pattern =  Pattern.compile(regex);
        Matcher matcher = pattern.matcher(query);

        while(matcher.find()){
            String token = matcher.group();
            tokens.add(token);
        }

        //System.out.println(tokens);
        tokens = refactorNullValues(tokens);
        //System.out.println(tokens);
        tokens = refactorCommas(tokens);
        //System.out.println(tokens);

        //printTokens(tokens);

        return tokens;

    }

    public void printTokens(List<String> tokens){
        int counter = 1;
        for(String token : tokens) {
            System.out.println((counter++)+" \t\t\"" + token+"\"");
        }
    }

    private List<String> refactorNullValues(List<String> tokens){
        List<String> processed = new ArrayList<>();

        for(String token : tokens){
            if(!token.equals("") && token.length()>0) {
                token = token.trim();
                if(!token.equals("") && token.length()>0){
                    processed.add(token);
                }
            }
        }

        return processed;
    }

    private List<String> refactorCommas(List<String> tokens){
        List<String> processed = new ArrayList<>();

        for(String token : tokens){
            processed.addAll(removeSpecialCharacter(token));
        }
        return processed;
    }

    private List<String> removeSpecialCharacter(String token){

        List<String> processed = new ArrayList<>();

        if(token.length()<=1){
            processed.add(token);
            return processed;
        }

        int startIndex = 0;
        int lastIndex = token.length()-1;

        if(token.charAt(startIndex)==',' || token.charAt(startIndex) == '(' || token.charAt(startIndex) == ')' || token.charAt(startIndex) == '\''){
            processed.add(Character.toString(token.charAt(startIndex)));
            startIndex = 1;
        }
        if(token.charAt(lastIndex)==',' || token.charAt(lastIndex) == '(' || token.charAt(lastIndex) == ')' || token.charAt(lastIndex) == '\'' || token.charAt(lastIndex) == ';'){
            processed.addAll(removeSpecialCharacter(token.substring(startIndex, lastIndex)));
            processed.add(Character.toString(token.charAt(lastIndex)));
        }
        else{
            processed.add(token.substring(startIndex, lastIndex+1));
        }

        return processed;
    }





}
