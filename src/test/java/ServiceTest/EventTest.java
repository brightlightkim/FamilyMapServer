package ServiceTest;

import DataAccess.Database;
import DataAccess.EventDAO;
import Error.DataAccessException;
import Model.Event;
import Request.RegisterRequest;
import Result.EventResult;
import Result.EventsResult;
import Service.EventService;
import Service.RegisterService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class EventTest {
    private Database db;
    private Event event;
    private Event event1;
    private EventResult result;
    private EventsResult results;
    private EventService service;

    @BeforeEach
    public void setUp() throws DataAccessException {
        try {
            db = new Database();
            event = new Event("special", "taeyangk95", "k2289",
                    (float) 155.3, (float) 132.5, "Korea", "Yongin", "BIRTH", 1995);
            event1 = new Event("howare", "taeyangk95", "k2289",
                    (float) 133.3, (float) 115.3, "Korea", "Sujio", "Marriage", 2023);
            service = new EventService();
            db.getConnection();
            db.clearTables();
            new EventDAO(db.getConnection()).insert(event);
            new EventDAO(db.getConnection()).insert(event1);
            db.closeConnection(true);
            RegisterRequest registerRequest = new RegisterRequest("taeyangk95", "1234",
                    "k2289@byu.edu", "Taeyang", "Kim", "m");
            RegisterService service = new RegisterService();
            service.register(registerRequest);
        }
        catch (DataAccessException e){
            e.printStackTrace();
            throw new DataAccessException("Error while setting up for the event test");
        }
    }

    @Test
    public void successFindOneEvent() throws DataAccessException {
        try {
            result = service.requestEvent("special");
            assertNotNull(result);
            assertEquals(result.isSuccess(), true);
        }
        catch (DataAccessException e){
            e.printStackTrace();
            throw new DataAccessException("Error while finding one event");
        }
    }

    @Test
    public void successFindManyEvents() throws DataAccessException {
        try {
            results = service.allEventResult("taeyangk95");
            assertNotNull(results);
            assertEquals(results.isSuccess(), true);
        }
        catch (DataAccessException e){
            e.printStackTrace();
            throw new DataAccessException("Error while finding many events");
        }
    }

    @Test
    public void failedFindOneEvent() throws DataAccessException {
        try {
            result = service.requestEvent("how");
            assertNotNull(result);
            assertEquals(result.isSuccess(), false);
        }
        catch (DataAccessException e){
            e.printStackTrace();
            throw new DataAccessException("Error in finding one event");
        }
    }

    @Test
    public void failedFindingManyEvents() throws DataAccessException {
        try {
            results = service.allEventResult("yoo");
            assertNotNull(results);
            assertEquals(results.getMessage(), "Error: no event is available for the given userName");
            assertEquals(results.isSuccess(), false);
        }
        catch (DataAccessException e){
            e.printStackTrace();
            throw new DataAccessException("Error in finding one event");
        }
    }

}
