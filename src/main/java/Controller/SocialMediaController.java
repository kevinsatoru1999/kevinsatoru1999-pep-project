// package Controller;

// import io.javalin.Javalin;
// import io.javalin.http.Context;

// /**
//  * TODO: You will need to write your own endpoints and handlers for your controller. The endpoints you will need can be
//  * found in readme.md as well as the test cases. You should
//  * refer to prior mini-project labs and lecture materials for guidance on how a controller may be built.
//  */
// public class SocialMediaController {
//     /**
//      * In order for the test cases to work, you will need to write the endpoints in the startAPI() method, as the test
//      * suite must receive a Javalin object from this method.
//      * @return a Javalin app object which defines the behavior of the Javalin controller.
//      */
//     public Javalin startAPI() {
//         Javalin app = Javalin.create();
//         app.get("example-endpoint", this::exampleHandler);

//         return app;
//     }

//     /**
//      * This is an example handler for an example endpoint.
//      * @param context The Javalin Context object manages information about both the HTTP request and response.
//      */
//     private void exampleHandler(Context context) {
//         context.json("sample text");
//     }


// }

package Controller;

import io.javalin.Javalin;
import io.javalin.http.Context;

import com.fasterxml.jackson.core.JsonProcessingException;

import Model.Account;
import Model.Message;
import Service.AccountService;
import Service.MessageService;

/**
 * TODO: You will need to write your own endpoints and handlers for your controller. The endpoints you will need can be
 * found in readme.md as well as the test cases. You should
 * refer to prior mini-project labs and lecture materials for guidance on how a controller may be built.
 */
public class SocialMediaController {
    /**
     * In order for the test cases to work, you will need to write the endpoints in the startAPI() method, as the test
     * suite must receive a Javalin object from this method.
     * @return a Javalin app object which defines the behavior of the Javalin controller.
     */
    private final AccountService accountService;
    private final MessageService messageService;

    public SocialMediaController(AccountService accountService, MessageService messageService) {
        this.accountService = accountService;
        this.messageService = messageService;
    }

    public SocialMediaController() {
        this.accountService = new AccountService();
        this.messageService = new MessageService();
    }

    public Javalin startAPI() {
        Javalin app = Javalin.create();
        app.post("/register", this::registerHandler);
        app.post("/login", this::loginHandler);
        app.post("/messages", this::createMessageHandler);
        app.get("/messages", this::getAllMessagesHandler);
        app.get("/messages/{message_id}", this::getMessageByIdHandler);
        app.delete("/messages/{id}", this::deleteMessageHandler);
        app.patch("/messages/{id}", this::updateMessageHandler);
        app.get("/accounts/{account_id}/messages", this::getMessagesByAccountIdHandler);
        return app;
    }

    /**
     * This is an example handler for an example endpoint.
     * @param context The Javalin Context object manages information about both the HTTP request and response.
     */
    private void registerHandler(Context context) throws JsonProcessingException{
        Account newAccount = context.bodyAsClass(Account.class);
        Account createdAccount = accountService.registerAccount(newAccount.username,newAccount.password);
        if (createdAccount != null) {
            context.json(createdAccount).status(200);
        } else {
            context.status(400);
        }
    }

    private void loginHandler(Context context) throws JsonProcessingException{
        Account account = context.bodyAsClass(Account.class);
        Account loggedInAccount = accountService.loginAccount(account.getUsername(), account.getPassword());
        if (loggedInAccount != null) {
            context.json(loggedInAccount).status(200);
        } else {
            context.status(401);
        }
    }

    private void createMessageHandler(Context context) throws JsonProcessingException{
        Message message = context.bodyAsClass(Message.class);
        Message createdMessage = messageService.createMessage(message.getPosted_by(), message.getMessage_text(), message.getTime_posted_epoch());
        if (createdMessage != null) {
            context.json(createdMessage).status(200);
        } else {
            context.status(400);
        }
    }

    private void getAllMessagesHandler(Context context) throws JsonProcessingException{
        context.json(messageService.getAllMessages()).status(200);
    }

    private void getMessageByIdHandler(Context context) throws JsonProcessingException{
        int messageId = Integer.parseInt(context.pathParam("message_id"));
        Message message = messageService.getMessageById(messageId);
        // if (message.getMessage_text() == null) {
            
        // } 
        context.json(message).status(200);
    }

    private void deleteMessageHandler(Context context) throws JsonProcessingException{
        int messageId = Integer.parseInt(context.pathParam("id"));
        Message deletedMessage = messageService.deleteMessageById(messageId);
        if (deletedMessage != null) {
            context.json(deletedMessage).status(200);
        } else {
            context.status(200);
        }
    }

    private void updateMessageHandler(Context context) throws JsonProcessingException{
        int messageId = Integer.parseInt(context.pathParam("id"));
        Message updatedMessage = context.bodyAsClass(Message.class);
        updatedMessage.setMessage_id(messageId);
        Message message = messageService.updateMessage(updatedMessage.getMessage_id(), updatedMessage);
        if (message != null) {
            context.json(message).status(200);
        } else {
            context.status(400);
        }
    }

    private void getMessagesByAccountIdHandler(Context context) throws JsonProcessingException{
        int accountId = Integer.parseInt(context.pathParam("account_id"));
        context.json(messageService.getMessagesByAccountId(accountId)).status(200);
    }




}