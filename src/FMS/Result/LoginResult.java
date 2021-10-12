package Result;

/**
 * Class that shows the result of login.
 * It has an authorized token, username, personID, message to display, and boolean if it succeeded.
 */
public class LoginResult {
    private String authtoken;
    private String username;
    private String personID;
    private String message;
    private boolean success;

    public LoginResult() {
    }

    public String getAuthtoken() {
        return authtoken;
    }

    public void setAuthtoken(String authtoken) {
        this.authtoken = authtoken;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPersonID() {
        return personID;
    }

    public void setPersonID(String personID) {
        this.personID = personID;
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