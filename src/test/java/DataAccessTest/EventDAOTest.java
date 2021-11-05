package DataAccessTest;

import DataAccess.Database;
import DataAccess.EventDAO;
import Error.DataAccessException;
import Model.Event;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;

import static org.junit.jupiter.api.Assertions.*;

//We will use this to test that our insert method is working and failing in the right ways
public class EventDAOTest {
    private Database db;
    private Event bestEvent;
    private Event compareEvent;
    private EventDAO eDao;

    @BeforeEach
    public void setUp() throws DataAccessException
    {
        //here we can set up any classes or variables we will need for the rest of our tests
        //lets create a new database
        db = new Database();
        //and a new event with random data
        bestEvent = new Event("Biking_123A", "Gale", "Gale123A",
                35.9f, 140.1f, "Japan", "Ushiku",
                "Biking_Around", 2016);
        compareEvent = new Event("Talent", "Yas", "ulen",
                23.2f, 130.1f, "Korea", "Jukjeon",
                "Talented", 2013);
        //Here, we'll open the connection in preparation for the test case to use it
        Connection conn = db.getConnection();
        //Let's clear the database as well so any lingering data doesn't affect our tests
        db.clearTables();
        //Then we pass that connection to the EventDAO so it can access the database
        eDao = new EventDAO(conn);
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
        eDao.insert(bestEvent);
        Event compareTest = eDao.find(bestEvent.getEventID());
        assertNotNull(compareTest);
        assertEquals(bestEvent, compareTest);
    }

    @Test
    public void insertFail() throws DataAccessException {
        eDao.insert(bestEvent);
        assertThrows(DataAccessException.class, ()-> eDao.insert(bestEvent));
    }

    @Test
    public void findPass() throws DataAccessException {
        eDao.insert(bestEvent);
        eDao.insert(compareEvent);
        Event compareTest = eDao.find(bestEvent.getEventID());
        assertNotNull(compareTest);
        assertEquals(bestEvent, compareTest);
    }

    @Test
    public void findFail() throws DataAccessException {
        assertNull( eDao.find(bestEvent.getPersonID()));
    }

    @Test
    public void clear() throws DataAccessException {
        eDao.clear();
        assertNull(eDao.find(bestEvent.getPersonID()));
    }
}
