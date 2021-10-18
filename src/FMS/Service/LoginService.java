package Service;


import DataAccess.Database;
import Request.LoginRequest;
import Result.LoginResult;

import java.io.IOException;

/**
 * Class that performs the login
 */
public class LoginService {
    /**
     * Performs the login and show the result
     * @param request of LoginRequest with username and password
     * @return login result
     */
    public LoginResult login(LoginRequest request) throws IOException{
        Database db = new Database();

        return null;
    }
}
