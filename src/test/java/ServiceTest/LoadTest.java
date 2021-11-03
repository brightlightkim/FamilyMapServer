package ServiceTest;

import DataAccess.Database;
import Error.DataAccessException;
import Model.Event;
import Model.Person;
import Model.User;
import Request.LoadRequest;
import Result.LoadResult;
import Service.LoadService;
import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class LoadTest {
    private Database db;
    LoadRequest request;
    LoadRequest badRequest;
    LoadService service;
    LoadResult result;


    @BeforeEach
    public void setUp() throws FileNotFoundException, DataAccessException {
        db = new Database();
        Gson gson = new Gson();
        JsonReader jsonReader = new JsonReader(new FileReader("passoffFiles/LoadData.json"));
        request = gson.fromJson(jsonReader, LoadRequest.class);
        ArrayList<User> users;
        ArrayList<Person> persons;
        ArrayList<Event> events;
        badRequest = new LoadRequest(null, null, null);
        service = new LoadService();
        db.getConnection();
        db.clearTables();
        db.closeConnection(true);
    }

    @Test
    public void SuccessfulLoad() throws DataAccessException {
        result = service.load(request);
        assertNotNull(result);
        assertEquals(result.getMessage(), "Successfully added 2 users, 11 persons, and 19 events to the database.");
        assertEquals(result.isSuccess(), true);
    }

    @Test
    public void FailedLoad() throws DataAccessException {
        result = service.load(badRequest);
        assertNotNull(result);
        assertEquals(result.getMessage(), "Empty Request");
        assertEquals(result.isSuccess(), false);
    }

}
