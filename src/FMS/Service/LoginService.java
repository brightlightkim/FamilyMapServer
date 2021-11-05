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
    private User matchedUser;

    public LoginResult login(LoginRequest request) throws DataAccessException {
        Database db = new Database();
        try{
            LoginResult result = checkPossibleErrors(request, db);
            if (result != null){
                return result;
            }

            return loginWithValidRequest(request, db);
        }
        catch (Exception ex) {
            ex.printStackTrace();
            db.closeConnection(false);
        }

        return null;
    }

    private LoginResult checkPossibleErrors(LoginRequest request, Database db) throws DataAccessException {
        try {
            db.getConnection();
            LoginResult result = null;
            if (request.getUsername() == null || request.getPassword() == null) {
                result = new LoginResult("Error: request field is not filled", false);
                db.closeConnection(false);
                return result;
            }

            // Use DAOs to do requested operation
            matchedUser = new UserDAO(db.getConnection()).find(request.getUsername());

            if (matchedUser == null) {
                result = new LoginResult("Error: No ID that match", false);
                db.closeConnection(false);
                return result;
            }

            //when the password not match
            if (!matchedUser.getPassword().equals(request.getPassword())) {
                result = new LoginResult("Error: Password not match", false);
                db.closeConnection(false);
                return result;
            }

            db.closeConnection(true);
            return result;
        }
        catch (DataAccessException e){
            e.printStackTrace();
            db.closeConnection(false);
            throw new DataAccessException("Error while checking the login request");
        }
    }

    private LoginResult loginWithValidRequest(LoginRequest request, Database db) throws DataAccessException {
        try {
            String uuid = UUID.nameUUIDFromBytes(request.getUsername().getBytes()).toString();
            AuthToken token = new AuthToken(uuid, request.getUsername());
            db.getConnection();
            new AuthTokenDAO(db.getConnection()).insert(token);
            // Close database connection, COMMIT transaction
            db.closeConnection(true);

            // Create and return SUCCESS Result object
            LoginResult result = new LoginResult(uuid, matchedUser.getUsername(), matchedUser.getPersonID(), true);
            return result;
        }
        catch (DataAccessException e){
            e.printStackTrace();
            db.closeConnection(false);
            throw new DataAccessException("Error while checking the login request");
        }
    }
}
