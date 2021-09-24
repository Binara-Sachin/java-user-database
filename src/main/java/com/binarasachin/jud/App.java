package com.binarasachin.jud;

import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

public class App {
    static DatabaseManager databaseManager = new DatabaseManager();
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
        try {
            List<User> userList = databaseManager.getAllUsers();

            System.out.println("\nCurrently Registered Users");
            for (User user : userList) {
                user.printUserData();
            }
        } catch (Exception e) {
            System.out.println("Error - Could not show registered users");
        }
    }

    private static void loginUser(){
        Scanner scanner = new Scanner(System.in);
        System.out.print("Username : ");
        String username  = scanner.nextLine();
        System.out.print("Password : ");
        String password  = scanner.nextLine();
        
        try {
            int loginResponse = databaseManager.loginUser(username, password);

            if (loginResponse == 200) {
                state = 1;
            }
        } catch (Exception e) {
            System.out.println("Error - Could not Log in");
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
        System.out.print("Confirm Password : ");
        String confirmPassword  = scanner.nextLine();
        System.out.print("Email : ");
        String email  = scanner.nextLine();

        if (validateData(username, password, confirmPassword, email)) {
            User newUser = new User(username, password, email);

            try {
                databaseManager.registerUser(newUser);
            } catch (Exception e) {
                System.out.println("Error - Could not register the new user");
            }
        }

        
    }

    private static boolean validateData(String username, String password, String confirmPassword, String email){
        if (username.isBlank()) {
            System.out.println("Error - Username cannot be blank");
            return false;
        }
        if (password.isBlank()) {
            System.out.println("Error - Password cannot be blank");
            return false;
        }
        if (password.length() < 6 || password.length() > 16) {
            System.out.println("Error - Password length should be between 6-16 characters");
            return false;
        }
        if (!password.equals(confirmPassword)) {
            System.out.println("Error - Passwords does not match");
            return false;
        }
        if (email.isBlank()) {
            System.out.println("Error - Email cannot be blank");
            return false;
        }
        return true;
    }


}
