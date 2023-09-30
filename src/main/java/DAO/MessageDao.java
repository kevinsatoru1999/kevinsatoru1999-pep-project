package DAO;

import Util.ConnectionUtil;
import Model.Message;
import Model.Account;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
     * TODO: a list containing all messages retrieved from the database.
     *
     * You only need to change the sql String and set preparedStatement parameters.
     *
     * @return all flights.
     */
public class MessageDao {
    /*public List<Message> getAllMessage(){
        Connection connection = ConnectionUtil.getConnection();
        List<Message> messages = new ArrayList<>();
        try {
            //Write SQL logic here
            String sql = "select * from message;";

            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet rs = preparedStatement.executeQuery();
            while(rs.next()){
                Message message = new Message();
                message.setMessage_id(rs.getInt("message_id"));
                message.setPosted_by(rs.getInt("posted_by"));
                message.setMessage_text(rs.getString("message_text"));
                message.setTime_posted_epoch(rs.getLong("time_posted_epoch"));
                messages.add(message);
            }
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
        return messages;
    } */

    // Create Message
    public Message createMessage(Message message) {
        
        Connection connection = ConnectionUtil.getConnection();
        //Please refrain from using a 'try-with-resources' block when connecting to your database. 
        try {
            String sql = "INSERT INTO Message (posted_by, message_text, time_posted_epoch) VALUES (?, ?, ?)";
            PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS); 
            statement.setInt(1, message.getPosted_by());
            statement.setString(2, message.getMessage_text());
            statement.setLong(3, message.getTime_posted_epoch());
            statement.executeUpdate();

            ResultSet resultSet = statement.getGeneratedKeys();
            if (resultSet.next()) {
                message.setMessage_id(resultSet.getInt(1));
            }
            return message;
        
    
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    // Get All Messages
    public List<Message> getAllMessages() {
        
        List<Message> messages = new ArrayList<>();
        Connection connection = ConnectionUtil.getConnection();
        //Please refrain from using a 'try-with-resources' block when connecting to your database. 
        try{

            //Statement statement = connection.createStatement();

            String sql = "SELECT * FROM Message";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet rs = preparedStatement.executeQuery();

            //Set resultSet = statement.executeQuery(sql);

            while (rs.next()) {
                Message message = new Message(rs.getInt("message_id"),
                        rs.getInt("posted_by"),
                        rs.getString("message_text"),
                        rs.getLong("time_posted_epoch"));
                messages.add(message);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return messages;
    }

    //Get One Message Given Message Id
    public Message getMessageById(int messageId) {
        
        //Message message = null;
        Connection connection = ConnectionUtil.getConnection();
        //Please refrain from using a 'try-with-resources' block when connecting to your database. 
        try {
            String sql = "SELECT * FROM Message WHERE message_id = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, messageId);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                Message message = new Message(
                        resultSet.getInt("message_id"),
                        resultSet.getInt("posted_by"),
                        resultSet.getString("message_text"),
                        resultSet.getLong("time_posted_epoch")
                );
                return message;
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return null;
    }

   //Update Message Given Message Id
    public void updateMessage(int messageId, Message messageText) {
        
        //Message updatedMessage = null;
        Connection connection = ConnectionUtil.getConnection();
        //Please refrain from using a 'try-with-resources' block when connecting to your database. 
        try{
            //Write SQL logic here
            String sql = "UPDATE Message SET message_text = ? WHERE message_id = ?;";

            //write PreparedStatement setString and setInt methods here.
            PreparedStatement statement = connection.prepareStatement(sql);
            //statement.setInt(1, messageText.getPosted_by());
            statement.setString(1, messageText.getMessage_text());
            //statement.setLong(2, messageText.getTime_posted_epoch());
            statement.setInt(2, messageId);
            statement.executeUpdate();

            //int rowsUpdated = statement.executeUpdate();
            /*if (rowsUpdated > 0) {
                updatedMessage = getMessageById(messageId);
            }*/

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        //return updatedMessage;
    }

    //Get All Messages From User Given Account Id
    public List<Message> getMessagesByAccountId(int accountId) {
        
        List<Message> messages = new ArrayList<>();
        Connection connection = ConnectionUtil.getConnection();
        //Please refrain from using a 'try-with-resources' block when connecting to your database. 
        try {
            //Write SQL logic here
            String sql = "SELECT * FROM Message WHERE posted_by = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            
            //write PreparedStatement setString and setInt methods here.
            statement.setInt(1, accountId);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                Message message = new Message(
                        resultSet.getInt("message_id"),
                        resultSet.getInt("posted_by"),
                        resultSet.getString("message_text"),
                        resultSet.getLong("time_posted_epoch")
                );
                messages.add(message);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return messages;
    }
    //
    //Delete a Message Given Message Id
    public Message deleteMessageById(int messageId) {
        
        Message deletedMessage = null;
        Connection connection = ConnectionUtil.getConnection();
        //Please refrain from using a 'try-with-resources' block when connecting to your database. 
        try{
            String selectSql = "SELECT * FROM Message WHERE message_id = ?";
            String deleteSql = "DELETE FROM Message WHERE message_id = ?";
            PreparedStatement selectStatement = connection.prepareStatement(selectSql);
            PreparedStatement deleteStatement = connection.prepareStatement(deleteSql);

            // Check if the message exists
            selectStatement.setInt(1, messageId);
            ResultSet resultSet = selectStatement.executeQuery();

            if (resultSet.next()) {
                // Get the message before deletion
                deletedMessage = new Message(
                        resultSet.getInt("message_id"),
                        resultSet.getInt("posted_by"),
                        resultSet.getString("message_text"),
                        resultSet.getLong("time_posted_epoch")
                );

                // Delete the message
                deleteStatement.setInt(1, messageId);
                deleteStatement.executeUpdate();
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return deletedMessage;
    }
    
}
