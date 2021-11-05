package ServiceTest;

import DataAccess.*;
import Model.AuthToken;
import Error.DataAccessException;
import Model.Event;
import Model.Person;
import Model.User;
import Result.ClearResult;
import Service.ClearService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertNull;

public class ClearTest {
    private ClearService service;
    private ClearResult result;
    private User user;
    private Person person;
    private Event event;
    private AuthToken token;
    private Database db;

    @BeforeEach
    public void setUp() throws DataAccessException {
        service = new ClearService();
        result = new ClearResult();
        db = new Database();
        user = new User("taeyangk95", "hello", "k2289@byu.edu",
                "Taeyang", "Kim", "M", "k2289");
        person = new Person("k2289", "taeyangk95", "Taeyang",
                            "Kim", "M", null, null, null);
        event = new Event("special", "taeyangk95", "k2289",
                            (float)155.3, (float) 132.5, "Korea", "Yongin", "BIRTH",
                            1995);
        token = new AuthToken("tokentoken", "k2289");
        db.getConnection();
        db.clearTables();
        db.closeConnection(true);
        new UserDAO(db.getConnection()).insert(user);
        new PersonDAO(db.getConnection()).insert(person);
        new EventDAO(db.getConnection()).insert(event);
        new AuthTokenDAO(db.getConnection()).insert(token);
        db.closeConnection(true);
    }

    @Test
    public void successfulClean() throws DataAccessException {
        try {
            result = service.clear();
            assertNotNull(result);
            assertEquals(true, result.isSuccess());
            assertNull(new UserDAO(db.getConnection()).find(user.getUsername()));
            db.closeConnection(true);
            assertNull(new PersonDAO(db.getConnection()).find(person.getPersonID()));
            db.closeConnection(true);
            assertNull(new EventDAO(db.getConnection()).find(event.getEventID()));
            db.closeConnection(true);
            assertNull(new AuthTokenDAO(db.getConnection()).find(token.getAuthtoken()));
            db.closeConnection(true);
        }
        catch (DataAccessException e){
            e.printStackTrace();
            throw new DataAccessException("Error: Error while cleaning the data tables");
        }
    }

}
