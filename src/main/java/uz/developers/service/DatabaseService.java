package uz.developers.service;

import org.postgresql.Driver;
import uz.developers.model.Result;
import uz.developers.model.User;

import java.sql.*;

public class DatabaseService {

    String url = "jdbc:postgresql://localhost:5432/computer_db";
    String dbUser = "postgres";
    String dbPassword = "1234";



    public Result registerUser(User user){

        try {
            Class.forName("org.postgresql.Driver");
            Connection connection = DriverManager.getConnection(url,dbUser,dbPassword);
            Statement statement = connection.createStatement();
            int count = 0;
            String checkPhoneNumberQuery = "select count(*) from users where phone_number='"+user.getPhoneNumber()+"'" ;
            ResultSet resultSetPhoneNumber = statement.executeQuery(checkPhoneNumberQuery);
            while (resultSetPhoneNumber.next()) {
                count = resultSetPhoneNumber.getInt(1);
            }
            if (count>0){
                return new Result("PhoneNumber already exist",false);
            }
            String checkUsernameQuery = "select count(*) from users where username='" +user.getUsername()+"'";
            ResultSet resultSetUsername = statement.executeQuery(checkUsernameQuery);
            while (resultSetUsername.next()) {
                count = resultSetUsername.getInt(1);
            }
            if (count>0){
                return new Result("Username already exist",false);
            }
            //userni saqlash

            String saveQuery = "insert into users(username,firstname,lastname,phone_number,password)"+
                    "values('"
                    +user.getUsername()+"','"
                    +user.getFirstname()+"','"
                    +user.getLastname()+"','"
                    +user.getPhoneNumber()+"','"
                    +user.getPassword()+"');";
            boolean execute = statement.execute(saveQuery);
            System.out.println(execute);
            return new Result("Successfully registered",true);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        return new Result("Error in server",false);
    }
}
