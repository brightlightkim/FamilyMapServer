package Result;
/**
 * Class that shows the result of load.
 * It has a message to display, and boolean if it succeeded.
 */
public class LoadResult extends Result{

    public LoadResult() {
        super(null, false);
    }

    public LoadResult(String message, boolean success){
        super(message, success);
    }
}
