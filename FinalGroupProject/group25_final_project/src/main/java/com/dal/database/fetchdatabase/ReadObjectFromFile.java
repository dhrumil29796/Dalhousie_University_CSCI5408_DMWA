package com.dal.database.fetchdatabase;

import com.dal.database.Login.RegisterUser;

import java.io.*;

public class ReadObjectFromFile {

    private static ReadObjectFromFile instance = null;
    private ReadObjectFromFile(){}

    public static ReadObjectFromFile getInstance(){
        if(instance == null){
            instance = new ReadObjectFromFile();
        }
        return instance;
    }

    public Object readObject(String path){
        FileInputStream fileInputStream = null;
        ObjectInputStream objectInputStream = null;
        Object readObject = null;
        try {
            File file = new File(path);
            if(!file.exists()){
                return null;
            }
            fileInputStream = new FileInputStream(path);
            objectInputStream = new ObjectInputStream(fileInputStream);
            readObject = objectInputStream.readObject();
            System.out.println("Reading data was successful");
        } catch (Exception e) {
            e.printStackTrace();
            /*RegisterUser registerUser = new RegisterUser();
            registerUser.registerNewUser("root", "root123");
            registerUser.registerNewUser("manu", "manu123");
            registerUser.writeToObjectFile();*/
        } finally {
            if(objectInputStream != null){
                try {
                    objectInputStream.close();
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }
            }
            if(fileInputStream != null){
                try {
                    fileInputStream.close();
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }
            }
        }

        return readObject;
    }


}
