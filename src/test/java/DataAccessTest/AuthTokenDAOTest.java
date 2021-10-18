package DataAccessTest;

import DataAccess.AuthTokenDAO;
import DataAccess.Database;
import DataAccess.PersonDAO;
import Model.AuthToken;
import Model.Person;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import Exception.DataAccessException;

import java.sql.Connection;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertNull;

public class AuthTokenDAOTest {
    private Database db;
    private AuthToken bestToken;
    private AuthToken compareToken;
    private AuthTokenDAO aDAO;
    @BeforeEach
    public void setUp() throws DataAccessException {
        db = new Database();
        bestToken = new AuthToken("taeyangk95", "taeyangk");
        compareToken = new AuthToken("jin", "that");
        Connection conn = db.getConnection();
        db.clearTables();
        aDAO = new AuthTokenDAO(conn);
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
        aDAO.insert(bestToken);
        AuthToken compareTest = aDAO.find(bestToken.getAuthToken());
        assertNotNull(compareTest);
        assertEquals(bestToken, compareTest);
    }

    @Test
    public void insertFail() throws DataAccessException {
        aDAO.insert(bestToken);
        assertThrows(DataAccessException.class, ()-> aDAO.insert(bestToken));
    }

    @Test
    public void findPass() throws DataAccessException {
        aDAO.insert(bestToken);
        aDAO.insert(compareToken);
        AuthToken compareTest = aDAO.find(bestToken.getAuthToken());
        assertNotNull(compareTest);
        assertEquals(bestToken, compareTest);
    }

    @Test
    public void findFail() throws DataAccessException {
        assertNull( aDAO.find(bestToken.getAuthToken()));
    }

    @Test
    public void clear() throws DataAccessException {
        aDAO.clear();
        assertNull(aDAO.find(bestToken.getAuthToken()));
    }
}
