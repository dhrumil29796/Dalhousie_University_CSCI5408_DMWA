package com.dal.database.saveData;

import com.dal.database.utils.BasicFolderStructure;

import java.io.*;

public class WriteStringToFile {

    public WriteStringToFile() {
        try {
            BasicFolderStructure basicFolderStructure = new BasicFolderStructure();
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
    }

    public void writeString(String value, String path)  {
        FileWriter fileWriter = null;
        DeleteFile.deleteThisFile(path);
        try {
            fileWriter = new FileWriter(path);
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
            bufferedWriter.write(value);
            bufferedWriter.close();
            System.out.println("File Written Successfully!!!!");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }

    }

}
