package ServiceTest;

import DataAccess.Database;
import DataAccess.UserDAO;
import Error.DataAccessException;
import Model.User;
import Request.RegisterRequest;
import Result.RegisterResult;
import Service.RegisterService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class RegisterTest {
    private Database db;
    private User bestUser;
    private User compareUser;
    private RegisterRequest passRequest;
    private RegisterRequest usedUsernameRequest;
    private RegisterRequest emptyFieldRequest;
    private RegisterService service;
    private RegisterResult result;

    @BeforeEach
    public void setUp() throws DataAccessException {
        db = new Database();
        bestUser = new User("taeyangk95", "hello", "k2289@byu.edu",
                "Taeyang", "Kim", "M", "k2289");
        compareUser = new User("jingerman", "hellso", "k2d289@byu.edu",
                "Taeyangs", "Kiam", "F", "k22s89");
        passRequest = new RegisterRequest("jessi", "hello", "k2289@byu.edu",
                "jessi", "Kim", "F");
        usedUsernameRequest = new RegisterRequest("taeyangk95", "hio", "k2as89@byu.edu",
                "asfg", "dim", "M");
        emptyFieldRequest = new RegisterRequest(null, null, null, null, null, null);

        db.openConnection();
        db.clearTables();
        db.closeConnection(true);
        service = new RegisterService();
    }
    /*
    @AfterEach
    public void tearDown() throws DataAccessException {
        try {
            db.closeConnection(false);
        } catch (DataAccessException e) {
            e.printStackTrace();
            throw new DataAccessException("close connection failed");
        }
    }
     */

    @Test
    public void registerPass() throws DataAccessException {
        try {
            result = service.register(passRequest);
        } catch (DataAccessException e){
            e.printStackTrace();
            throw new DataAccessException("register failed");
        }
        assertNotNull(result);
        assertNotNull(result.getAuthtoken());
        assertNotNull(result.getPersonID());
        assertEquals(result.getUsername(), "jessi");
    }

    @Test
    public void EmptyRequestField() throws DataAccessException {
        try {
            result = service.register(emptyFieldRequest);
        } catch (DataAccessException e){
            e.printStackTrace();
            throw new DataAccessException("Error: register failed");
        }
        assertNotNull(result);
        assertEquals(result.getMessage(), "Error: Request Field Is Not Filled");
    }

    @Test
    public void UsedUserName() throws DataAccessException {
        new UserDAO(db.getConnection()).insert(bestUser);
        db.closeConnection(true);
        result = service.register(usedUsernameRequest);
        assertNotNull(result);
        assertEquals(result.getMessage(), "Error: We already have this username");
    }
}
