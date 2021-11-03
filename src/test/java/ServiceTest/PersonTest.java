package ServiceTest;

import DataAccess.Database;
import DataAccess.PersonDAO;
import Error.DataAccessException;
import Model.Person;
import Request.RegisterRequest;
import Result.PersonResult;
import Result.PersonsResult;
import Service.PersonService;
import Service.RegisterService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class PersonTest {
    private Database db;
    private Person person;
    private Person comparePerson;
    private RegisterRequest registerRequest;
    private RegisterService registerService;
    private PersonService service;
    private PersonResult personResult;
    private PersonsResult personsResult;

    @BeforeEach
    public void setUp() throws DataAccessException {
        db = new Database();
        person = new Person("k2289", "taeyangk95", "Taeyang",
                "Kim", "M", null, null, null);
        comparePerson = new Person("k2245", "tasdfgk95", "Yaeyang",
                "Kdfm", "M", null, null, null);
        db.getConnection();
        db.clearTables();
        new PersonDAO(db.getConnection()).insert(person);
        new PersonDAO(db.getConnection()).insert(comparePerson);
        db.closeConnection(true);
        registerRequest = new RegisterRequest(person.getAssociatedUsername(),
                                    "abcdefg", "k2289@byu.edu", person.getFirstName(),
                                            person.getLastName(),"m");
        registerService = new RegisterService();
        registerService.register(registerRequest);
        service = new PersonService();
    }

    @Test
    public void successSearchOnePerson() throws DataAccessException {
        try{
            personResult = service.findPerson("k2289");
            assertNotNull(personResult);
            assertEquals(personResult.getLastName(), person.getLastName());
            assertEquals(personResult.getAssociatedUsername(), person.getAssociatedUsername());
            assertEquals(personResult.getFirstName(), person.getFirstName());
        }
        catch (DataAccessException e){
            e.printStackTrace();
            throw new DataAccessException("Error while searching for one person");
        }
    }

    @Test
    public void successSearchManyPeople() throws DataAccessException {
        try{
            personsResult = service.findAllPeople(person.getAssociatedUsername());
            assertNotNull(personsResult);
            assertEquals(personsResult.isSuccess(), true);
        }
        catch (DataAccessException e){
            e.printStackTrace();
            throw new DataAccessException("Error while searching for many people");
        }
    }

    @Test
    public void failedSearchOnePerson() throws DataAccessException {
        try{
            personResult = service.findPerson("k2893");
            assertNotNull(personResult);
            assertEquals(personResult.isSuccess(), false);
        }
        catch (DataAccessException e){
            e.printStackTrace();
            throw new DataAccessException("Error while searching for one person");
        }
    }

    @Test
    public void failedSearchManyPeople() throws DataAccessException {
        try{
            personsResult = service.findAllPeople("yaho");
            assertNotNull(personsResult);
            assertEquals(personsResult.isSuccess(), false);
        }
        catch (DataAccessException e){
            e.printStackTrace();
            throw new DataAccessException("Error while searching for many people");
        }
    }
}
