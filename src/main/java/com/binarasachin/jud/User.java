package com.binarasachin.jud;

//Basic Model for User Objects
public class User {
    private String username;
    private String password = "";   //Not a required field
    private String email;

    //Initiate without password
    public User(String inputUsername, String inputEmail){
        username = inputUsername;
        email = inputEmail;
    }

    //Initiate with password
    public User(String inputUsername, String inputPassword, String inputEmail){
        username = inputUsername;
        password = inputPassword;
        email = inputEmail;
    }


    public String getUsername(){
        return username;
    }

    public String getPassword(){
        return password;
    }

    public String getEmail(){
        return email;
    }

    //Print Object info
    public void printUserData(){
        System.out.println("\t" + username + "\t" + email);
    }
}