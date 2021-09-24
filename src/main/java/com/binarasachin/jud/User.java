package com.binarasachin.jud;

public class User {
    private String username;
    private String password = "";
    private String email;

    public User(String inputUsername, String inputEmail){
        username = inputUsername;
        email = inputEmail;
    }

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

    public void printUserData(){
        System.out.println(username + "\t" + email);
    }
}