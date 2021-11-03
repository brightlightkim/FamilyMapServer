package Service;

import DataAccess.AuthTokenDAO;
import DataAccess.Database;
import DataAccess.UserDAO;
import Error.DataAccessException;
import Model.AuthToken;
import Model.User;
import Request.LoginRequest;
import Result.LoginResult;

import java.util.UUID;

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
            User matchedUser = new UserDAO(db.openConnection()).find(request.getUsername());

            if (matchedUser == null){
                LoginResult result = new LoginResult("No ID that match", false);
                db.closeConnection(false);
                return result;
            }

            //when the password not match
            if (matchedUser.getPassword() != request.getPassword()){
                LoginResult result = new LoginResult("Password not match", false);
                db.closeConnection(false);
                return result;
            }

            //Then I have to create the Authorization token and insert it.
            String uuid = UUID.nameUUIDFromBytes(request.getUsername().getBytes()).toString();
            AuthToken token = new AuthToken(uuid, request.getUsername());
            new AuthTokenDAO(db.openConnection()).insert(token);
            // Close database connection, COMMIT transaction
            db.closeConnection(true);

            // Create and return SUCCESS Result object
            LoginResult result = new LoginResult(uuid, matchedUser.getUsername(), matchedUser.getPersonID(),true);
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
