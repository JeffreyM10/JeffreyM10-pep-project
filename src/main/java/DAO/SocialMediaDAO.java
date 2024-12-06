
package DAO;
import Model.Account;
import Util.ConnectionUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class SocialMediaDAO{

    /**
     * Retrieve all accounts in the "account" table 
     * Need to add a SQL Query and Prepared statement parameters. 
     * @return all accounts
     */
    public List<Account> getAllAccounts(){
        Connection connection = ConnectionUtil.getConnection();
        List<Account> accounts = new ArrayList<>();
        try {
            //SQL logic to get all of the accounts within the table
            String sql = "SELECT * FROM account";  // SELECT * FROM account 
            

            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet rs = preparedStatement.executeQuery();
            while(rs.next()){
                Account account = new Account(rs.getInt("account_id"), rs.getString("username"),
                        rs.getString("password"));
               accounts.add(account);
            }

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return accounts;
    }

    /**
     * Retrieve a specific account in the "account" table 
     * Need to add a SQL Query and Prepared statement parameters. 
     * @param userName - name of the user from the "account" table
     */
    public Account getAccountByUsername (String userName){
        Connection connection = ConnectionUtil.getConnection();
        try {
            //SQL logic to get an account off of the account table
            String sql = "SELECT * FROM account WHERE username = ?";

            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            //write preparedStatements setString and setInt methods here
            preparedStatement.setString(1, userName);

            ResultSet rs = preparedStatement.executeQuery();

            while(rs.next()){
                Account account = new Account(rs.getInt("account_id"), rs.getString("username"),
                        rs.getString("password"));
               return account;
            }

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return null; 
    }

        /**
     * Retrieve a specific account_id in the "account" table 
     * Need to add a SQL Query and Prepared statement parameters. 
     * @param id - id of the user from the "account" table
     */
    public Account getAccountByUserID (int id){
        Connection connection = ConnectionUtil.getConnection();
        try {
            //SQL logic to get an account off of the account table
            String sql = "SELECT * FROM account WHERE account_id = ?";

            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            //write preparedStatements setString and setInt methods here
            preparedStatement.setInt(1, id);

            ResultSet rs = preparedStatement.executeQuery();

            while(rs.next()){
                Account account = new Account(rs.getInt("account_id"), rs.getString("username"),
                        rs.getString("password"));
               return account;
            }

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return null; 
    }

       /**
     * Retrieve a specific account in the "account" table using their username
     * Need to add a SQL Query and Prepared statement parameters. 
     * @param userName - name of the user from the "account" table
     */
    public boolean checkUser (String userName){
        Connection connection = ConnectionUtil.getConnection();
        try {
            //SQL logic to get an account off of the account table
            String sql = "SELECT * FROM account WHERE username = ?";

            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            //write preparedStatements setString and setInt methods here
            preparedStatement.setString(1, userName);

            ResultSet rs = preparedStatement.executeQuery();

            while(rs.next()){
               return true;
            }

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return false; 
    }

    /**
     * Add an account using the parameters given into the "account" table 
     * Need to add a SQL Query and Prepared statement parameters. 
     * @param account an object modelling an Account.
     */
    public Account insertAccount(Account account){
        Connection connection = ConnectionUtil.getConnection();
        try {
            //SQL logic to insert into the account table
            String sql = "INSERT INTO account (username, password) VALUES (?, ?);";
            PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            //write preparedStatements setString and setInt methods here
            preparedStatement.setString(1,account.getUsername());
            preparedStatement.setString(2,account.getPassword());

            preparedStatement.executeUpdate();
            ResultSet pkeyResultSet = preparedStatement.getGeneratedKeys();

            if(pkeyResultSet.next()){
                int generated_account_id = (int) pkeyResultSet.getLong(1);
               return new Account(generated_account_id,account.getUsername(), account.getPassword());
            }

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return null;
    }
    
}