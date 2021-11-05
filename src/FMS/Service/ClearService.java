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
        ClearResult result;
        try{
            db.getConnection();
            db.clearTables();
            db.closeConnection(true);
            result = new ClearResult("clear succeeded", true);
        }
        catch (Exception ex) {
            ex.printStackTrace();
            db.closeConnection(false);
            result = new ClearResult("Error: failed clearing", false);
        }
        return result;
    }
}
