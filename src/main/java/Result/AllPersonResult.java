package Result;

import Model.Person;

import java.util.ArrayList;

public class AllPersonResult {
    private ArrayList<Person> data;
    private String message;
    private boolean success;

    public AllPersonResult() {
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
