import Controller.SocialMediaController;
import DAO.MessageDAO;
import Model.Message;
import Service.MessageService;
import io.javalin.Javalin;

/**
 * This class is provided with a main method to allow you to manually run and test your application. This class will not
 * affect your program in any way and you may write whatever code you like here.
 */
public class Main {
    public static void main(String[] args) {
        SocialMediaController controller = new SocialMediaController();
        Javalin app = controller.startAPI();
        app.start(8080);

        // Message msg = new Message();
        // msg.setPosted_by(1);
        // msg.setMessage_text("XXXXX");

        // Message msg1 = new Message();
        // msg.setPosted_by(1);
        // msg.setMessage_text("new. new");
        
        // MessageDAO newbie = new MessageDAO();
        // newbie.insertMessage(msg);
        // System.out.println(newbie.getAllMessages());
        // newbie.updateMessage(msg.getMessage_id(), msg1);
        // System.out.println(newbie.getAllMessages() + " updatessss");
    
    }
}
