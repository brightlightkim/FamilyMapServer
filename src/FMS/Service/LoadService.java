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
    int userNum;
    int personNum;
    int eventNum;
    int emptyIfItReachThree;

    /**
     * Performs the loading with the request and return the result
     * @param request LoadRequest object
     * @return Load Result
     */
    public LoadResult load(LoadRequest request) throws DataAccessException {
        Database db = new Database();
        db.getConnection();
        db.clearTables();
        db.closeConnection(true);
        LoadResult result;
        String message;
        try{
            userNum = 0;
            personNum = 0;
            eventNum = 0;
            emptyIfItReachThree = 0;

            loadUsers(request, db);
            loadPersons(request, db);
            loadEvents(request, db);

            if (emptyIfItReachThree != 3) {
                message = "Successfully added " + userNum + " users, " + personNum + " persons, and " + eventNum + " events to the database.";
                result = new LoadResult(message, true);
            }
            else{
                message = "Error: Empty Request";
                result = new LoadResult(message, false);
            }
        }
        catch (Exception exception){
            exception.printStackTrace();
            db.closeConnection(false);
            result = new LoadResult("Error in accessing data", false);
        }
        return result;
    }

    private void loadUsers(LoadRequest request, Database db) throws DataAccessException{
        try {
            if (request.getUsers() != null) {
                for (User user : request.getUsers()) {
                    new UserDAO(db.getConnection()).insert(user);
                    db.closeConnection(true);
                    userNum++;
                }
            } else {
                emptyIfItReachThree++;
            }
        }
        catch (DataAccessException e){
            e.printStackTrace();
            throw new DataAccessException("Error while loading users");
        }
    }

    private void loadPersons(LoadRequest request, Database db) throws DataAccessException{
        try {
            if (request.getPersons() != null) {
                for (Person person : request.getPersons()) {
                    new PersonDAO(db.getConnection()).insert(person);
                    db.closeConnection(true);
                    personNum++;
                }
            }
            else{
                emptyIfItReachThree++;
            }
        }
        catch (DataAccessException e){
            e.printStackTrace();
            throw new DataAccessException("Error while loading users");
        }
    }

    private void loadEvents(LoadRequest request, Database db) throws DataAccessException{
        try {
            if (request.getEvents() != null) {
                for (Event event : request.getEvents()) {
                    new EventDAO(db.getConnection()).insert(event);
                    db.closeConnection(true);
                    eventNum++;
                }
            }
            else{
                emptyIfItReachThree++;
            }
        }
        catch (DataAccessException e){
            e.printStackTrace();
            throw new DataAccessException("Error while loading users");
        }
    }
}
