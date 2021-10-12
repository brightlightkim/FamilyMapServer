package Model;

/**
 * Model Class for AuthToken
 */
public class AuthToken {
    private String AuthToken;
    private String Username;

    /**
     * Constructor
     * @param authToken authorized token
     * @param username related username
     */
    public AuthToken(String authToken, String username) {
        this.AuthToken = authToken;
        this.Username = username;
    }

    public String getAuthToken() {
        return AuthToken;
    }

    public void setAuthToken(String authToken) {
        this.AuthToken = authToken;
    }

    public String getUsername() {
        return Username;
    }

    public void setUsername(String username) {
        this.Username = username;
    }
}
