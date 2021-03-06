package Service;

import DataAccess.Database;
import DataAccess.PersonDAO;
import Error.DataAccessException;
import Model.Person;
import Result.PersonsResult;
import Result.PersonResult;

import java.util.ArrayList;
import java.util.Set;

/**
 * Class that performs person relating methods
 * It can either find one person or every person
 */
public class PersonService {
    /**
     * Method that shows the result of finding one person
     *
     * @return one person
     */
    public PersonResult findPerson(String personID) throws DataAccessException {
        Database db = new Database();
        try {
            db.openConnection();
            // Use DAOs to do requested operation
            Person desiredPerson = new PersonDAO(db.getConnection()).find(personID);

            if (desiredPerson == null) {
                PersonResult result = new PersonResult("Error: No Person that match the ID", false);
                db.closeConnection(false);
                return result;
            }

            // Close database connection, COMMIT transaction
            db.closeConnection(true);

            // Create and return SUCCESS Result object
            PersonResult result = new PersonResult(desiredPerson.getAssociatedUsername(),
                    desiredPerson.getPersonID(), desiredPerson.getFirstName(),
                    desiredPerson.getLastName(), desiredPerson.getGender(),
                    desiredPerson.getFatherID(), desiredPerson.getMotherID(),
                    desiredPerson.getSpouseID(), true);
            return result;
        } catch (Exception ex) {
            ex.printStackTrace();

            // Close database connection, ROLLBACK transaction
            db.closeConnection(false);

            // Create and return FAILURE Result object

        }

        return null;
    }

    /**
     * Method that shows the result of finding every person
     *
     * @return every person
     */
    public PersonsResult findAllPeople(String userName) throws DataAccessException {
        Database db = new Database();
        try {
            db.getConnection();
            // Use DAOs to do requested operation
            Set<Person> desiredPeople = new PersonDAO(db.getConnection()).findPeople(userName);
            PersonsResult result;
            if (desiredPeople == null || desiredPeople.size() == 0) {
                result = new PersonsResult("Error: no data is available for the given username", false);
                db.closeConnection(false);
                return result;
            }

            // Close database connection, COMMIT transaction
            db.closeConnection(true);

            return getPersonsResult(desiredPeople);
        } catch (Exception ex) {
            ex.printStackTrace();
            db.closeConnection(false);
        }
        return null;
    }
    private PersonsResult getPersonsResult(Set<Person> desiredPeople){
        ArrayList<PersonResult> peopleResultArray = new ArrayList<>();
        for (Person person : desiredPeople) {
            PersonResult personResult = new PersonResult(person.getAssociatedUsername(),
                    person.getPersonID(), person.getFirstName(),
                    person.getLastName(), person.getGender(),
                    person.getFatherID(), person.getMotherID(),
                    person.getSpouseID(),true);
            peopleResultArray.add(personResult);
        }
        return new PersonsResult(peopleResultArray, true);
    }
}
