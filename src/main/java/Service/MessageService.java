package Service;

import DAO.MessageDao;
import Model.Message;

import java.util.List;

public class MessageService {
    MessageDao messageDao;

    public MessageService() {
        this.messageDao = new MessageDao();
    }
    //private final MessageDao messageDao = new MessageDao();

    // Create Message
    public Message createMessage(int posted_by, String message_text, long time_posted_epoch) {
        //blank and user carrying too many character
        
        Message message = new Message(posted_by, message_text, time_posted_epoch);
        if(message_text == null || message_text.equals("") || message_text.length() > 254){
            return null;
        } else
        return messageDao.createMessage(message);
    }

    // Get All Messages
    public List<Message> getAllMessages() {
        return messageDao.getAllMessages();
    }

    // Add more methods as needed
    // Get Message by ID
    public Message getMessageById(int messageId) {
        return messageDao.getMessageById(messageId);
    }

    // Update Message
    public Message updateMessage(int messageId, Message message) {
        //Message message = new Message(posted_by, message_text, time_posted_epoch);
        //return messageDao.updateMessage(messageId, message);
        Message existingMessage = messageDao.getMessageById(messageId);

        //if the message is already exist update the message
        if(existingMessage != null) {
            messageDao.updateMessage(messageId, message);
            return messageDao.getMessageById(messageId);
        } else {
            return null;
        }
    }

    // Get Messages by Account ID
    public List<Message> getMessagesByAccountId(int accountId) {
        return messageDao.getMessagesByAccountId(accountId);
    }

    // Delete Message by Account ID
    public Message deleteMessageById(int messageId) {
        return messageDao.deleteMessageById(messageId);
    }

}

