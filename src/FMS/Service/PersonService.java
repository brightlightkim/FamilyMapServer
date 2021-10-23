package Service;

import DataAccess.Database;
import DataAccess.PersonDAO;
import Error.DataAccessException;
import Model.Person;
import Result.PeopleResult;
import Result.PersonResult;

import java.util.ArrayList;

/**
 * Class that performs person relating methods
 * It can either find one person or every person
 */
public class PersonService {
    /**
     * Method that shows the result of finding one person
     * @return one person
     */
    public PersonResult findPerson(String personID) throws DataAccessException {
        Database db = new Database();
        try{
            db.openConnection();
            // Use DAOs to do requested operation
            Person desiredPerson = new PersonDAO(db.openConnection()).find(personID);

            if (desiredPerson == null){
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
                    desiredPerson.getSpouseID(), "found person Successfully",
                    true);
            return result;
        }
        catch (Exception ex) {
            ex.printStackTrace();

            // Close database connection, ROLLBACK transaction
            db.closeConnection(false);

            // Create and return FAILURE Result object

        }

        return null;
    }

    /**
     * Method that shows the result of finding every person
     * @return every person
     */
    public PeopleResult findAllPeople(String userName) throws DataAccessException {
        Database db = new Database();
        try{
            db.openConnection();
            // Use DAOs to do requested operation
            ArrayList<Person> desiredPeople = new PersonDAO(db.openConnection()).findPeople(userName);
            PeopleResult result;
            if (desiredPeople == null){
                result = new PeopleResult("no data is available for the given username", false);
                db.closeConnection(false);
                return result;
            }

            // Close database connection, COMMIT transaction
            db.closeConnection(true);

            // Create and return SUCCESS Result object
            //TODO: Check if this format is right
            ArrayList<PersonResult> peopleResultArray = new ArrayList<>();
            for (Person person : desiredPeople){
                PersonResult personResult = new PersonResult(person.getAssociatedUsername(),
                        person.getPersonID(), person.getFirstName(),
                        person.getLastName(), person.getGender(),
                        person.getFatherID(), person.getMotherID(),
                        person.getSpouseID(), "found person Successfully",
                        true);
                peopleResultArray.add(personResult);
            }
            result = new PeopleResult(peopleResultArray, "successfully got the array", true);
            return result;
        }
        catch (Exception ex) {
            ex.printStackTrace();

            // Close database connection, ROLLBACK transaction
            db.closeConnection(false);

            // Create and return FAILURE Result object

        }

        return null;

        //4. make new function in the personDAO

        //   select person where associated username = username..

        //   10-30 people >> find all persons.. >> back to clients
    }
}
