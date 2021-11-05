package Service;

import DataAccess.AuthTokenDAO;
import DataAccess.Database;
import DataAccess.EventDAO;
import DataAccess.UserDAO;
import Error.DataAccessException;
import Model.AuthToken;
import Model.Person;
import Model.User;
import Request.RegisterRequest;
import Result.RegisterResult;

import java.io.FileNotFoundException;
import java.util.UUID;

/**
 * Class that performs the register
 */
public class RegisterService {
    /**
     * Performs the register and show the result
     * @param request Register Request Object
     * @return register result
     */
    public RegisterResult register(RegisterRequest request) throws DataAccessException {
        Database db = new Database();
        try{
            db.getConnection();
            RegisterResult result;

            result = checkRequestIsEmpty(request, db);
            if (result != null){
                return result;
            }

            result = checkRequestedUsername(request, db);
            if (result != null){
                return result;
            }

            return registerINFO(request, db);
        }
        catch (Exception ex) {
            ex.printStackTrace();

            // Close database connection, ROLLBACK transaction
            db.closeConnection(false);

            // Create and return FAILURE Result object

        }

        return null;
    }

    private RegisterResult checkRequestIsEmpty(RegisterRequest request, Database db) throws DataAccessException {
        RegisterResult result = null;
        if(request.getUsername() == null || request.getFirstName() == null ||
                request.getLastName() == null || request.getGender() == null ||
                request.getEmail() == null || request.getPassword() == null){
            result = new RegisterResult("Error: Request Field Is Not Filled", false);
            db.closeConnection(false);
        }
        else{
            db.closeConnection(true);
        }
        return result;
    }

    private RegisterResult checkRequestedUsername(RegisterRequest request, Database db) throws DataAccessException {
        RegisterResult result = null;
        User matchedUser = new UserDAO(db.getConnection()).find(request.getUsername());
        if (matchedUser != null){
            result = new RegisterResult("Error: We already have this username", false);
            db.closeConnection(false);
        }
        else{
            db.closeConnection(true);
        }
        return result;
    }

    private RegisterResult registerINFO(RegisterRequest request, Database db) throws DataAccessException, FileNotFoundException {
        FillService service = new FillService();

        int bornYear = service.getRandomNum(1921,2021);

        Person newPerson = service.generatePerson(request.getUsername(), request.getFirstName(), request.getLastName(),
                request.getGender(), bornYear, 4);

        User newUser = new User(request.getUsername(), request.getPassword(),
                request.getEmail(), request.getFirstName(), request.getLastName(),
                request.getGender(), newPerson.getPersonID());

        String uuid = UUID.randomUUID().toString();
        AuthToken newToken = new AuthToken(uuid, request.getUsername());

        db.getConnection();
        new UserDAO(db.getConnection()).insert(newUser);
        new EventDAO(db.getConnection()).removeDeathEvent(newPerson);
        new AuthTokenDAO(db.getConnection()).insert(newToken);
        db.closeConnection(true);

        RegisterResult result = new RegisterResult(uuid, request.getUsername(),
                newPerson.getPersonID(), true);
        return result;
    }
}
