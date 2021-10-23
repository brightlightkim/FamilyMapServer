package Result;

import Model.Event;

import java.util.ArrayList;

/**
 * Class that shows the result of all events.
 * It has data arraylist, message to display, and check if it succeeded.
 */
public class AllEventResult extends Result{
    private ArrayList<Event> data;

    public AllEventResult() {
        super(null, false);
    }

    public AllEventResult(String message, boolean success, ArrayList<Event> data) {
        super(message, success);
        this.data = data;
    }

    public ArrayList<Event> getData() {
        return data;
    }

    public void setData(ArrayList<Event> data) {
        this.data = data;
    }

}
