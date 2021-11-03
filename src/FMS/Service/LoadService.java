package Service;

import DataAccess.Database;
import DataAccess.EventDAO;
import DataAccess.PersonDAO;
import DataAccess.UserDAO;
import Error.DataAccessException;
import Model.Event;
import Model.Person;
import Model.User;
import Request.LoadRequest;
import Result.LoadResult;
/**
 * Class that performs the loading
 */
public class LoadService {
    /**
     * Performs the loading with the request and return the result
     * @param request LoadRequest object
     * @return Load Result
     */
    public LoadResult load(LoadRequest request) throws DataAccessException {
        Database db = new Database();
        LoadResult result;
        String message;
        try{
            int userNum = 0;
            int personNum = 0;
            int eventNum = 0;
            db.openConnection();
            for(User user: request.getUsers()){
                new UserDAO(db.getConnection()).insert(user);
                userNum++;
            }
            for(Person person: request.getPersons()){
                new PersonDAO(db.getConnection()).insert(person);
                personNum++;
            }
            for(Event event: request.getEvents()){
                new EventDAO(db.getConnection()).insert(event);
                eventNum++;
            }
            db.closeConnection(true);
            message = "Successfully added " + userNum + " users, " + personNum + " persons, and " + eventNum + " events to the database.";
            result = new LoadResult(message, true);
        }
        catch (Exception exception){
            exception.printStackTrace();
            db.closeConnection(false);
            result = new LoadResult("Error in accessing data", false);
        }
        return result;
    }
}
