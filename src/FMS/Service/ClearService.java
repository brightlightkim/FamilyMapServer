package Service;

import DataAccess.Database;
import Error.DataAccessException;
import Result.ClearResult;

/**
 * Class that performs the clear
 */
public class ClearService {
    /**
     * It performs the clear then show the result of it
     * @return the ClearResult object
     */
    public ClearResult clear() throws DataAccessException{
        Database db = new Database();
        try{
            db.openConnection();
            // clear all tables
            db.clearTables();
            // Close database connection, COMMIT transaction
            db.closeConnection(true);

            // Create and return SUCCESS Result object
            ClearResult result = new ClearResult("Cleared all tables", true);
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
