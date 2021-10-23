package Result;

import java.util.ArrayList;

/**
 * Class that shows the result of all events.
 * It has data arraylist, message to display, and check if it succeeded.
 */
public class AllEventResult extends Result{
    private ArrayList<EventResult> data;

    public AllEventResult() {
        super(null, false);
    }

    public AllEventResult(String message, boolean success, ArrayList<EventResult> data) {
        super(message, success);
        this.data = data;
    }

    public AllEventResult(String message, boolean success){
        super(message, success);
    }

    public ArrayList<EventResult> getData() {
        return data;
    }

    public void setData(ArrayList<EventResult> data) {
        this.data = data;
    }
}
