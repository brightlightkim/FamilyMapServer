package Result;

import java.util.ArrayList;

/**
 * Class that shows the result of all events.
 * It has data arraylist, message to display, and check if it succeeded.
 */
public class EventsResult extends Result{
    private ArrayList<EventResult> data;

    public EventsResult() {
        super(null, false);
    }

    public EventsResult(boolean success, ArrayList<EventResult> data) {
        super(success);
        this.data = data;
    }

    public EventsResult(String message, boolean success){
        super(message, success);
    }

    public ArrayList<EventResult> getData() {
        return data;
    }

    public void setData(ArrayList<EventResult> data) {
        this.data = data;
    }
}
