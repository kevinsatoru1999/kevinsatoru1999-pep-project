package DAO;

import Util.ConnectionUtil;
import Model.Account;
import Model.Message;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
     * TODO: a list containing all messages retrieved from the database.
     *
     * You only need to change the sql String and set preparedStatement parameters.
     *
     * @return all flights.
     */
public class AccountDao {
    //public static Connection connection = ConnectionUtil.getConnection();
    // Create Account
    public Account createAccount(Account account) {
        
        Connection connection = ConnectionUtil.getConnection();
        //Please refrain from using a 'try-with-resources' block when connecting to your database. 
        try {
            //Write SQL logic here
            String sql = "INSERT INTO Account (username, password) VALUES (?, ?)";
            PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            //write PreparedStatement setString and setInt methods here
            //statement.setInt(1, account.getAccount_id());
            statement.setString(1, account.getUsername());
            statement.setString(2, account.getPassword());
            statement.executeUpdate();
            ResultSet pkeyResultSet = statement.getGeneratedKeys();
            if(pkeyResultSet.next()) {
                int generated_account_id = (int) pkeyResultSet.getLong(1);
                return new Account(generated_account_id, account.getUsername(), account.getPassword());
            }
            //return account;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            
        }
        return null;
    }

    // Get Account by Username
    public Account getAccountByUsername(String username) {
        
        Connection connection = ConnectionUtil.getConnection();
        //Please refrain from using a 'try-with-resources' block when connecting to your database. 
        try {
            //Write SQL logic here
            String sql = "SELECT * FROM Account WHERE username = ?";
            PreparedStatement statement = connection.prepareStatement(sql);

            //write PreparedStatement setString and setInt methods here.
            statement.setString(1, username);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return new Account(resultSet.getInt("account_id"),
                        resultSet.getString("username"),
                        resultSet.getString("password"));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    // get Account By Id
    public Account getAccountById(int accountId) {
        
        Account account = null;
        Connection connection = ConnectionUtil.getConnection();

        //Please refrain from using a 'try-with-resources' block when connecting to your database. 
        try {
            String sql = "SELECT * FROM Account WHERE account_id = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, accountId);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                account = new Account(
                        resultSet.getInt("account_id"),
                        resultSet.getString("username"),
                        resultSet.getString("password")
                );
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return account;
    }
}
