package DataAccess;

import Model.Person;
import java.util.ArrayList;

/**
 * Data Access Class for Person
 * It stores all persons
 * It finds, inserts, and clears persons.
 */
public class PersonDAO {
    private ArrayList<Person> persons;

    public PersonDAO() {
    }

    /**
     * clear persons
     */
    void clear(){}

    /**
     * find the person with given id
     * @param id id of the person
     * @return the person
     */
    Person find(String id){return null;}

    /**
     * insert the person
     * @param person It's the person
     */
    void insert(Person person){}

    /**
     * find all persons
     * @return persons
     */
    ArrayList<Person> findPersons(){
        return null;
    }
}
