package Result;

import Model.Person;

import java.util.ArrayList;
/**
 * Class that shows the result of all persons.
 * It has data arraylist, message to display, and check if it succeeded.
 */
public class PeopleResult extends Result{
    private ArrayList<Person> data;

    public PeopleResult() {
        super(null, false);
    }

    public PeopleResult (ArrayList<Person> people, String message, boolean success){
        super(message, success);
        data = people;
    }

    public ArrayList<Person> getData() {
        return data;
    }

    public void setData(ArrayList<Person> data) {
        this.data = data;
    }

}
