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

    //Statement Strings
    private static final String QUARRY_ALL_USERS_STRING = "SELECT * FROM users";
    private static final String QUARRY_USER_BY_USERNAME_STRING = "SELECT passwrd FROM users WHERE username =?";
    private static final String REGISTER_USER_STRING = "INSERT INTO users (username, passwrd, email) VALUES (?, ?, ?)";

    protected Connection getConnection() {
        try {
			Class.forName("com.mysql.cj.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}

        Connection connection = null;

        try {
            connection = DriverManager.getConnection(url, username, password);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return connection;
    }

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

    private void printSQLException(SQLException ex) {
        for (Throwable e: ex) {
            if (e instanceof SQLException) {
                e.printStackTrace(System.err);
                System.err.println("SQLState: " + ((SQLException) e).getSQLState());
                System.err.println("Error Code: " + ((SQLException) e).getErrorCode());
                System.err.println("Message: " + e.getMessage());
                Throwable t = ex.getCause();
                while (t != null) {
                    System.out.println("Cause: " + t);
                    t = t.getCause();
                }
            }
        }
    }
}