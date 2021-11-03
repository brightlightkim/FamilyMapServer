package Result;

import java.util.ArrayList;
/**
 * Class that shows the result of all persons.
 * It has data arraylist, message to display, and check if it succeeded.
 */
public class PeopleResult extends Result{
    private ArrayList<PersonResult> data;

    public PeopleResult() {
        super(null, false);
    }

    public PeopleResult (ArrayList<PersonResult> people, boolean success){
        super(success);
        data = people;
    }

    public PeopleResult(String message, boolean success){
        super(message, success);
    }

    public ArrayList<PersonResult> getData() {
        return data;
    }

    public void setData(ArrayList<PersonResult> data) {
        this.data = data;
    }
}
