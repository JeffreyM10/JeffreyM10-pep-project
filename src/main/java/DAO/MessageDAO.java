package DAO;
import Model.Message;
import Util.ConnectionUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class MessageDAO{

    public Message insertMessage(Message msg){
        Connection connection = ConnectionUtil.getConnection();
        try {
            //SQL logic to insert into the message table
            String sql = "INSERT INTO message (posted_by, message_text, time_posted_epoch) VALUES (?, ?, ?);";
            PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            //write preparedStatements setString and setInt methods here
            preparedStatement.setInt(1, msg.getPosted_by());
            preparedStatement.setString(2, msg.getMessage_text());
            preparedStatement.setLong(3, msg.getTime_posted_epoch());

            preparedStatement.executeUpdate();
            ResultSet pkeyResultSet = preparedStatement.getGeneratedKeys();

            if(pkeyResultSet.next()){
                int generated_message_id = (int) pkeyResultSet.getLong(1);
               return new Message(generated_message_id,msg.getPosted_by(), msg.getMessage_text(), msg.getTime_posted_epoch());
            }

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    /**
     * Retrieve a specific message in the "message" table 
     * Need to add a SQL Query and Prepared statement parameters. 
     * @param id - message id
     */
    public Message getMessageByID (int id){
        Connection connection = ConnectionUtil.getConnection();
        try {
            //SQL logic to get an message off of the message table
            String sql = "SELECT * FROM message WHERE message_id = ?";

            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            //write preparedStatements setString and setInt methods here
            preparedStatement.setInt(1, id);

            ResultSet rs = preparedStatement.executeQuery();

            while(rs.next()){
                Message msg = new Message(rs.getInt("message_id"), rs.getInt("posted_by"), rs.getString("message_text"),
                        rs.getLong("time_posted_epoch"));
               return msg;
            }

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return null; 
    }

        /**
     * Retrieve all messages in the "account" table 
     * Need to add a SQL Query and Prepared statement parameters. 
     * @return messages
     */
    public List<Message> getAllMessages(){
        Connection connection = ConnectionUtil.getConnection();
        List<Message> messages = new ArrayList<>();
        try {
            //SQL logic to get all of the messages within the table
            String sql = "SELECT * FROM message";  // SELECT * FROM account 
            

            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet rs = preparedStatement.executeQuery();
            while(rs.next()){
                Message msg = new Message(rs.getInt("message_id"), rs.getInt("posted_by"), rs.getString("message_text"),
                        rs.getLong("time_posted_epoch"));
               messages.add(msg);
            }

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return messages;
    }

    /**
     * Delete a specific message in the "message" table
     * @param id - message_id
     * @return null
     */
    public void deleteMessage(int id){
        Connection connection = ConnectionUtil.getConnection();
        try {
            //SQL logic to delete the from the message table
            String sql = "DELETE FROM message WHERE message_id = ?;";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            //write preparedStatements setString and setInt methods here
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Update a specific message in the "message" table
     */
    public void updateMessage (int id, Message msg){
        Connection connection = ConnectionUtil.getConnection();
        try {
            //Write SQL logic to update the message_text
            String sql = "UPDATE message SET message_text = ? WHERE message_id = ?;";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            //write PreparedStatement setString and setInt methods here.
            preparedStatement.setString(1, msg.getMessage_text());
            preparedStatement.setInt(2, id);

            preparedStatement.executeUpdate();
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
    }

    /**
     * 
     * @return
     */
    public List<Message> getAllMessagesFromUser(int id){
        Connection connection = ConnectionUtil.getConnection();
        List<Message> messages = new ArrayList<>();
        try {
            //SQL logic to get all of the messages within the table from a user
            String sql = "SELECT * FROM message WHERE posted_by = ?";  
            
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1,id);
            ResultSet rs = preparedStatement.executeQuery();

            while(rs.next()){
                Message msg = new Message(rs.getInt("message_id"), rs.getInt("posted_by"), rs.getString("message_text"),
                        rs.getLong("time_posted_epoch"));
               messages.add(msg);
            }

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return messages;
    }


    
}