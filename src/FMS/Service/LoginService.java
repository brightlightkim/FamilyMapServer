package Service;

import DataAccess.AuthTokenDAO;
import DataAccess.Database;
import DataAccess.PersonDAO;
import Exception.DataAccessException;
import Model.AuthToken;
import Model.Person;
import Request.LoginRequest;
import Result.LoginResult;

/**
 * Class that performs the login
 */
public class LoginService {
    /**
     * Performs the login and show the result
     * @param request of LoginRequest with username and password
     * @return login result
     */
    public LoginResult login(LoginRequest request) throws DataAccessException {
        Database db = new Database();
        try{
            db.openConnection();
            // Use DAOs to do requested operation
            AuthToken matchedToken = new AuthTokenDAO(db.getConnection()).find(request.getUsername());

            if (matchedToken == null){
                LoginResult result = new LoginResult("No ID that match", false);
                return result;
            }

            Person matchedPerson = new PersonDAO(db.getConnection()).find(matchedToken.getUsername());
            // Close database connection, COMMIT transaction
            db.closeConnection(true);

            // Create and return SUCCESS Result object
            LoginResult result = new LoginResult(matchedToken.getAuthToken(),matchedToken.getUsername(),
                    matchedPerson.getPersonID(),"found Person!", true);
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
