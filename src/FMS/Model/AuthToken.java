package Model;

import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AuthToken authToken = (AuthToken) o;
        return Objects.equals(AuthToken, authToken.AuthToken) && Objects.equals(Username, authToken.Username);
    }

    @Override
    public int hashCode() {
        return Objects.hash(AuthToken, Username);
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
