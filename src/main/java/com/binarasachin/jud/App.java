package com.binarasachin.jud;

import java.sql.SQLException;
import java.util.List;

public class App {
    public static void main( String[] args ) throws SQLException {
        DatabaseManager databaseManager = new DatabaseManager();
        List<User> userList = databaseManager.getAllUsers();
        printUserList(userList);
    }

    private static void printUserList(List<User> userList){
        for (User user : userList) {
            user.printUserData();
        }
    }
}
