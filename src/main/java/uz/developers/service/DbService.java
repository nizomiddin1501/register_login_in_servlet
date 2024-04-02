package uz.developers.service;

import uz.developers.model.Result;
import uz.developers.model.User;

import java.sql.*;

public class DbService {


    String url = "jdbc:postgresql://localhost:5432/computer_db";
    String dbUser = "postgres";
    String dbPassword = "1234";


    public Result registerUser(User user) {

        try {
            Class.forName("org.postgresql.Driver");
            Connection connection = DriverManager.getConnection(url, dbUser, dbPassword);
            int count = 0;
            String checkPhoneNumberQuery = "select count(*) from users where phone_number='" + user.getPhoneNumber() + "'";
            PreparedStatement preparedStatement = connection.prepareStatement(checkPhoneNumberQuery);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                count = resultSet.getInt(1);
            }
            if (count > 0) {
                return new Result("PhoneNumber already exist", false);
            }

            String checkUsernameQuery = "select count(*) from users where username='" + user.getUsername() + "'";
            //ResultSet resultSet1 = statement.executeQuery(checkUsernameQuery);
            ResultSet resultSet1 = preparedStatement.executeQuery();
            while (resultSet1.next()) {
                count = resultSet1.getInt(1);
            }
            if (count > 0) {
                return new Result("Username already exist", false);
            }


            String queryPost = "insert into users(firstname,lastname,username,phone_number,password)values(?,?,?,?,?)";
            preparedStatement = connection.prepareStatement(queryPost);
            preparedStatement.setString(1, user.getFirstname());
            preparedStatement.setString(2, user.getLastname());
            preparedStatement.setString(3, user.getUsername());
            preparedStatement.setString(4, user.getPhoneNumber());
            preparedStatement.setString(5, user.getPassword());
            boolean execute = preparedStatement.execute();
            System.out.println(execute);
            return new Result("Successfully registered", true);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return new Result("Error in server", false);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }


    public User login(String username, String password) {
        try {
            Class.forName("org.postgresql.Driver");
            Connection connection = DriverManager.getConnection(url, dbUser, dbPassword);
            String query = "select * from users where username=? and password=?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, password);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                int id = resultSet.getInt(1);
                username = resultSet.getString(2);
                String firstname = resultSet.getString(3);
                String lastname = resultSet.getString(4);
                String phoneNumber = resultSet.getString(5);
                User user = new User(
                        id,
                        firstname,
                        lastname,
                        phoneNumber,
                        username);
                return user;
            }
            return null;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        return null;
    }


    public User loadUserByCookies(String username) {
        try {
            Class.forName("org.postgresql.Driver");
            Connection connection = DriverManager.getConnection(url, dbUser, dbPassword);
            String query = "select * from users where username=?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, username);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                int id = resultSet.getInt(1);
                username = resultSet.getString(2);
                String firstname = resultSet.getString(3);
                String lastname = resultSet.getString(4);
                String phoneNumber = resultSet.getString(5);
                User user = new User(
                        id,
                        firstname,
                        lastname,
                        phoneNumber,
                        username);
                return user;
            }
            return null;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        return null;
    }



}
