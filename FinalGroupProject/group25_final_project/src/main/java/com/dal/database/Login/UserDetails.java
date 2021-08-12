package com.dal.database.Login;

import com.dal.database.fetchdatabase.FetchDataFromDataFile;

import java.io.Serializable;

public class UserDetails implements Serializable {

    private String userName = "";
    private String password = "";
    public static String space = AllUsers.space+"\t";

    public String getUserName() {
        return userName;
    }

    public UserDetails(String userName, String password) {
        this.userName = userName;
        this.password = password;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String toString(){
        String data = "User Name = " + getUserName() + "\n" + "Password = " + getPassword() + "\n";
        return data;
    }

    public String getMyUserDetails(){
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("{\n");

        stringBuilder.append(space+"userName : "+userName+ ",\n");
        stringBuilder.append(space+"Password : "+password+ ",\n");

        stringBuilder.append(space + "}");

        return stringBuilder.toString();
    }

    public static UserDetails fetchUserDetails(String keyword){
        String userName = null;
        String password = null;
        for(;;){
            if(keyword.equalsIgnoreCase("userName")){
                keyword = FetchDataFromDataFile.getNextKeyword();
                userName = keyword;
            }
            if(keyword.equalsIgnoreCase("password")){
                keyword = FetchDataFromDataFile.getNextKeyword();
                password = keyword;
            }
            if(keyword.equals("}") || keyword.equals("]")){
                UserDetails userDetails = new UserDetails(userName, password);
                return userDetails;
            }
            keyword = FetchDataFromDataFile.getNextKeyword();

        }

    }

}
