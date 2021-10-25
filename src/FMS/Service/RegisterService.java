package Service;

import DataAccess.AuthTokenDAO;
import DataAccess.Database;
import DataAccess.PersonDAO;
import DataAccess.UserDAO;
import Error.DataAccessException;
import Model.AuthToken;
import Model.Person;
import Model.User;
import Request.RegisterRequest;
import Result.RegisterResult;

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
            db.openConnection();
            // Use DAOs to do requested operation
            User matchedUser = new UserDAO(db.openConnection()).find(request.getUsername());

            if (matchedUser != null){
                RegisterResult result = new RegisterResult("We already have this username", false);
                db.closeConnection(false);
                return result;
            }
            String uuid = UUID.randomUUID().toString();

            //Create each objects for this person.
            User newUser = new User(request.getUsername(), request.getPassword(),
                    request.getEmail(), request.getFirstName(), request.getLastName(),
                    request.getGender(), uuid);
            //TODO: Have to fix the part of father, mother, and spouse later.
            String fatherID = UUID.randomUUID().toString();
            String motherID = UUID.randomUUID().toString();
            String spouseID = UUID.randomUUID().toString();
            Person newPerson = new Person(uuid, request.getUsername(), request.getFirstName(),
                    request.getLastName(), request.getGender(), fatherID, motherID, spouseID);



            uuid = UUID.randomUUID().toString(); //grant the random token for the username.
            AuthToken newToken = new AuthToken(uuid, request.getUsername());

            //Add it to the database
            new UserDAO(db.getConnection()).insert(newUser);
            new PersonDAO(db.getConnection()).insert(newPerson);
            new AuthTokenDAO(db.getConnection()).insert(newToken);

            // Close database connection, COMMIT transaction
            db.closeConnection(true);

            // Create and return SUCCESS Result object
            RegisterResult result = new RegisterResult(uuid, request.getUsername(),
                    newPerson.getPersonID(), "successfully registered", true);
            return result;
        }
        catch (Exception ex) {
            ex.printStackTrace();

            // Close database connection, ROLLBACK transaction
            db.closeConnection(false);

            // Create and return FAILURE Result object

        }

        return null;
    }
}
