package com.dal.database.Login;

import com.dal.database.saveData.WriteObjectToFile;
import com.dal.database.saveData.WriteStringToFile;
import com.dal.database.utils.Application;

public class RegisterUser {

    AllUsers allUsers = null;
    public RegisterUser() {
        allUsers = AllUsers.getInstance();
    }

    public boolean registerNewUser(String userName, String password){
        UserDetails userDetails = new UserDetails(userName, password);
        allUsers.addUser(userDetails);
        return true;
    }


    public boolean writeUsersToFile(){
        WriteStringToFile writeStringToFile = new WriteStringToFile();
        writeStringToFile.writeString(allUsers.getMyAllUsers(), "./FilesOfData/LoginUsersData/AllUsers");

        return writeToFile();
    }

    public boolean writeToFile(){
        WriteObjectToFile writeObjectToFile = new WriteObjectToFile();
        writeObjectToFile.writeObject(allUsers,Application.pathOfUsers );
        return true;
    }
}
