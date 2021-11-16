package Result;
/**
 * Class that shows the result of fill.
 * It has a message to display, and boolean if it succeeded.
 */
public class FillResult extends Result{

    public FillResult() {
        super(null, false);
    }

    public FillResult(String message, boolean success){
        super(message, success);
    }

}
