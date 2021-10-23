package Result;

/**
 * Class that shows the result of register.
 * It has authorized token, username, and personID.
 * It also has a message to display, and boolean if it succeeded.
 */
public class RegisterResult extends Result{
    private String authtoken;
    private String username;
    private String personID;

    public RegisterResult(String authtoken, String username, String personID, String message, boolean success) {
        super(message, success);
        this.authtoken = authtoken;
        this.username = username;
        this.personID = personID;
    }

    public RegisterResult(String message, boolean success){
        super(message, success);
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


}
