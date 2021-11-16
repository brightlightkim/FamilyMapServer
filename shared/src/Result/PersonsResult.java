package Result;

import java.util.ArrayList;
/**
 * Class that shows the result of all persons.
 * It has data arraylist, message to display, and check if it succeeded.
 */
public class PersonsResult extends Result{
    private ArrayList<PersonResult> data;

    public PersonsResult() {
        super(null, false);
    }

    public PersonsResult(ArrayList<PersonResult> people, boolean success){
        super(success);
        data = people;
    }

    public PersonsResult(String message, boolean success){
        super(message, success);
    }

    public ArrayList<PersonResult> getData() {
        return data;
    }

    public void setData(ArrayList<PersonResult> data) {
        this.data = data;
    }
}
