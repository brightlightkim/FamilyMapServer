package ServiceTest;

import DataAccess.Database;
import Error.DataAccessException;
import Model.User;
import Result.FillResult;
import Service.FillService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class FillTest {
    private Database db;
    private User bestUser;
    private User compareUser;
    private FillService service;
    private FillResult result;
    //TODO: How to grab the server error (HTTP Request)
    //TODO: How to avoid the database locked problem in Intelij while it's good in my server.
    @BeforeEach
    public void setUp() throws DataAccessException, FileNotFoundException {
        db = new Database();
        bestUser = new User("taeyangk95", "hello", "k2289@byu.edu",
                "Taeyang", "Kim", "M", "k2289");
        compareUser = new User("jingerman", "hellso", "k2d289@byu.edu",
                "Taeyangs", "Kiam", "F", "k22s89");
        db.getConnection();
        db.clearTables();
        db.closeConnection(true);
        service = new FillService();
    }

    @Test
    public void normalFillPass() throws DataAccessException {
        result = service.fillResult(bestUser.getUsername(), bestUser.getLastName(), 4);
        assertNotNull(result);
        assertEquals(result.getMessage(), "Successfully created 31 persons and 92 events to the database");
    }

    @Test
    public void fiveGenerationPass() throws DataAccessException {
        result = service.fillResult(bestUser.getUsername(), bestUser.getLastName(), 5);
        assertNotNull(result);
        assertEquals(result.getMessage(), "Successfully created 63 persons and 188 events to the database");
    }

    @Test
    public void invalidGenerationNumber() throws DataAccessException {
        result = service.fillResult(bestUser.getUsername(), bestUser.getLastName(), -3);
        assertNotNull(result);
        assertEquals(result.getMessage(), "Invalid generation number");
    }

    @Test
    public void TooGreatGenerationNumber() throws DataAccessException {
        result = service.fillResult(bestUser.getUsername(), bestUser.getLastName(), 200);
        assertNotNull(result);
        assertEquals(result.getMessage(), "generation numbers are too great");
    }
}
