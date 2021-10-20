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
        try{
            db.openConnection();
            // Use DAOs to do requested operation
            new DaoA(db.getConnection()).dbOpA(request.x, request.y);
            new DaoB(db.getConnection()).dbOpB(request.z);

            // Close database connection, COMMIT transaction
            db.closeConnection(true);

            // Create and return SUCCESS Result object
            Result result = new Result(true, a, b, c);
            return result;
        }
        catch (Exception ex) {
            ex.printStackTrace();

            // Close database connection, ROLLBACK transaction
            db.closeConnection(false);

            // Create and return FAILURE Result object
            Result result = new Result(false, “Error message”);
            return result;
        }

        return null;
    }
}
