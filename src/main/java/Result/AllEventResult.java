package Result;

import Model.Event;
import Model.Person;

import java.util.ArrayList;

public class AllEventResult {
    private ArrayList<Event> data;
    private String message;
    private boolean success;

    public AllEventResult() {
    }

    public ArrayList<Event> getData() {
        return data;
    }

    public void setData(ArrayList<Event> data) {
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
