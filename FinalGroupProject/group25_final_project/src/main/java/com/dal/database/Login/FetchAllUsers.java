package com.dal.database.Login;

/*
import com.dal.database.fetchdatabase.ReadObjectFromFile;
import com.dal.database.utils.Application;
*/

public class FetchAllUsers {

    private AllUsers allUsers = null;

    private static FetchAllUsers fetchAllUsers = null;

    public static FetchAllUsers getInstance() {
        if(fetchAllUsers == null){
            fetchAllUsers = new FetchAllUsers();
        }

        return fetchAllUsers;
    }

    private FetchAllUsers() {

        //Object object = ReadObjectFromFile.getInstance().readObject(Application.pathOfUsers);
        this.allUsers = AllUsers.getInstance();
    }

    public AllUsers getAllUsers(){
        return this.allUsers;
    }

}
