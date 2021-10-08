package Result;

/**
 * Class that shows the result of clear.
 * It has a message to display, and boolean if it succeeded.
 */

public class ClearResult {
    private String message;
    private boolean success;

    public ClearResult() {
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
