package DataAccess;

import Model.*;

import java.util.List;

public class UserDao {

    //Users Table

    // clear
    // find (get) >> retrieve >> primary key matter.
    // insert a row to the table


    void createUser(User user){}
    boolean validate(String username, String password){
        return false;
    }
    //does not matter.

    User getUserByID(String userId){
        return null;
    }


    //It should be in the event dao.
    List<Event> findUserEvents(String username){
        return null;
    }

}
