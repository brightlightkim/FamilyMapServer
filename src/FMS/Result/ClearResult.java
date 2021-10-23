package Result;

/**
 * Class that shows the result of clear.
 * It has a message to display, and boolean if it succeeded.
 */

public class ClearResult extends Result{

    public ClearResult() {
        super(null, false);
    }

    public ClearResult(String message, boolean success) {
        super(message, success);
    }
}
