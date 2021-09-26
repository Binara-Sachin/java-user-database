package com.binarasachin.jud;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class DatabaseManager{
    //Database Credentials
    private String url = "jdbc:mysql://localhost:3306/user_data";
    private String username = "root";
    private String password = "12345";

    //MySQL Statement Strings
    private static final String QUARRY_ALL_USERS_STRING = "SELECT * FROM users";
    private static final String QUARRY_USER_BY_USERNAME_STRING = "SELECT passwrd FROM users WHERE username =?";
    private static final String REGISTER_USER_STRING = "INSERT INTO users (username, passwrd, email) VALUES (?, ?, ?)";

    //Make the connection
    private Connection getConnection() {
        try {
			Class.forName("com.mysql.cj.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}

        Connection connection = null;

        try {
            connection = DriverManager.getConnection(url, username, password);
        } catch (SQLException e) {
            printSQLException(e);
        }

        return connection;
    }

    //Quarry All Users from database
    public List<User> getAllUsers() {
        List<User> userList = new ArrayList<User>();

        try {
            Connection connection = getConnection();
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(QUARRY_ALL_USERS_STRING);

            while (resultSet.next()) {
                String username = resultSet.getString("username");
                String email = resultSet.getString("email");
                userList.add(new User(username, email));
            }

        } catch (SQLException e) {
            printSQLException(e);
        }
        return userList;
    }

    //Add new user to database
    public void registerUser(User user){
        try {
            Connection connection = getConnection(); 
            PreparedStatement statement = connection.prepareStatement(REGISTER_USER_STRING);

            statement.setString(1, user.getUsername());
            statement.setString(2, user.getPassword());
            statement.setString(3, user.getEmail());

            statement.executeUpdate();

            System.out.println("Successfully Created a New User");
        } catch (SQLException e) {
            printSQLException(e);
        }
    }

    //Login user
    public int loginUser(String username, String password){
        try {
            Connection connection = getConnection();

            PreparedStatement statement = connection.prepareStatement(QUARRY_USER_BY_USERNAME_STRING);
            statement.setString(1, username);
            ResultSet resultSet = statement.executeQuery();

            if (!resultSet.isBeforeFirst()) {
                System.out.println("Invalid username");
                return 201;
            } else {
                resultSet.next();
                String userPassword = resultSet.getString("passwrd");

                if (password.equals(userPassword)) {
                    System.out.println("Login Successful");
                    return 200;
                } else {
                    System.out.println("Invalid Password " + userPassword);
                    return 202;
                }
            }

        } catch (SQLException e) {
            printSQLException(e);
            return 209;
        } 
    }

    //Print Exceptions
    private void printSQLException(SQLException exception) {
        switch (exception.getErrorCode()) {
            case 1064:  //Check 'Statement Strings' for SQL syntax errors
                System.out.println("SQL Code Error - Wrong syntax");
                break;
            case 1054:  //Database columns should be 'username', 'passwrd', 'email'
                System.out.println("SQL Code Error - Requested column does not exist");
                break;
            case 1146:  //Table name should be 'Users'
                System.out.println("SQL Code Error - Requested table does not exist");
                break;
            case 1062:  //Username and email are set to be unique fields in SQL table.
                if (exception.getMessage().contains("users.username")) {
                    System.out.println("Username already exists. Please select a different username");
                }
                else if (exception.getMessage().contains("users.email")) {
                    System.out.println("Email already exists. Use another Email or use Login");
                }
                break;
            default:    //Other error messages
                for (Throwable e: exception) {
                    if (e instanceof SQLException) {
                        // e.printStackTrace(System.err);
                        System.out.println("Something went wrong. Please Try Again");
                        System.err.println("SQLState: " + ((SQLException) e).getSQLState());
                        System.err.println("Error Code: " + ((SQLException) e).getErrorCode());
                        System.err.println("Message: " + e.getMessage());
                        Throwable t = exception.getCause();
                        while (t != null) {
                            System.out.println("Cause: " + t);
                            t = t.getCause();
                        }
                    }
                }
                break;
        }
    }
}