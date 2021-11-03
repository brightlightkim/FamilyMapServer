package ServiceTest;

import DataAccess.Database;
import DataAccess.UserDAO;
import Error.DataAccessException;
import Model.User;
import Request.LoginRequest;
import Result.LoginResult;
import Service.LoginService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;

import static org.junit.jupiter.api.Assertions.*;

public class RegisterTest {
    private Database db;
    private User bestUser;
    private User compareUser;
    private LoginRequest passRequest;
    private LoginRequest wrongPasswordRequest;
    private LoginRequest wrongIDRequest;
    private LoginService service;
    private LoginResult result;

    @BeforeEach
    public void setUp() throws DataAccessException {
        db = new Database();
        bestUser = new User("taeyangk95", "hello", "k2289@byu.edu",
                "Taeyang", "Kim", "M", "k2289");
        compareUser = new User("jingerman", "hellso", "k2d289@byu.edu",
                "Taeyangs", "Kiam", "F", "k22s89");
        passRequest = new LoginRequest("taeyangk95", "hello");
        wrongIDRequest = new LoginRequest("xoxo", "nonono");
        wrongPasswordRequest = new LoginRequest("taeyangk95", "hi");
        Connection conn = db.getConnection();
        db.clearTables();
        new UserDAO(conn).insert(bestUser);
        new UserDAO(conn).insert(compareUser);
        db.closeConnection(true);
        service = new LoginService();
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
    public void loginPass() throws DataAccessException {
        result = service.login(passRequest);
        assertNotNull(result);
        assertEquals(result.getUsername(), "taeyangk95");
        assertEquals(result.getPersonID(), "k2289");
    }

    @Test
    public void NoIDFound() throws DataAccessException {
        result = service.login(wrongIDRequest);
        assertNotNull(result);
        assertEquals(result.getMessage(), "No ID that match");
    }

    @Test
    public void passwordNotMatch() throws DataAccessException {
        result = service.login(wrongPasswordRequest);
        assertNotNull(result);
        assertEquals(result.getMessage(), "Password not match");
    }
}
