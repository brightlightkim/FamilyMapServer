package Result;

import Model.Person;

import java.util.ArrayList;
/**
 * Class that shows the result of all persons.
 * It has data arraylist, message to display, and check if it succeeded.
 */
public class PeopleResult {
    private ArrayList<Person> data;
    private String message;
    private boolean success;

    public PeopleResult() {
    }

    public ArrayList<Person> getData() {
        return data;
    }

    public void setData(ArrayList<Person> data) {
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }
}
