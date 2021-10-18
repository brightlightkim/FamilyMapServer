package Service;

import DataAccess.Database;
import Exception.DataAccessException;
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
        db.openConnection();

        return null;
    }
}
