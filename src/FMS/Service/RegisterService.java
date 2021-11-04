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

            if(request.getUsername() == null || request.getFirstName() == null ||
            request.getLastName() == null || request.getGender() == null ||
            request.getEmail() == null || request.getPassword() == null){
                RegisterResult result = new RegisterResult("Error: Request Field Is Not Filled", false);
                db.closeConnection(false);
                return result;
            }
            // Use DAOs to do requested operation
            User matchedUser = new UserDAO(db.getConnection()).find(request.getUsername());

            if (matchedUser != null){
                RegisterResult result = new RegisterResult("Error: We already have this username", false);
                db.closeConnection(false);
                return result;
            }
            db.closeConnection(true);
            FillService service = new FillService();
            int bornYear = service.getRandomNum(1921,2021);

            Person newPerson = service.generatePerson(request.getUsername(), request.getFirstName(), request.getLastName(),
                    request.getGender(), bornYear, 4);

            User newUser = new User(request.getUsername(), request.getPassword(),
                    request.getEmail(), request.getFirstName(), request.getLastName(),
                    request.getGender(), newPerson.getPersonID());

            //Create Token for this request
            String uuid = UUID.randomUUID().toString(); //grant the random token for the username.
            AuthToken newToken = new AuthToken(uuid, request.getUsername());

            //Create Event for this request >> Birth Event
            //Event birthEvent = new Event("BIRTH", ) >> function of create birth event.
            db.getConnection();
            //Add it to the database
            new UserDAO(db.getConnection()).insert(newUser);
            new EventDAO(db.getConnection()).removeDeathEvent(newPerson);
            new AuthTokenDAO(db.getConnection()).insert(newToken);

            // Close database connection, COMMIT transaction
            db.closeConnection(true);

            // Create and return SUCCESS Result object
            RegisterResult result = new RegisterResult(uuid, request.getUsername(),
                    newPerson.getPersonID(), true);
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
