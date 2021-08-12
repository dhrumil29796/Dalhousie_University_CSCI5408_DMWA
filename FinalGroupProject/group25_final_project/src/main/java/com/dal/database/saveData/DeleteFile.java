package com.dal.database.saveData;

import com.dal.database.CreateQueries.DeleteTableEntry;

import java.io.File;

public class DeleteFile {

    private DeleteFile(){}

    public static boolean deleteThisFile(String path){
        File file = new File(path);

        if(file.exists()){
            file.delete();
            return true;
        }
        return false;
    }
}
