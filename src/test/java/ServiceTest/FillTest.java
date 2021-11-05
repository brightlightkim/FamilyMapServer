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
    private User bestUser;
    private FillService service;
    private FillResult result;

    @BeforeEach
    public void setUp() throws DataAccessException, FileNotFoundException {
        Database db = new Database();
        bestUser = new User("taeyangk95", "hello", "k2289@byu.edu",
                "Taeyang", "Kim", "M", "k2289");
        db.getConnection();
        db.clearTables();
        db.closeConnection(true);
        service = new FillService();
    }

    @Test
    public void normalFillPass() throws DataAccessException {
        result = service.fillResult(bestUser.getUsername(), bestUser.getLastName(), 4);
        assertNotNull(result);
        assertEquals("Successfully added 31 persons and 92 events to the database", result.getMessage());
    }

    @Test
    public void fiveGenerationPass() throws DataAccessException {
        result = service.fillResult(bestUser.getUsername(), bestUser.getLastName(), 5);
        assertNotNull(result);
        assertEquals("Successfully added 63 persons and 188 events to the database", result.getMessage());
    }

    @Test
    public void invalidGenerationNumber() throws DataAccessException {
        result = service.fillResult(bestUser.getUsername(), bestUser.getLastName(), -3);
        assertNotNull(result);
        assertEquals(result.getMessage(), "Error: Invalid generation number");
    }

    @Test
    public void TooGreatGenerationNumber() throws DataAccessException {
        result = service.fillResult(bestUser.getUsername(), bestUser.getLastName(), 200);
        assertNotNull(result);
        assertEquals("Error: generation numbers are too great", result.getMessage());
    }
}
