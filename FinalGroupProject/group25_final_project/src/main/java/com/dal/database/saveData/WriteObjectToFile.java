package com.dal.database.saveData;

import com.dal.database.utils.BasicFolderStructure;

import java.io.*;


public class WriteObjectToFile {

    public WriteObjectToFile() {
        try {
            BasicFolderStructure basicFolderStructure = new BasicFolderStructure();
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
    }

    public void writeObject(Object object, String path)  {
        FileOutputStream fileOutputStream = null;
        DeleteFile.deleteThisFile(path);
        try {
            fileOutputStream = new FileOutputStream(path);
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
            objectOutputStream.writeObject(object);
            objectOutputStream.close();
            //System.out.println("Object written successfully!!!!");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }

    }



}
