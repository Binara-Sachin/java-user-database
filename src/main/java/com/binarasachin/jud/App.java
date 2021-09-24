package com.binarasachin.jud;

import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

public class App {
    final static DatabaseManager databaseManager = new DatabaseManager();
    static int state = 0;

    public static void main( String[] args ) throws SQLException {
        Scanner scanner = new Scanner(System.in);

        Loop : while (true) {
            switch (state) {
                case 0:
                    System.out.println("\nSelect a Task \n\t1.\tLogin \n\t2.\tRegister \n\t3.\tExit");
                    System.out.print("Response> ");
                    String input0  = scanner.nextLine();

                    switch (input0) {
                        case "1":
                            loginUser();
                            break;
                        case "2":
                            registerUser();
                            break;
                        case "3":
                            break Loop;
                        default:
                            System.out.println("Invalid Input");
                            break;
                    }
                    break;
                case 1:
                    System.out.println("\nSelect a Task \n\t1.\tView users \n\t2.\tLogout \n\t3.\tExit");
                    System.out.print("Response> ");
                    String input1  = scanner.nextLine();

                    switch (input1) {
                        case "1":
                            printUserList();
                            break;
                        case "2":
                            logoutUser();
                            break;
                        case "3":
                            break Loop;
                        default:
                            System.out.println("Invalid Input");
                            break;
                    }
                    break;
                default:
                    break;
            }
        }
    }

    private static void printUserList(){
        List<User> userList = databaseManager.getAllUsers();

        System.out.println("\nCurrently Registered Users");
        for (User user : userList) {
            user.printUserData();
        }
    }

    private static void loginUser(){
        Scanner scanner = new Scanner(System.in);
        System.out.print("Username : ");
        String username  = scanner.nextLine();
        System.out.print("Password : ");
        String password  = scanner.nextLine();
        
        int loginResponse = databaseManager.loginUser(username, password);

        if (loginResponse == 200) {
            state = 1;
        }
    }

    private static void logoutUser(){
        state = 0;
        System.out.print("Successfully Logged Out");
    }

    private static void registerUser(){
        Scanner scanner = new Scanner(System.in);
        System.out.print("Username : ");
        String username  = scanner.nextLine();
        System.out.print("Password : ");
        String password  = scanner.nextLine();
        System.out.print("Email : ");
        String email  = scanner.nextLine();

        User newUser = new User(username, password, email);

        databaseManager.registerUser(newUser);


    }


}
