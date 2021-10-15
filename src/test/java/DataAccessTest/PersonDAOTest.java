package DataAccessTest;

import DataAccess.Database;
import DataAccess.PersonDAO;
import Exception.DataAccessException;
import Model.Person;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

public class PersonDAOTest {
    private Database db;
    private Person bestPerson;
    private Person comparePerson;
    private PersonDAO pDAO;

    @BeforeEach
    public void setUp() throws DataAccessException {
        db = new Database();
        bestPerson = new Person("taeyangk95", "taeyangk", "Taeyang"
                , "Kim", "m", "gwant", "hong", "you");
        comparePerson = new Person("jin", "that", "hwang"
                , "tae", "f", "gwang", "hons", "yod");
        Connection conn = db.getConnection();
        db.clearTables();
        pDAO = new PersonDAO(conn);
    }

    @AfterEach
    public void tearDown() throws DataAccessException{
        try {
            db.closeConnection(false);
        } catch (DataAccessException e) {
            e.printStackTrace();
            throw new DataAccessException("close connection failed");
        }
    }

    @Test
    public void insertPass() throws DataAccessException {
        pDAO.insert(bestPerson);
        Person compareTest = pDAO.find(bestPerson.getPersonID());
        assertNotNull(compareTest);
        assertEquals(bestPerson, compareTest);
    }

    @Test
    public void insertFail() throws DataAccessException {
        pDAO.insert(bestPerson);
        assertThrows(DataAccessException.class, ()-> pDAO.insert(bestPerson));
    }

    @Test
    public void findPass() throws DataAccessException {
        pDAO.insert(bestPerson);
        pDAO.insert(comparePerson);
        Person compareTest = pDAO.find(bestPerson.getPersonID());
        assertNotNull(compareTest);
        assertEquals(bestPerson, compareTest);
    }

    @Test
    public void findFail() throws DataAccessException {
        assertNull( pDAO.find(bestPerson.getPersonID()));
    }

    @Test
    public void clear() throws DataAccessException {
        pDAO.clear();
        assertNull(pDAO.find(bestPerson.getPersonID()));
    }
}
