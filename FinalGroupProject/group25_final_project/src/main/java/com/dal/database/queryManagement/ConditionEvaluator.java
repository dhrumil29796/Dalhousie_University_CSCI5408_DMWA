package com.dal.database.queryManagement;

import com.dal.database.DataStorage.TableRowEntryStructure;

import java.util.ArrayList;
import java.util.List;

public class ConditionEvaluator {

    public ConditionEvaluator(){

    }

    public List<TableRowEntryStructure> evaluateCondition(List<TableRowEntryStructure> rows, List<String> condition){
        List<TableRowEntryStructure> newRows = new ArrayList<>();

        for(TableRowEntryStructure row : rows){
            if(row.Inputs.containsKey(condition.get(0))){
                String rowValue = row.Inputs.get(condition.get(0)).toString();
                String operator = (condition.get(1)).toUpperCase();
                switch (operator){
                    case "=" : {
                        if(rowValue.equals(condition.get(2))){
                            newRows.add(row);
                        }
                        break;
                    }
                    case "!=" : {
                        if(!rowValue.equals(condition.get(2))){
                            newRows.add(row);
                        }
                        break;
                    }
                }
            }
        }
        return newRows;
    }

}
