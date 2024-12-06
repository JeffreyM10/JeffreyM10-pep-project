package Controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import Model.Account;
import Model.Message;
import Service.MessageService;
import Service.SocialMediaService;
import io.javalin.Javalin;
import io.javalin.http.Context;

/**
 * TODO: You will need to write your own endpoints and handlers for your controller. The endpoints you will need can be
 * found in readme.md as well as the test cases. You should
 * refer to prior mini-project labs and lecture materials for guidance on how a controller may be built.
 */
public class SocialMediaController {
    SocialMediaService socialMediaService;
    MessageService messageService;

    public SocialMediaController(){
        socialMediaService = new SocialMediaService();
        messageService = new MessageService();
        
    }
    /**
     * In order for the test cases to work, you will need to write the endpoints in the startAPI() method, as the test
     * suite must receive a Javalin object from this method.
     * @return a Javalin app object which defines the behavior of the Javalin controller.
     */
    public Javalin startAPI() {
        Javalin app = Javalin.create();
        //app.get("example-endpoint", this::exampleHandler);
        app.post("register", this:: postAccountHandler); // postAccountHandler
        app.post("login", this:: loginAccountHandler); // loginAccountHandler
        app.post("messages", this:: createMessageHandler); // createMessageHandler
        app.get("messages", this:: retrieveAllMessagesHandler); // retrieveAllMessagesHandler
        app.get("/messages/{message_id}", this:: retrieveMessageHandler);  //retrieveMessageHandler
        app.delete("/messages/{message_id}", this:: deleteMessageHandler);  //deleteMessageHandler
        app.patch("/messages/{message_id}", this:: patchMessageHandler);    //patchMessageHandler
        app.get("/accounts/{account_id}/messages", this:: retrieveAllMessagesFromUserHandler);  //retrieveAllMessagesFromUserHandler
        return app;
    }


    /**
     * As a user, I should be able to create a new Account on the endpoint POST localhost:8080/register. The body will contain a representation of a JSON Account, but will not contain an account_id.
     * The registration will be successful if and only if the username is not blank, the password is at least 4 characters long, and an Account with that username does not already exist. 
     * If all these conditions are met, the response body should contain a JSON of the Account, including its account_id. The response status should be 200 OK, which is the default. 
     * The new account should be persisted to the database.
     * If the registration is not successful, the response status should be 400. (Client error)
     * @throws JsonProcessingException
     */

    private void postAccountHandler (Context ctx) throws JsonProcessingException{

        ObjectMapper mapper = new ObjectMapper();
        Account acc = mapper.readValue(ctx.body(), Account.class);
        
        // if and only if the username is not blank, the password is at least 4 characters long, and an Account with that usernmae does not already exist.
        if(!acc.getUsername().isBlank() && acc.getPassword().length() >= 4 && socialMediaService.checkUser(acc.getUsername()) == false){
            ctx.status(200);                                                        
            ctx.json(socialMediaService.addAccount(acc));       //returning the newly added account in the position in the table 
        }
        else{
            ctx.status(400);
        }
    }

    /**
     * The login will be successful if and only if the username and password provided in the request body JSON match a real account existing on the database.
     *  If successful, the response body should contain a JSON of the account in the response body, including its account_id.
     *  The response status should be 200 OK, which is the default.
     *  If the login is not successful, the response status should be 401. (Unauthorized
     * @param context The Javalin Context object manages information about both the HTTP request and response.
     */
    private void loginAccountHandler (Context ctx) throws JsonProcessingException{
        ObjectMapper mapper = new ObjectMapper();
        Account acc = mapper.readValue(ctx.body(), Account.class);
        
        //If and only if the username and password provided in the request body JSON match a real account existing on the database
        if(socialMediaService.checkUser(acc.getUsername()) == true){ //check if there is that user
            if(socialMediaService.getAccountByUsername(acc.getUsername()).getPassword().equals(acc.getPassword())){                //check if the passwords match
                ctx.status(200);
                ctx.json(socialMediaService.getAccountByUsername(acc.getUsername()));  //returning the newly added account in the position in the table
                return;
            }
        }
        ctx.status(401);
    }

    /**
     * As a user, I should be able to submit a new post on the endpoint POST localhost:8080/messages. The request body will contain a JSON representation of a message, which should be persisted to the database, but will not contain a message_id.
     * The creation of the message will be successful if and only if the message_text is not blank, is not over 255 characters, and posted_by refers to a real, existing user. If successful, the response body should contain a JSON of the message, including its message_id. The response status should be 200, which is the default. The new message should be persisted to the database.
     * If the creation of the message is not successful, the response status should be 400. (Client error)
     * @param ctx
     * @throws JsonProcessingException
     */
    private void createMessageHandler (Context ctx) throws JsonProcessingException{
        ObjectMapper mapper = new ObjectMapper();
        Message msg = mapper.readValue(ctx.body(), Message.class);

        //if and only if the message_text is not blank, is not over 255 characters, and posted_by refers to a real, existing user
        if(!msg.getMessage_text().isBlank() && msg.message_text.length() <= 255 && socialMediaService.getAccountById(msg.getPosted_by()) != null){
            ctx.status(200);
            ctx.json(messageService.createMessage(msg)); //returning the message
        }
        else{
            ctx.status(400);
        }
    }
    /**
     * Handler to retrieve all messages.
     * result 200 is default
     * @param ctx
     */
    private void retrieveAllMessagesHandler (Context ctx){
        ctx.json(messageService.getAllMessages());
    }

     /**
     * Handler to retrieve one message by it message_id.
     * @param ctx
     */
    private void retrieveMessageHandler (Context ctx){
        int id = Integer.parseInt(ctx.pathParam("message_id"));
        Message store = messageService.getMessageById(id); //store the message
         if(store != null){
            ctx.json(store);
         } 
         else{
            ctx.result(""); // be empty if no such message
            ctx.status(200); //successful anyway
        }

    }

    /**
     * Our API should be able to delete a message identified by a message ID.
     * As a User, I should be able to submit a DELETE request on the endpoint DELETE localhost:8080/messages/{message_id}.
     * The deletion of an existing message should remove an existing message from the database. If the message existed, the response body should contain the now-deleted message. 
     * The response status should be 200, which is the default. 
     * If the message did not exist, the response status should be 200, but the response body should be empty. 
     * This is because the DELETE verb is intended to be idempotent, ie, multiple calls to the DELETE endpoint should respond with the same type of response.
     * @param ctx
     */

    private void deleteMessageHandler (Context ctx){
        int id = Integer.parseInt(ctx.pathParam("message_id"));
       // Message store = messageService.getMessageById(id); //store the message
        if(messageService.getMessageById(id) != null){
            ctx.json(messageService.getMessageById(id));
            messageService.deleteMessageById(id);         
        }
        ctx.status(200);
    }

    /**
     * As a user, I should be able to submit a PATCH request on the endpoint PATCH localhost:8080/messages/{message_id}. 
     * The request body should contain a new message_text values to replace the message identified by message_id.
     * The request body can not be guaranteed to contain any other information.The update of a message should be successful if 
     * and only if the message id already exists and the new message_text is not blank and is not over 255 characters.
     * If the update is successful, the response body should contain the full updated message (including message_id, posted_by, message_text, and time_posted_epoch), 
     * and the response status should be 200, which is the default. The message existing on the database should have the updated message_text. 
     * If the update of the message is not successful for any reason, the response status should be 400. (Client error)
     */
    private void patchMessageHandler(Context ctx) throws JsonProcessingException{
         ObjectMapper mapper = new ObjectMapper();
         Message msg = mapper.readValue(ctx.body(), Message.class);
         int id = Integer.parseInt(ctx.pathParam("message_id"));
    
        if(!msg.getMessage_text().isBlank() && msg.message_text.length() <= 255 && messageService.getMessageById(id) != null){
            messageService.updateMessage(id, msg);
            ctx.status(200);
            ctx.json(messageService.getMessageById(id)); //returning the updated message
        }
        else{
            ctx.status(400);
        }      
    }

    /**
     * Our API should be able to retrieve all messages written by a particular user.
     * As a user, I should be able to submit a GET request on the endpoint GET localhost:8080/accounts/{account_id}/messages.
     * The response body should contain a JSON representation of a list containing all messages posted by a particular user, which is retrieved from the database. 
     * It is expected for the list to simply be empty if there are no messages. The response status should always be 200, which is the default.
     */

    private void retrieveAllMessagesFromUserHandler(Context ctx){
        int id = Integer.parseInt(ctx.pathParam("account_id"));
        ctx.json(messageService.getAllMessagesFromUser(id));
    }

}