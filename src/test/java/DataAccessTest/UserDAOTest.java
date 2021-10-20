package DataAccessTest;

import DataAccess.Database;
import DataAccess.UserDAO;
import Model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;

import Error.DataAccessException;

import static org.junit.jupiter.api.Assertions.*;

public class UserDAOTest {
    private Database db;
    private User bestUser;
    private User compareUser;
    private UserDAO uDao;

    @BeforeEach
    public void setUp() throws DataAccessException
    {
        db = new Database();
        bestUser = new User("taeyangk95", "hello", "k2289@byu.edu",
                "Taeyang", "Kim", "M", "k2289");
        compareUser = new User("jingerman", "hellso", "k2d289@byu.edu",
                "Taeyangs", "Kiam", "F", "k22s89");
        Connection conn = db.getConnection();
        db.clearTables();
        uDao = new UserDAO(conn);
    }

    @AfterEach
    public void tearDown() throws DataAccessException {
        try {
            db.closeConnection(false);
        } catch (DataAccessException e) {
            e.printStackTrace();
            throw new DataAccessException("close connection failed");
        }
    }

    @Test
    public void insertPass() throws DataAccessException {
        uDao.insert(bestUser);
        User compareTest = uDao.find(bestUser.getUsername());
        assertNotNull(compareTest);
        assertEquals(bestUser, compareTest);
    }

    @Test
    public void insertFail() throws DataAccessException {
        uDao.insert(bestUser);
        assertThrows(DataAccessException.class, ()-> uDao.insert(bestUser));
    }

    @Test
    public void findPass() throws DataAccessException {
        uDao.insert(bestUser);
        uDao.insert(compareUser);
        User compareTest = uDao.find(bestUser.getUsername());
        assertNotNull(compareTest);
        assertEquals(bestUser, compareTest);
    }

    @Test
    public void findFail() throws DataAccessException {
        assertNull( uDao.find(bestUser.getPersonID()));
    }

    @Test
    public void clear() throws DataAccessException {
        uDao.clear();
        assertNull(uDao.find(bestUser.getPersonID()));
    }
}
