package Service;
import Model.Message;

import java.util.List;

import DAO.MessageDAO;

public class MessageService {
    MessageDAO messageDAO;

     /**
     * Constructor for a message Service
     * Initialize the messageDAO
     */
    public MessageService(){
        messageDAO = new MessageDAO();
    }

    /**
     * Use the DAO to add a new message to the database.
     */
    public Message createMessage(Message msg){
        return messageDAO.insertMessage(msg);
    }

    /**
     * Use the DAO to get a message from the database by its message_id.
     */
    public Message getMessageById(int id){
        return messageDAO.getMessageByID(id);
    }

    /**
     * Use the DAO to get all of the messages from the database.
     */
    public List<Message> getAllMessages(){
        return messageDAO.getAllMessages();
    }

    /**
     * Use the DAO to delete a message from the database by its message_id.
     */
    public void deleteMessageById(int id){
       messageDAO.deleteMessage(id);
    }

    /**
     * Use the DAO to update a message from the database by its message_id.
     * @param msg
     * @param id
     */
    public void updateMessage(int id, Message msg){
           messageDAO.updateMessage(id, msg);
    }

    /**
     * Use the DAO to get all the message created by a specific user.
     * @param id - user id
     */
    public List<Message> getAllMessagesFromUser(int id){
        return messageDAO.getAllMessagesFromUser(id);
    }
 

}