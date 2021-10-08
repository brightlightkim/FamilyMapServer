package DataAccess;

import Model.Person;
import java.util.ArrayList;

/**
 * Data Access Class for Person
 * It stores all persons
 * It finds, inserts, and clears persons.
 */
public class PersonDao {
    private ArrayList<Person> persons;

    public PersonDao() {
    }

    /**
     * clear persons
     */
    void clear(){}

    /**
     * find the person with given id
     * @param id
     * @return
     */
    Person find(String id){return null;}

    /**
     * insert the person
     * @param person
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
