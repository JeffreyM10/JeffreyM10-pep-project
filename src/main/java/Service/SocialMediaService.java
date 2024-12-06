package Service;
import Model.Account;
import DAO.SocialMediaDAO;

public class SocialMediaService{
    SocialMediaDAO socialMediaDAO;

    /**
     * Constructor for a socialMedia Service
     * Initialize the socialmediaDAO
     */
    public SocialMediaService(){
        socialMediaDAO = new SocialMediaDAO();
    }

    /**
     * Use the DAO to add a new account to the database.
     */
    public Account addAccount(Account account){
        return socialMediaDAO.insertAccount(account);
    }

    public Account getAccountByUsername(String username){
        return socialMediaDAO.getAccountByUsername(username);
    }

    public Account getAccountById(int id){
        return socialMediaDAO.getAccountByUserID(id);
    }

    public boolean checkUser(String username){
        return socialMediaDAO.checkUser(username);
    }
    
}