package DataAccess;

import Model.*;

import java.util.ArrayList;

/**
 * Data Access Class for user
 * It stores all users
 * It finds, inserts, and clears users.
 */
public class UserDAO {
    private ArrayList<User> users;
    /**
     * clear users
     */
    void clear(){}

    /**
     * find the user by id
     * @param id id of the user
     * @return user
     */
    User find(String id){return null;}

    /**
     * inser the user
     * @param user user
     */
    void insert(User user){}
}
