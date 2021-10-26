package Service;

import Error.DataAccessException;
import Model.Person;
import Result.FillResult;
/**
 * Class that help fill.
 */
public class FillService {
    /**
     * Fill it and show the result
     * @return the result of fill
     */
    public FillResult fillResult (String username, int generations) throws DataAccessException{

        return null;
    }

    Person generatePerson(String gender, int generations) {

        Person mother = null;
        Person father = null;

        if (generations > 1) {
            mother = generatePerson("FEMALE", generations - 1);
            father = generatePerson("MALE", generations - 1);

            // Set mother's and father's spouse IDs

            // Add marriage events to mother and father

            // (their marriage events must be in synch with each other)
        }

        //Person person = new Person();
        // Set person's properties
        // Generate events for person (except marriage)
        // Save person in database

        return person;
    }

}
