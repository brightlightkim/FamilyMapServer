package Service;

import DataAccess.Database;
import DataAccess.PersonDAO;
import Error.DataAccessException;
import Model.Person;
import Result.PersonsResult;
import Result.PersonResult;

import java.util.ArrayList;

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
                PersonResult result = new PersonResult("No Person that match the ID", false);
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
            db.openConnection();
            // Use DAOs to do requested operation
            ArrayList<Person> desiredPeople = new PersonDAO(db.getConnection()).findPeople(userName);
            PersonsResult result;
            if (desiredPeople == null) {
                result = new PersonsResult("no data is available for the given username", false);
                db.closeConnection(false);
                return result;
            }

            // Close database connection, COMMIT transaction
            db.closeConnection(true);

            // Create and return SUCCESS Result object
            //TODO: Check if this format is right
            ArrayList<PersonResult> peopleResultArray = new ArrayList<>();
            for (Person person : desiredPeople) {
                PersonResult personResult = new PersonResult(person.getAssociatedUsername(),
                        person.getPersonID(), person.getFirstName(),
                        person.getLastName(), person.getGender(),
                        person.getFatherID(), person.getMotherID(),
                        person.getSpouseID(),true);
                peopleResultArray.add(personResult);
            }
            result = new PersonsResult(peopleResultArray, true);
            return result;
        } catch (Exception ex) {
            ex.printStackTrace();
            db.closeConnection(false);
        }
        return null;
    }
}
